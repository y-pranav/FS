const express = require('express');
const path = require('path');
const Document = require('../models/Document');
const { authenticateToken } = require('../middleware/auth');
const { upload, handleMulterError } = require('../middleware/upload');
const { extractTextFromDocument, preprocessText } = require('../utils/documentProcessor');

const router = express.Router();

// Apply authentication to all routes
router.use(authenticateToken);

// Upload and process document
router.post('/upload', 
  upload.single('document'), 
  handleMulterError,
  async (req, res) => {
    try {
      if (!req.file) {
        return res.status(400).json({
          error: 'No file uploaded',
          message: 'Please select a document to upload'
        });
      }

      const fileExtension = path.extname(req.file.originalname).toLowerCase().replace('.', '');
      
      // Create document record
      const document = new Document({
        userId: req.user._id,
        filename: req.file.filename,
        originalName: req.file.originalname,
        filePath: req.file.path,
        fileType: fileExtension,
        fileSize: req.file.size
      });

      await document.save();

      // Extract text from document
      try {
        const { text, metadata } = await extractTextFromDocument(req.file.path, fileExtension);
        const cleanText = preprocessText(text);

        // Update document with extracted text
        document.extractedText = cleanText;
        document.metadata = metadata;
        document.isProcessed = true;
        await document.save();

        res.status(201).json({
          message: 'Document uploaded and processed successfully',
          document: {
            id: document._id,
            originalName: document.originalName,
            fileType: document.fileType,
            fileSize: document.fileSize,
            isProcessed: document.isProcessed,
            metadata: document.metadata,
            textPreview: cleanText.substring(0, 500) + (cleanText.length > 500 ? '...' : '')
          }
        });

      } catch (processingError) {
        // Update document with processing error
        document.processingError = processingError.message;
        await document.save();

        res.status(500).json({
          error: 'Document processing failed',
          message: processingError.message,
          documentId: document._id
        });
      }

    } catch (error) {
      console.error('Document upload error:', error);
      res.status(500).json({
        error: 'Upload failed',
        message: error.message
      });
    }
  }
);

// Get user's documents
router.get('/', async (req, res) => {
  try {
    const page = parseInt(req.query.page) || 1;
    const limit = parseInt(req.query.limit) || 10;
    const skip = (page - 1) * limit;

    const documents = await Document.find({ userId: req.user._id })
      .select('-extractedText') // Exclude full text for list view
      .sort({ createdAt: -1 })
      .skip(skip)
      .limit(limit);

    const total = await Document.countDocuments({ userId: req.user._id });

    res.json({
      documents: documents.map(doc => ({
        id: doc._id,
        originalName: doc.originalName,
        fileType: doc.fileType,
        fileSize: doc.fileSize,
        isProcessed: doc.isProcessed,
        processingError: doc.processingError,
        metadata: doc.metadata,
        createdAt: doc.createdAt
      })),
      pagination: {
        currentPage: page,
        totalPages: Math.ceil(total / limit),
        totalDocuments: total,
        hasNext: page < Math.ceil(total / limit),
        hasPrev: page > 1
      }
    });

  } catch (error) {
    console.error('Error fetching documents:', error);
    res.status(500).json({
      error: 'Failed to fetch documents',
      message: error.message
    });
  }
});

// Get specific document details
router.get('/:documentId', async (req, res) => {
  try {
    const { documentId } = req.params;

    const document = await Document.findOne({
      _id: documentId,
      userId: req.user._id
    });

    if (!document) {
      return res.status(404).json({
        error: 'Document not found'
      });
    }

    res.json({
      id: document._id,
      originalName: document.originalName,
      fileType: document.fileType,
      fileSize: document.fileSize,
      isProcessed: document.isProcessed,
      processingError: document.processingError,
      metadata: document.metadata,
      extractedText: document.extractedText,
      createdAt: document.createdAt
    });

  } catch (error) {
    console.error('Error fetching document:', error);
    res.status(500).json({
      error: 'Failed to fetch document',
      message: error.message
    });
  }
});

// Delete document
router.delete('/:documentId', async (req, res) => {
  try {
    const { documentId } = req.params;

    const document = await Document.findOne({
      _id: documentId,
      userId: req.user._id
    });

    if (!document) {
      return res.status(404).json({
        error: 'Document not found'
      });
    }

    // Delete file from filesystem
    const fs = require('fs').promises;
    try {
      await fs.unlink(document.filePath);
    } catch (fileError) {
      console.warn('Failed to delete file:', fileError.message);
    }

    // Delete document from database
    await Document.findByIdAndDelete(documentId);

    res.json({
      message: 'Document deleted successfully'
    });

  } catch (error) {
    console.error('Error deleting document:', error);
    res.status(500).json({
      error: 'Failed to delete document',
      message: error.message
    });
  }
});

module.exports = router;
