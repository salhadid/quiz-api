package com.cooksys.quiz_api.services;

import com.cooksys.quiz_api.dtos.QuestionRequestDto;
import com.cooksys.quiz_api.dtos.QuestionResponseDto;
import com.cooksys.quiz_api.dtos.QuizRequestDto;
import com.cooksys.quiz_api.dtos.QuizResponseDto;

import java.util.List;

public interface QuizService {

  QuizResponseDto createQuiz(QuizRequestDto quiz);

  List<QuizResponseDto> getAllQuizzes();

  QuizResponseDto updateQuizName(Long id, String newName);

  QuizResponseDto deleteQuiz(Long id);

  QuestionResponseDto randomQuestion(Long id);

  QuizResponseDto addQuestion(Long id, QuestionRequestDto question);

  QuestionResponseDto deleteQuestion(Long id, Long questionID);
}
