const mongoose = require('mongoose');

const taskSchema = new mongoose.Schema({
  userId: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'User',
    required: true
  },
  documentId: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'Document',
    required: true
  },
  questions: [{
    question: {
      type: String,
      required: true
    },
    answer: {
      type: String,
      default: ''
    },
    confidence: {
      type: Number,
      default: 0
    }
  }],
  summary: {
    type: String,
    default: ''
  },
  emailDraft: {
    subject: {
      type: String,
      default: ''
    },
    body: {
      type: String,
      default: ''
    },
    recipient: {
      type: String,
      default: ''
    },
    isEdited: {
      type: Boolean,
      default: false
    }
  },
  status: {
    type: String,
    enum: ['pending', 'processing', 'completed', 'failed'],
    default: 'pending'
  },
  emailSent: {
    type: Boolean,
    default: false
  },
  emailSentAt: {
    type: Date,
    default: null
  },
  processingTime: {
    type: Number,
    default: 0
  },
  error: {
    type: String,
    default: null
  },
  createdAt: {
    type: Date,
    default: Date.now
  }
}, {
  timestamps: true
});

module.exports = mongoose.model('Task', taskSchema);
