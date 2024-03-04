package com.adenbofa.quizservice.service;

import com.adenbofa.quizservice.dao.QuizDao;
import com.adenbofa.quizservice.feign.QuizInterface;
import com.adenbofa.quizservice.model.QuestionWrapper;
import com.adenbofa.quizservice.model.Quiz;
import com.adenbofa.quizservice.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;
    @Autowired
    QuizInterface quizInterface;


    public ResponseEntity<Quiz> createQuiz(String category, int numQ, String title) {

        List<Integer> questions = quizInterface.getQuestionsForQuiz(category, numQ).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questions);
        quizDao.save(quiz);
        return new ResponseEntity<>(quiz, HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(int id) {
        Quiz quiz = quizDao.findById(id).get();
        List<Integer> questionIds = quiz.getQuestionIds();

       ResponseEntity<List<QuestionWrapper>> questions = quizInterface.getQuestionsFromId(questionIds);


        return new ResponseEntity<>(questions.getBody(), HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(int id, List<Response> responses) {
        ResponseEntity<Integer> score = quizInterface.getScore(responses);
        return new ResponseEntity<>(score.getBody(), HttpStatus.OK);
    }
}
