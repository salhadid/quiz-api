package com.cooksys.quiz_api.controllers;

import com.cooksys.quiz_api.dtos.QuestionRequestDto;
import com.cooksys.quiz_api.dtos.QuestionResponseDto;
import com.cooksys.quiz_api.dtos.QuizRequestDto;
import com.cooksys.quiz_api.dtos.QuizResponseDto;
import com.cooksys.quiz_api.services.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/quiz")
public class QuizController {

  private final QuizService quizService;

  @GetMapping
  public List<QuizResponseDto> getAllQuizzes() {
    return quizService.getAllQuizzes();
  }
  
  // TODO: Implement the remaining 6 endpoints from the documentation.

  // check minute 12 in DTO video
  @PostMapping
  public QuizResponseDto createQuiz(@RequestBody QuizRequestDto quiz) {
    return quizService.createQuiz(quiz);
  }

  @PatchMapping("/{id}/rename/{newName}")
  public QuizResponseDto updateQuizName(@PathVariable Long id, @PathVariable String newName) {
    return quizService.updateQuizName(id, newName);
  }

  @DeleteMapping("/{id}")
  public QuizResponseDto deleteQuiz(@PathVariable Long id) {
    return quizService.deleteQuiz(id);
  }

  @GetMapping("/{id}/random")
  public QuestionResponseDto randomQuestion(@PathVariable Long id) {
    return quizService.randomQuestion(id);
  }

  @PatchMapping("{id}/add")
  public QuizResponseDto addQuestion(@PathVariable Long id, @RequestBody QuestionRequestDto question) {
    return quizService.addQuestion(id, question);
  }

  @DeleteMapping("{id}/delete/{questionID}")
  public QuestionResponseDto deleteQuestion(@PathVariable Long id, @PathVariable Long questionID) {
    return quizService.deleteQuestion(id, questionID);
  }


}
