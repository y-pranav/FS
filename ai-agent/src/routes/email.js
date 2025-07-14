const express = require('express');
const Task = require('../models/Task');
const { authenticateToken } = require('../middleware/auth');
const { validateEmailSending } = require('../middleware/validation');
const { sendEmail, verifyEmailConfig } = require('../services/emailService');

const router = express.Router();

// Apply authentication to all routes
router.use(authenticateToken);

// Send email from task
router.post('/send/:taskId', validateEmailSending, async (req, res) => {
  try {
    const { taskId } = req.params;
    const { recipient, subject, body } = req.body;
    const userId = req.user._id;

    // Find the task
    const task = await Task.findOne({
      _id: taskId,
      userId: userId
    }).populate('documentId', 'originalName');

    if (!task) {
      return res.status(404).json({
        error: 'Task not found'
      });
    }

    if (task.status !== 'completed') {
      return res.status(400).json({
        error: 'Task not completed',
        message: 'Can only send email for completed tasks'
      });
    }

    if (task.emailSent) {
      return res.status(400).json({
        error: 'Email already sent',
        message: 'Email has already been sent for this task'
      });
    }

    // Send email
    const emailResult = await sendEmail(
      recipient,
      subject,
      body,
      req.user.email
    );

    // Update task
    task.emailSent = true;
    task.emailSentAt = new Date();
    task.emailDraft.recipient = recipient;
    task.emailDraft.subject = subject;
    task.emailDraft.body = body;

    await task.save();

    res.json({
      message: 'Email sent successfully',
      emailResult: {
        messageId: emailResult.messageId,
        recipient: recipient,
        subject: subject,
        sentAt: task.emailSentAt
      }
    });

  } catch (error) {
    console.error('Email sending error:', error);
    res.status(500).json({
      error: 'Failed to send email',
      message: error.message
    });
  }
});

// Test email configuration
router.post('/test-config', async (req, res) => {
  try {
    const result = await verifyEmailConfig();
    res.json(result);
  } catch (error) {
    res.status(500).json({
      error: 'Email configuration test failed',
      message: error.message
    });
  }
});

// Send test email
router.post('/test-send', async (req, res) => {
  try {
    const { recipient } = req.body;
    
    if (!recipient) {
      return res.status(400).json({
        error: 'Recipient email is required'
      });
    }

    const testSubject = 'AI Document Agent - Test Email';
    const testBody = `
Hello,

This is a test email from the AI Document Agent application.

If you received this email, the email configuration is working correctly.

Best regards,
AI Document Agent System

Sent by: ${req.user.name} (${req.user.email})
Timestamp: ${new Date().toISOString()}
    `.trim();

    const result = await sendEmail(recipient, testSubject, testBody, req.user.email);

    res.json({
      message: 'Test email sent successfully',
      recipient: recipient,
      messageId: result.messageId
    });

  } catch (error) {
    console.error('Test email error:', error);
    res.status(500).json({
      error: 'Failed to send test email',
      message: error.message
    });
  }
});

module.exports = router;
