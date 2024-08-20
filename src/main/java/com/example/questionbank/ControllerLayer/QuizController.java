package com.example.questionbank.ControllerLayer;

import com.example.questionbank.Model.QuestionWrapper;
import com.example.questionbank.Model.Response;
import com.example.questionbank.ServiceLayer.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping("/create")
    public ResponseEntity<String> createQuiz(@RequestParam String category,@RequestParam int noOfQues,@RequestParam String title){
        return quizService.createQuiz(category, noOfQues, title);
    }

    @GetMapping("/getQuizQuestions/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestion(@PathVariable Integer id){
        return quizService.getQuizQuestions(id);
    }

    @PostMapping("submitAnswers/{id}")
    public ResponseEntity<Integer> submitAnswers(@PathVariable Integer id, @RequestBody List<Response> responses){
        return quizService.calculateResult(id, responses);
    }
}
