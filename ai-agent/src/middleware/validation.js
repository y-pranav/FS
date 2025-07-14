const { body, validationResult } = require('express-validator');

// Validation middleware
const validate = (req, res, next) => {
  const errors = validationResult(req);
  if (!errors.isEmpty()) {
    return res.status(400).json({
      error: 'Validation failed',
      details: errors.array()
    });
  }
  next();
};

// User registration validation
const validateRegister = [
  body('email')
    .isEmail()
    .normalizeEmail()
    .withMessage('Please provide a valid email'),
  body('password')
    .isLength({ min: 6 })
    .withMessage('Password must be at least 6 characters long'),
  body('name')
    .trim()
    .isLength({ min: 2, max: 50 })
    .withMessage('Name must be between 2 and 50 characters'),
  validate
];

// User login validation
const validateLogin = [
  body('email')
    .isEmail()
    .normalizeEmail()
    .withMessage('Please provide a valid email'),
  body('password')
    .notEmpty()
    .withMessage('Password is required'),
  validate
];

// Task creation validation
const validateTaskCreation = [
  body('questions')
    .isArray({ min: 1 })
    .withMessage('At least one question is required'),
  body('questions.*.question')
    .trim()
    .isLength({ min: 5, max: 500 })
    .withMessage('Each question must be between 5 and 500 characters'),
  body('emailRecipient')
    .optional()
    .isEmail()
    .withMessage('Please provide a valid email address'),
  validate
];

// Email sending validation
const validateEmailSending = [
  body('recipient')
    .isEmail()
    .withMessage('Please provide a valid recipient email'),
  body('subject')
    .trim()
    .isLength({ min: 1, max: 200 })
    .withMessage('Subject must be between 1 and 200 characters'),
  body('body')
    .trim()
    .isLength({ min: 10, max: 10000 })
    .withMessage('Email body must be between 10 and 10000 characters'),
  validate
];

module.exports = {
  validate,
  validateRegister,
  validateLogin,
  validateTaskCreation,
  validateEmailSending
};
