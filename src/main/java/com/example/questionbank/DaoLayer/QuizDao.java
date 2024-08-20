package com.example.questionbank.DaoLayer;

import com.example.questionbank.Model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizDao extends JpaRepository<Quiz, Integer> {

}
