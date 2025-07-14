const fs = require('fs').promises;
const path = require('path');
const pdfParse = require('pdf-parse');
const mammoth = require('mammoth');

// Extract text from PDF
const extractTextFromPDF = async (filePath) => {
  try {
    const dataBuffer = await fs.readFile(filePath);
    const data = await pdfParse(dataBuffer);
    
    return {
      text: data.text,
      metadata: {
        pageCount: data.numpages,
        wordCount: data.text.split(/\s+/).length,
        characterCount: data.text.length
      }
    };
  } catch (error) {
    throw new Error(`Failed to extract text from PDF: ${error.message}`);
  }
};

// Extract text from DOCX
const extractTextFromDOCX = async (filePath) => {
  try {
    const dataBuffer = await fs.readFile(filePath);
    const result = await mammoth.extractRawText({ buffer: dataBuffer });
    
    const text = result.value;
    return {
      text: text,
      metadata: {
        wordCount: text.split(/\s+/).length,
        characterCount: text.length
      }
    };
  } catch (error) {
    throw new Error(`Failed to extract text from DOCX: ${error.message}`);
  }
};

// Extract text from TXT
const extractTextFromTXT = async (filePath) => {
  try {
    const text = await fs.readFile(filePath, 'utf-8');
    
    return {
      text: text,
      metadata: {
        wordCount: text.split(/\s+/).length,
        characterCount: text.length
      }
    };
  } catch (error) {
    throw new Error(`Failed to extract text from TXT: ${error.message}`);
  }
};

// Main text extraction function
const extractTextFromDocument = async (filePath, fileType) => {
  switch (fileType.toLowerCase()) {
    case 'pdf':
      return await extractTextFromPDF(filePath);
    case 'docx':
      return await extractTextFromDOCX(filePath);
    case 'txt':
      return await extractTextFromTXT(filePath);
    default:
      throw new Error(`Unsupported file type: ${fileType}`);
  }
};

// Clean and preprocess text
const preprocessText = (text) => {
  return text
    .replace(/\s+/g, ' ') // Replace multiple spaces with single space
    .replace(/\n+/g, '\n') // Replace multiple newlines with single newline
    .trim();
};

module.exports = {
  extractTextFromDocument,
  preprocessText
};
