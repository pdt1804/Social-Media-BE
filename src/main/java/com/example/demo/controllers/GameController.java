package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.ImageQuestion;
import com.example.demo.entities.ListeningQuestion;
import com.example.demo.services.ImageQuestionService;
import com.example.demo.services.ListeningQuestionService;

@RestController
@RequestMapping("/api/v1/game")
public class GameController {

	@Autowired
	private ImageQuestionService imageQuestionService;
	
	@Autowired
	private ListeningQuestionService listeningQuestionService;
	
	@GetMapping("/getAllImageQuestions")
	public List<ImageQuestion> getAllImageQuestions() {
		return imageQuestionService.getAllImagesQuestion();
	}
	
	@GetMapping("/getAllListeningQuestions")
	public List<ListeningQuestion> getAllListeningQuestions() {
		return listeningQuestionService.getAllListeningQuestion();
	}
}
