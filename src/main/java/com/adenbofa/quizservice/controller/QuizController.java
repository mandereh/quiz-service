package com.adenbofa.quizservice.controller;

import com.adenbofa.quizservice.model.QuestionWrapper;
import com.adenbofa.quizservice.model.Quiz;
import com.adenbofa.quizservice.model.QuizDto;
import com.adenbofa.quizservice.model.Response;
import com.adenbofa.quizservice.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<Quiz> create(@RequestBody QuizDto quizDto){
        return quizService.createQuiz(quizDto.getCategoryName(), quizDto.getNumQuestions(), quizDto.getTitle());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable int id){
        return quizService.getQuizQuestions(id);
    }

    @PostMapping("/submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable int id, @RequestBody List<Response> responses){
        return quizService.calculateResult(id, responses);
    }
}
