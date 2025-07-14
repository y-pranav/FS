const nodemailer = require('nodemailer');

// Create email transporter
const createTransporter = () => {
  return nodemailer.createTransporter({
    service: process.env.EMAIL_SERVICE || 'gmail',
    auth: {
      user: process.env.EMAIL_USER,
      pass: process.env.EMAIL_PASSWORD
    }
  });
};

// Send email
const sendEmail = async (to, subject, body, from = null) => {
  try {
    const transporter = createTransporter();
    
    const mailOptions = {
      from: from || process.env.EMAIL_USER,
      to: to,
      subject: subject,
      html: body.replace(/\n/g, '<br>'), // Convert newlines to HTML breaks
      text: body // Also include plain text version
    };
    
    const result = await transporter.sendMail(mailOptions);
    
    return {
      success: true,
      messageId: result.messageId,
      response: result.response
    };
  } catch (error) {
    throw new Error(`Failed to send email: ${error.message}`);
  }
};

// Verify email configuration
const verifyEmailConfig = async () => {
  try {
    const transporter = createTransporter();
    await transporter.verify();
    return { success: true, message: 'Email configuration is valid' };
  } catch (error) {
    throw new Error(`Email configuration error: ${error.message}`);
  }
};

module.exports = {
  sendEmail,
  verifyEmailConfig
};
