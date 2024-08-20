package com.example.questionbank.DaoLayer;

import com.example.questionbank.Model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionDao extends JpaRepository<Question, Integer> {

    List<Question> findQuestionsByCategory(String category);

    @Query(value = "SELECT * FROM quizbank.question q WHERE q.category=?1 ORDER BY RAND() LIMIT ?2", nativeQuery = true)
    List<Question> findQuestionsByCategoryAndNoOfQues(@Param("category") String category,@Param("noOfQues") int noOfQues);
}
