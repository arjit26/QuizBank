package com.example.questionbank.ServiceLayer;

import com.example.questionbank.DaoLayer.QuestionDao;
import com.example.questionbank.DaoLayer.QuizDao;
import com.example.questionbank.Model.Question;
import com.example.questionbank.Model.QuestionWrapper;
import com.example.questionbank.Model.Quiz;
import com.example.questionbank.Model.Response;
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
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, int noOfQues, String title) {

        List<Question> questions = questionDao.findQuestionsByCategoryAndNoOfQues(category, noOfQues);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);
        return new ResponseEntity<>("success in saving create quiz", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {

        Optional<Quiz> quizList = quizDao.findById(id);
        List<Question> questionsfromDB = quizList.get().getQuestions();
        List<QuestionWrapper> questionWrappersForUsers = new ArrayList<>();
        for (Question q:questionsfromDB) {
            QuestionWrapper questionWrapper = new QuestionWrapper(q.getId(), q.getQuestion_title(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
            questionWrappersForUsers.add(questionWrapper);
        }
        return new ResponseEntity<>(questionWrappersForUsers, HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        Optional<Quiz> quiz = quizDao.findById(id);
        List<Question> questions = quiz.get().getQuestions();

//        Quiz quiz = quizDao.findById(id).get();
//        List<Question> questions = quiz.getQuestions();
        int rightAnswer = 0;
        int idOfQuestionsFromQuiz=0;
        for (Response response: responses) {
            if (response.getResponse().equals(questions.get(idOfQuestionsFromQuiz).getRight_answer())){
                rightAnswer++;
            }
            idOfQuestionsFromQuiz++;
        }

        return new ResponseEntity<>(rightAnswer, HttpStatus.FOUND);
    }
}
