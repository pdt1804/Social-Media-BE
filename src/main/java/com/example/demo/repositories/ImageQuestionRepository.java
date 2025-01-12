package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.ImageQuestion;

@Repository
public interface ImageQuestionRepository extends JpaRepository<ImageQuestion, Long> {

}
