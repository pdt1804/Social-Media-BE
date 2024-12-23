package com.example.demo.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long ID;
	@Lob
	private String Content;
	private Date dateSent;
    private List<String> images = new ArrayList<>();
	private MessageUserStatus status;
    @ManyToOne
    @JoinColumn(name = "sent_username")
    @JsonIgnore
	private User sentUser;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "received_username")
	private User receivedUser;
    
}
