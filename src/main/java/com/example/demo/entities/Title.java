package com.example.demo.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Title {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String titleName;
	
	@OneToMany(mappedBy = "title")
	private List<Question> questions = new ArrayList<>();
}
