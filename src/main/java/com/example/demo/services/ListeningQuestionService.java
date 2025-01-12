package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.ListeningQuestion;
import com.example.demo.repositories.ListeningQuestionRepository;

import jakarta.annotation.PostConstruct;

@Service
public class ListeningQuestionService {

	@Autowired
	private ListeningQuestionRepository listeningQuestionRepository;
	
	public List<ListeningQuestion> getAllListeningQuestion() {
		return listeningQuestionRepository.findAll();
	}
	
	@PostConstruct
	private void generateQuestion() {
		if (listeningQuestionRepository.findAll().size() == 0) {
			listeningQuestionRepository.save(new ListeningQuestion(1, "https://www.youtube.com/watch?v=f7KX1AwgZ3w", "What is the main content of the video ?", "A girl is talking about her interests", "A girl is teaching how to play tennis", "A girl would like to go to cinema with her friend", "None of them", "A"));
			listeningQuestionRepository.save(new ListeningQuestion(2, "https://www.youtube.com/watch?v=ry9SYnV3svc", "What is the working of the man in the video ?", "08:30 - 17:30", "09:00 - 18:00", "Another working time", "The video does not provide this information", "C"));
		}
	}
}
