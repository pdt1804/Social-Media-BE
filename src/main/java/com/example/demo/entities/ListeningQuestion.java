package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListeningQuestion {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String video;
	private String question;
	private String answerA;
	private String answerB;
	private String answerC;
	private String answerD;
	private String correctAnswer;
	
}
