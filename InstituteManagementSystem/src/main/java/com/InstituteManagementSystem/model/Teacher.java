package com.InstituteManagementSystem.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Teacher {
	
	@Id @GeneratedValue
    private Long id;
    private String name;
    private String email;
    
    
    @ManyToOne
    @JoinColumn(name = "institute")
    private Institute institute;


    
    @OneToMany(mappedBy = "teacher")
    @JsonManagedReference("subject-teacher") // Add this
    private List<Subject> subjects;



	public Teacher() {
		super();
	}



	public Teacher(Long id, String name, String email, Institute institute, List<Subject> subjects) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.institute = institute;
		this.subjects = subjects;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public Institute getInstitute() {
		return institute;
	}



	public void setInstitute(Institute institute) {
		this.institute = institute;
	}



	public List<Subject> getSubjects() {
		return subjects;
	}



	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}

}
