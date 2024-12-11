package com.example.demo.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "group_studying")
@Getter
@Setter
public class GroupStudying {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int groupID;
	@Lob
	private byte[] imageGroup;
	private String nameGroup;
	private Date dateCreated;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user_name", nullable = true)
	private User leaderOfGroup;
	@JsonIgnore
	@ManyToMany(mappedBy = "groups", fetch = FetchType.LAZY)
    private List<User> users = new ArrayList<>();
	@OneToMany(mappedBy = "groupStudying")
	private List<Notifycation> notifycations = new ArrayList<>();
	@OneToMany(mappedBy = "group")
	private List<Document> documents = new ArrayList<>();
	@OneToMany(mappedBy = "group")
	private List<MessageGroup> messages = new ArrayList<>();
	
	public GroupStudying(int groupID, byte[] imageGroup, String nameGroup, Date dateCreated, Set<User> users) {
		super();
		this.groupID = groupID;
		this.imageGroup = imageGroup;
		this.nameGroup = nameGroup;
		this.dateCreated = dateCreated;
	}
	
	public GroupStudying()
	{
		
	}
}
