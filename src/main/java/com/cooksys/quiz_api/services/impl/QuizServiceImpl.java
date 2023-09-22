package com.cooksys.quiz_api.services.impl;

import com.cooksys.quiz_api.dtos.QuestionRequestDto;
import com.cooksys.quiz_api.dtos.QuestionResponseDto;
import com.cooksys.quiz_api.dtos.QuizRequestDto;
import com.cooksys.quiz_api.dtos.QuizResponseDto;
import com.cooksys.quiz_api.entities.Answer;
import com.cooksys.quiz_api.entities.Question;
import com.cooksys.quiz_api.entities.Quiz;
import com.cooksys.quiz_api.mappers.QuestionMapper;
import com.cooksys.quiz_api.mappers.QuizMapper;
import com.cooksys.quiz_api.repositories.AnswerRepository;
import com.cooksys.quiz_api.repositories.QuestionRepository;
import com.cooksys.quiz_api.repositories.QuizRepository;
import com.cooksys.quiz_api.services.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

  private final QuizRepository quizRepository;
  private final QuizMapper quizMapper;
  private final QuestionRepository questionRepository;
  private final AnswerRepository answerRepository;
  private final QuestionMapper questionMapper;

  @Override
  public List<QuizResponseDto> getAllQuizzes() {
    return quizMapper.entitiesToDtos(quizRepository.findAll());
  }

  @Override
  public QuizResponseDto updateQuizName(Long id, String newName) {
    Optional<Quiz> optionalQuiz = quizRepository.findById(id);

    if (optionalQuiz.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz not found");
    }

    Quiz quizToUpdate = optionalQuiz.get();
    quizToUpdate.setName(newName);

    return quizMapper.entityToDto(quizRepository.saveAndFlush(quizToUpdate));


  }

  // this probably needs modification
  @Override
  public QuizResponseDto createQuiz(QuizRequestDto quiz) {

    Quiz quizToSave = quizMapper.dtoToEntity(quiz);

    quizToSave = quizRepository.saveAndFlush(quizToSave);

    for (Question question : quizToSave.getQuestions()) {
      question.setQuiz(quizToSave);
      questionRepository.saveAndFlush(question);

      for (Answer answer : question.getAnswers()) {
        answer.setQuestion(question);
        answerRepository.saveAndFlush(answer);
      }
    }

    return quizMapper.entityToDto(quizToSave);
  }

  @Override
  public QuizResponseDto deleteQuiz(Long id) {
    Optional<Quiz> optionalQuiz = quizRepository.findById(id);
    if (optionalQuiz.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz not found");
    }
    Quiz quizToDelete = optionalQuiz.get();

    for (Question question : quizToDelete.getQuestions()) {
      for (Answer answer : question.getAnswers()) {
        answerRepository.delete((answer));
      }
      questionRepository.delete(question);
    }

    quizRepository.delete(quizToDelete);

    return quizMapper.entityToDto(quizToDelete);

  }

  @Override
  public QuestionResponseDto randomQuestion(Long id) {
    Optional<Quiz> optionalQuiz = quizRepository.findById(id);

    if (optionalQuiz.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz not found");
    }

    Quiz quiz = optionalQuiz.get();

    List<Question> questions = new ArrayList<>(quiz.getQuestions());

    Question randomQuestion = questions.get((int) (Math.random() * questions.size()));

    return questionMapper.entityToDto(randomQuestion);

  }

  @Override
  public QuizResponseDto addQuestion(Long id, QuestionRequestDto question) {
    Optional<Quiz> optionalQuiz = quizRepository.findById(id);

    if (optionalQuiz.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz not found");
    }

    Quiz quiz = optionalQuiz.get();

    Question questionToSave = questionMapper.dtoToEntity(question);

    questionToSave.setQuiz(quiz);

    questionRepository.saveAndFlush(questionToSave);

    for (Answer answer : questionToSave.getAnswers()) {
      answer.setQuestion(questionToSave);
      answerRepository.saveAndFlush(answer);
    }

    return quizMapper.entityToDto(quizRepository.saveAndFlush(quiz));

  }

  @Override
  public QuestionResponseDto deleteQuestion(Long id, Long questionID) {
    Optional<Quiz> optionalQuiz = quizRepository.findById(id);
    if (optionalQuiz.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz not found");
    }
    Quiz quiz = optionalQuiz.get();

    Optional<Question> optionalQuestion = questionRepository.findById(questionID);
    if (optionalQuestion.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Question not found");
    }
    Question question = optionalQuestion.get();

    answerRepository.deleteAll(question.getAnswers());

    quiz.getQuestions().remove(question);

    quizRepository.saveAndFlush(quiz);

    questionRepository.delete(question);

    return questionMapper.entityToDto(question);

  }

}


//  @Override
//  public QuizResponseDto deleteQuiz(Long id) {
//    Optional<Quiz> optionalQuiz = quizRepository.findById(id);
//    if (optionalQuiz.isEmpty()) {
//      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz not found");
//    }
//    Quiz quizToDelete = optionalQuiz.get();
//
//    for (Question question : quizToDelete.getQuestions()) {
//      for (Answer answer : question.getAnswers()) {
//        answerRepository.delete((answer));
//      }
//      questionRepository.delete(question);
//    }
//
//    quizRepository.delete(quizToDelete);
//
//    return quizMapper.entityToDto(quizToDelete);
//
//  }