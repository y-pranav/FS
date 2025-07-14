const { GoogleGenerativeAI } = require('@google/generative-ai');

// Initialize Gemini client
const genAI = new GoogleGenerativeAI(process.env.GEMINI_API_KEY);
const model = genAI.getGenerativeModel({ model: "gemini-1.5-flash" });

// Generate document summary
const generateSummary = async (documentText) => {
  try {
    const prompt = `
Please provide a comprehensive summary of the following document. 
Focus on the main points, key findings, and important information.
Keep the summary concise but informative (around 200-300 words).

Document:
${documentText}

Summary:`;

    const result = await model.generateContent(prompt);
    return result.response.text();
  } catch (error) {
    throw new Error(`Failed to generate summary: ${error.message}`);
  }
};

// Answer questions about the document
const answerQuestions = async (documentText, questions) => {
  try {
    const answers = [];
    
    for (const questionObj of questions) {
      const question = questionObj.question;
      
      const prompt = `
Based on the following document, please answer this question accurately and thoroughly.
If the information is not available in the document, please state that clearly.

Document:
${documentText}

Question: ${question}

Answer:`;

      const result = await model.generateContent(prompt);
      const answer = result.response.text();
      
      // Simple confidence scoring based on answer quality
      const confidence = calculateConfidence(answer, documentText);
      
      answers.push({
        question: question,
        answer: answer,
        confidence: confidence
      });
    }
    
    return answers;
  } catch (error) {
    throw new Error(`Failed to answer questions: ${error.message}`);
  }
};

// Generate email draft
const generateEmailDraft = async (documentText, summary, questionsAndAnswers, recipient = '') => {
  try {
    const qaText = questionsAndAnswers
      .map(qa => `Q: ${qa.question}\nA: ${qa.answer}`)
      .join('\n\n');
    
    const prompt = `
Please generate a professional email draft based on the following information:

Document Summary:
${summary}

Questions and Answers:
${qaText}

Please create an email that:
1. Has an appropriate subject line
2. Includes a professional greeting
3. Summarizes the key points from the document
4. Addresses the questions and answers in a clear format
5. Ends with a professional closing
6. Is well-structured and easy to read

Generate the email in the following format:
SUBJECT: [subject line]

BODY:
[email body content]`;

    const result = await model.generateContent(prompt);
    const emailContent = result.response.text();
    
    // Parse the generated email
    const subjectMatch = emailContent.match(/SUBJECT:\s*(.+)/);
    const bodyMatch = emailContent.match(/BODY:\s*([\s\S]+)/);
    
    return {
      subject: subjectMatch ? subjectMatch[1].trim() : 'Document Analysis Results',
      body: bodyMatch ? bodyMatch[1].trim() : emailContent,
      recipient: recipient
    };
  } catch (error) {
    throw new Error(`Failed to generate email draft: ${error.message}`);
  }
};

// Simple confidence calculation
const calculateConfidence = (answer, documentText) => {
  // Simple heuristic: check if answer contains "not available", "don't know", etc.
  const lowConfidenceTerms = [
    'not available', 'don\'t know', 'cannot find', 'not mentioned', 
    'unclear', 'insufficient information', 'not specified'
  ];
  
  const answerLower = answer.toLowerCase();
  const hasLowConfidenceTerms = lowConfidenceTerms.some(term => 
    answerLower.includes(term)
  );
  
  if (hasLowConfidenceTerms) {
    return 0.3; // Low confidence
  }
  
  // Check if answer references the document content
  const answerWords = answerLower.split(/\s+/);
  const documentWords = documentText.toLowerCase().split(/\s+/);
  const overlap = answerWords.filter(word => 
    word.length > 3 && documentWords.includes(word)
  ).length;
  
  const overlapRatio = overlap / Math.min(answerWords.length, 50);
  
  if (overlapRatio > 0.1) {
    return 0.8; // High confidence
  } else {
    return 0.6; // Medium confidence
  }
};

module.exports = {
  generateSummary,
  answerQuestions,
  generateEmailDraft
};
