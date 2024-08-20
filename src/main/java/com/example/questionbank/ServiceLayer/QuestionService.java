package com.example.questionbank.ServiceLayer;

import com.example.questionbank.DaoLayer.QuestionDao;
import com.example.questionbank.Model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;
    public ResponseEntity<List<Question>> getAllQuestions(){
        try {
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.FOUND);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<List<Question>> getAllQuestionsByCategory(String category) {
        try {
            return new ResponseEntity<>(questionDao.findQuestionsByCategory(category), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestion(Question question) {
        try {
            questionDao.save(question);
            return new ResponseEntity<>("Successfully added", HttpStatus.CREATED);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Cannot add question", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> updateQuestion(int id, Question question) {
        try {
            if (questionDao.existsById(id)){
                Question existingQuestion = questionDao.findById(id).orElse(null);
                if (existingQuestion != null){
                    existingQuestion.setQuestion_title(question.getQuestion_title());
                    existingQuestion.setOption1(question.getOption1());;
                    existingQuestion.setOption2(question.getOption2());
                    existingQuestion.setOption3(question.getOption3());
                    existingQuestion.setOption4(question.getOption4());
                    existingQuestion.setRight_answer(question.getRight_answer());
                    existingQuestion.setDifficulty_level(question.getDifficulty_level());
                    existingQuestion.setCategory(question.getCategory());

                    questionDao.save(existingQuestion);
                    return new ResponseEntity<>("Updated Successfully", HttpStatus.OK);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("id not found", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> deleteQuestion(int id) {
        try {
            if (questionDao.existsById(id)){
                questionDao.deleteById(id);
                return new ResponseEntity<>("Successfully Deleted", HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<>("ID not found", HttpStatus.BAD_GATEWAY);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Cannot be deleted", HttpStatus.BAD_GATEWAY);
    }
}
