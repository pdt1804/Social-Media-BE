package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.ListeningQuestion;

@Repository
public interface ListeningQuestionRepository extends JpaRepository<ListeningQuestion, Long> {

}
