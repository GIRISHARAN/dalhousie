import React, { useState, useEffect } from 'react';

const HomePage = () => {
  const [questionLevel, setQuestionLevel] = useState('');
  const [questionCategory, setQuestionCategory] = useState('');
  const [questions, setQuestions] = useState([]);
  const [newQuestionVisible, setNewQuestionVisible] = useState(false);
  const [newQuestion, setNewQuestion] = useState({
    questionLevel: '',
    questionCategory: '',
    question: '',
    options: ['', ''],
    correctAnswer: ''
  });
  const [editingQuestion, setEditingQuestion] = useState(null);

  useEffect(() => {
    const fetchInitialQuestions = async () => {
      try {
        const response = await fetch('https://d8nbpcntna.execute-api.us-east-1.amazonaws.com/serverless/getquizquestions');
        const data = await response.json();
        if (response.ok) {
          setQuestions(data.questions);
        } else {
          console.log('Error:', data.error);
        }
      } catch (error) {
        console.log('Error:', error);
      }
    };

    fetchInitialQuestions();
  }, []);

  const handleQuestionLevelChange = (event) => {
    setQuestionLevel(event.target.value);
  };

  const handleQuestionCategoryChange = (event) => {
    setQuestionCategory(event.target.value);
  };

  const handleNewQuestionChange = (event) => {
    setNewQuestion((prevQuestion) => ({
      ...prevQuestion,
      [event.target.name]: event.target.value
    }));
  };

  const handleOptionChange = (index, value) => {
    setNewQuestion((prevQuestion) => {
      const options = [...prevQuestion.options];
      options[index] = value;
      return {
        ...prevQuestion,
        options
      };
    });
  };

  const handleAddOption = () => {
    setNewQuestion((prevQuestion) => ({
      ...prevQuestion,
      options: [...prevQuestion.options, '']
    }));
  };

  const handleDeleteOption = (index) => {
    setNewQuestion((prevQuestion) => {
      const options = [...prevQuestion.options];
      options.splice(index, 1);
      return {
        ...prevQuestion,
        options
      };
    });
  };

  const handleSelectSubmit = async () => {
    if (questionLevel && questionCategory) {
      try {
        const response = await fetch('https://d8nbpcntna.execute-api.us-east-1.amazonaws.com/serverless/getquizquestions', {
          method: 'POST',
          body: JSON.stringify({
            questionLevel,
            questionCategory
          }),
          headers: {
            'Content-Type': 'application/json'
          }
        });
        const data = await response.json();
        if (response.ok) {
          setQuestions(data.questions);
        } else {
          console.log('Error:', data.error);
        }
      } catch (error) {
        console.log('Error:', error);
      }
    }
  };

  const handleNewQuestionSubmit = async () => {
    if (
      newQuestion.questionLevel &&
      newQuestion.questionCategory &&
      newQuestion.question &&
      newQuestion.options.length >= 2 &&
      newQuestion.correctAnswer
    ) {
      try {
        const response = await fetch('https://d8nbpcntna.execute-api.us-east-1.amazonaws.com/serverless/addnewquestion', {
          method: 'POST',
          body: JSON.stringify({
            questionLevel: newQuestion.questionLevel,
            questionCategory: newQuestion.questionCategory,
            correctAnswer: newQuestion.correctAnswer,
            options: newQuestion.options.filter((option) => option !== ''),
            question: newQuestion.question
          }),
          headers: {
            'Content-Type': 'application/json'
          }
        });
        const data = await response.json();
        if (response.ok && data.statusCode === 200 && data.body === 'New question added successfully') {
          setQuestions((prevQuestions) => [...prevQuestions, newQuestion]);
          setNewQuestionVisible(false);
          setNewQuestion({
            questionLevel: '',
            questionCategory: '',
            question: '',
            options: ['', ''],
            correctAnswer: ''
          });
        } else {
          console.log('Error:', data.error);
        }
      } catch (error) {
        console.log('Error:', error);
      }
    }
  };

  const handleEdit = (question) => {
    setEditingQuestion(question);
    setNewQuestionVisible(true);
    setNewQuestion({
      uuid: question.uuid,
      questionLevel: question.questionLevel,
      questionCategory: question.questionCategory,
      question: question.question,
      options: question.options.slice(),
      correctAnswer: question.correctAnswer
    });
  };
  
  

  const handleUpdate = async () => {
    if (
      editingQuestion &&
      newQuestion.questionLevel &&
      newQuestion.questionCategory &&
      newQuestion.question &&
      newQuestion.options.length >= 2 &&
      newQuestion.correctAnswer
    ) {
      try {
        console.log(editingQuestion.uuidKey);
        const response = await fetch('https://d8nbpcntna.execute-api.us-east-1.amazonaws.com/serverless/edit', {
          method: 'PUT',
          body: JSON.stringify({
            uuid: editingQuestion.uuidKey, // Pass the UUID in the request body
            questionLevel: newQuestion.questionLevel,
            questionCategory: newQuestion.questionCategory,
            correctAnswer: newQuestion.correctAnswer,
            options: newQuestion.options.filter((option) => option !== ''),
            question: newQuestion.question
          }),
          headers: {
            'Content-Type': 'application/json'
          }
        });
        const data = await response.json();
        if (response.ok && data.statusCode === 200 && data.body === 'Question updated successfully') {
          const updatedQuestions = questions.map((question) => {
            if (question.uuidKey === editingQuestion.uuidKey) {
              return {
                uuidKey: question.uuidKey,
                questionCategory: newQuestion.questionCategory,
                questionLevel: newQuestion.questionLevel,
                correctAnswer: newQuestion.correctAnswer,
                options: newQuestion.options.filter((option) => option !== ''),
                question: newQuestion.question
              };
            }
            return question;
          });
          setQuestions(updatedQuestions);
          setNewQuestionVisible(false);
          setNewQuestion({
            questionLevel: '',
            questionCategory: '',
            question: '',
            options: ['', ''],
            correctAnswer: ''
          });
          setEditingQuestion(null);
        } else {
          console.log('Error:', data.error);
        }
      } catch (error) {
        console.log('Error:', error);
      }
    }
  };
  
  
  

  const handleDelete = async (uuid) => {
    try {
      const response = await fetch('https://d8nbpcntna.execute-api.us-east-1.amazonaws.com/serverless/getquizquestions', {
        method: 'DELETE',
        body: JSON.stringify({
          uuid,
          questionCategory
        }),
        headers: {
          'Content-Type': 'application/json'
        }
      });
      const data = await response.json();
      if (response.ok && data.statusCode === 200 && data.body === 'Item deleted successfully') {
        const updatedQuestions = questions.filter((question) => question.uuidKey !== uuid);
        setQuestions(updatedQuestions);
      } else {
        console.log('Error:', data.error);
      }
    } catch (error) {
      console.log('Error:', error);
    }
  };

  return (
    <div>
      <div>
        <label>
          Question Level:
          <select value={questionLevel} onChange={handleQuestionLevelChange} style={{ marginLeft: '0.5rem' }}>
            <option value="">Select</option>
            <option value="easy">Easy</option>
            <option value="hard">Hard</option>
          </select>
        </label>
      </div>
      <div>
        <label>
          Question Category:
          <select value={questionCategory} onChange={handleQuestionCategoryChange} style={{ marginLeft: '0.5rem' }}>
            <option value="">Select</option>
            <option value="space">Space</option>
            <option value="technology">Technology</option>
            <option value="cricket">Cricket</option>
            <option value="movies">Movies</option>
          </select>
        </label>
      </div>
      <div>
        <button onClick={handleSelectSubmit}>Submit</button>
        <button onClick={() => setQuestionLevel('')}>Reset</button>
      </div>
      <div>
        <button onClick={() => setNewQuestionVisible(!newQuestionVisible)}>
          {newQuestionVisible ? 'Cancel' : 'Add Question'}
        </button>
      </div>
      {newQuestionVisible && (
        <div>
          <h3>{editingQuestion ? 'Edit Question' : 'Add New Question'}</h3>
          <div>
            <label>
              Question Level:
              <select
                value={newQuestion.questionLevel}
                name="questionLevel"
                onChange={handleNewQuestionChange}
                style={{ marginLeft: '0.5rem' }}
              >
                <option value="">Select</option>
                <option value="easy">Easy</option>
                <option value="hard">Hard</option>
              </select>
            </label>
          </div>
          <div>
            <label>
              Question Category:
              <select
                value={newQuestion.questionCategory}
                name="questionCategory"
                onChange={handleNewQuestionChange}
                style={{ marginLeft: '0.5rem' }}
              >
                <option value="">Select</option>
                <option value="space">Space</option>
                <option value="technology">Technology</option>
                <option value="cricket">Cricket</option>
                <option value="movies">Movies</option>
              </select>
            </label>
          </div>
          <div>
            <label>
              Question:
              <input
                type="text"
                name="question"
                value={newQuestion.question}
                onChange={handleNewQuestionChange}
                style={{ marginLeft: '0.5rem' }}
              />
            </label>
          </div>
          <div>
            <label>
              Options:
              {newQuestion.options.map((option, index) => (
                <div key={index}>
                  <input
                    type="text"
                    value={option}
                    onChange={(event) => handleOptionChange(index, event.target.value)}
                    style={{ marginLeft: '0.5rem' }}
                  />
                  <button onClick={() => handleDeleteOption(index)}>-</button>
                </div>
              ))}
              <button onClick={handleAddOption}>+</button>
            </label>
          </div>
          <div>
            <label>
              Correct Answer:
              <input
                type="text"
                name="correctAnswer"
                value={newQuestion.correctAnswer}
                onChange={handleNewQuestionChange}
                style={{ marginLeft: '0.5rem' }}
              />
            </label>
          </div>
          <div>
            {editingQuestion ? (
              <button onClick={handleUpdate}>Update</button>
            ) : (
              <button onClick={handleNewQuestionSubmit}>Submit</button>
            )}
            <button onClick={() => setNewQuestionVisible(false)}>Cancel</button>
          </div>
        </div>
      )}
      <div>
        {questions.length > 0 && (
          <div>
            <h3>Questions:</h3>
            {questions.map((question) => (
              <div key={question.uuidKey}>
                <h4>UUID: {question.uuidKey}</h4>
                <p>Question: {question.question}</p>
                <p>Options:</p>
                <ul>
                  {question.options.map((option, index) => (
                    <li key={index}>{option}</li>
                  ))}
                </ul>
                <p>Correct Answer: {question.correctAnswer}</p>
                <button onClick={() => handleEdit(question)}>Edit</button>

                <button onClick={() => handleDelete(question.uuidKey)}>Delete</button>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
};

export default HomePage;
