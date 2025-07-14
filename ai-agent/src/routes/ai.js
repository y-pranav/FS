const express = require('express');
const Document = require('../models/Document');
const Task = require('../models/Task');
const { authenticateToken } = require('../middleware/auth');
const { validateTaskCreation } = require('../middleware/validation');
const { generateSummary, answerQuestions, generateEmailDraft } = require('../services/aiService');

const router = express.Router();

// Apply authentication to all routes
router.use(authenticateToken);

// Process document with AI
router.post('/process', validateTaskCreation, async (req, res) => {
  try {
    const { documentId, questions, emailRecipient } = req.body;
    const userId = req.user._id;

    // Find the document
    const document = await Document.findOne({
      _id: documentId,
      userId: userId
    });

    if (!document) {
      return res.status(404).json({
        error: 'Document not found'
      });
    }

    if (!document.isProcessed) {
      return res.status(400).json({
        error: 'Document not processed',
        message: 'Please wait for document processing to complete'
      });
    }

    // Create task record
    const task = new Task({
      userId: userId,
      documentId: documentId,
      questions: questions.map(q => ({ question: q.question })),
      status: 'processing'
    });

    await task.save();

    // Start processing (in background)
    processDocumentWithAI(task._id, document, questions, emailRecipient)
      .catch(error => {
        console.error('AI processing error:', error);
        updateTaskWithError(task._id, error.message);
      });

    res.status(202).json({
      message: 'Processing started',
      taskId: task._id,
      status: 'processing'
    });

  } catch (error) {
    console.error('AI process initiation error:', error);
    res.status(500).json({
      error: 'Failed to start processing',
      message: error.message
    });
  }
});

// Get task status and results
router.get('/tasks/:taskId', async (req, res) => {
  try {
    const { taskId } = req.params;
    const userId = req.user._id;

    const task = await Task.findOne({
      _id: taskId,
      userId: userId
    }).populate('documentId', 'originalName fileType');

    if (!task) {
      return res.status(404).json({
        error: 'Task not found'
      });
    }

    res.json({
      id: task._id,
      documentId: task.documentId._id,
      documentName: task.documentId.originalName,
      status: task.status,
      questions: task.questions,
      summary: task.summary,
      emailDraft: task.emailDraft,
      emailSent: task.emailSent,
      emailSentAt: task.emailSentAt,
      processingTime: task.processingTime,
      error: task.error,
      createdAt: task.createdAt
    });

  } catch (error) {
    console.error('Error fetching task:', error);
    res.status(500).json({
      error: 'Failed to fetch task',
      message: error.message
    });
  }
});

// Get user's tasks
router.get('/tasks', async (req, res) => {
  try {
    const userId = req.user._id;
    const page = parseInt(req.query.page) || 1;
    const limit = parseInt(req.query.limit) || 10;
    const skip = (page - 1) * limit;

    const tasks = await Task.find({ userId: userId })
      .populate('documentId', 'originalName fileType')
      .sort({ createdAt: -1 })
      .skip(skip)
      .limit(limit);

    const total = await Task.countDocuments({ userId: userId });

    res.json({
      tasks: tasks.map(task => ({
        id: task._id,
        documentId: task.documentId._id,
        documentName: task.documentId.originalName,
        status: task.status,
        questionsCount: task.questions.length,
        emailSent: task.emailSent,
        createdAt: task.createdAt
      })),
      pagination: {
        currentPage: page,
        totalPages: Math.ceil(total / limit),
        totalTasks: total,
        hasNext: page < Math.ceil(total / limit),
        hasPrev: page > 1
      }
    });

  } catch (error) {
    console.error('Error fetching tasks:', error);
    res.status(500).json({
      error: 'Failed to fetch tasks',
      message: error.message
    });
  }
});

// Update email draft
router.put('/tasks/:taskId/email-draft', async (req, res) => {
  try {
    const { taskId } = req.params;
    const { subject, body, recipient } = req.body;
    const userId = req.user._id;

    const task = await Task.findOne({
      _id: taskId,
      userId: userId
    });

    if (!task) {
      return res.status(404).json({
        error: 'Task not found'
      });
    }

    if (task.status !== 'completed') {
      return res.status(400).json({
        error: 'Task not completed',
        message: 'Can only edit email draft for completed tasks'
      });
    }

    // Update email draft
    task.emailDraft.subject = subject || task.emailDraft.subject;
    task.emailDraft.body = body || task.emailDraft.body;
    task.emailDraft.recipient = recipient || task.emailDraft.recipient;
    task.emailDraft.isEdited = true;

    await task.save();

    res.json({
      message: 'Email draft updated successfully',
      emailDraft: task.emailDraft
    });

  } catch (error) {
    console.error('Error updating email draft:', error);
    res.status(500).json({
      error: 'Failed to update email draft',
      message: error.message
    });
  }
});

// Background AI processing function
const processDocumentWithAI = async (taskId, document, questions, emailRecipient) => {
  const startTime = Date.now();
  
  try {
    const task = await Task.findById(taskId);
    const documentText = document.extractedText;

    // Generate summary
    console.log(`Generating summary for task ${taskId}...`);
    const summary = await generateSummary(documentText);
    
    // Answer questions
    console.log(`Answering ${questions.length} questions for task ${taskId}...`);
    const answeredQuestions = await answerQuestions(documentText, questions);
    
    // Generate email draft
    console.log(`Generating email draft for task ${taskId}...`);
    const emailDraft = await generateEmailDraft(
      documentText, 
      summary, 
      answeredQuestions, 
      emailRecipient
    );

    // Update task with results
    const processingTime = Date.now() - startTime;
    
    task.summary = summary;
    task.questions = answeredQuestions;
    task.emailDraft = emailDraft;
    task.status = 'completed';
    task.processingTime = processingTime;
    
    await task.save();
    
    console.log(`Task ${taskId} completed in ${processingTime}ms`);
    
  } catch (error) {
    console.error(`Task ${taskId} failed:`, error);
    await updateTaskWithError(taskId, error.message);
  }
};

// Update task with error
const updateTaskWithError = async (taskId, errorMessage) => {
  try {
    await Task.findByIdAndUpdate(taskId, {
      status: 'failed',
      error: errorMessage
    });
  } catch (updateError) {
    console.error('Failed to update task with error:', updateError);
  }
};

module.exports = router;
