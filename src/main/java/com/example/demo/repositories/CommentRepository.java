package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
