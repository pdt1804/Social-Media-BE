package com.example.demo.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reply {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long replyID;
	@Column(columnDefinition = "TEXT")
	private String Content;
	private Date dateReplied;
	@ManyToOne
	@JoinColumn(name = "user_reply_ID", nullable = true)
	private User userReplied;
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "comment_reply_ID", nullable = true)
	private Comment comment;
	@OneToMany(mappedBy = "reply", cascade = CascadeType.ALL)
	private List<File> files = new ArrayList<>();
}
