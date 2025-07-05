package com.InstituteManagementSystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Student {
	
	@Id @GeneratedValue
    private Long id;
    private String name;
    private String email;

    @ManyToOne
  // @JsonBackReference
    @JsonBackReference("student-classroom") 
    private Classroom classroom;

    
    
    
 


	public Student() {
		super();
	}




	public Student(Long id, String name, String email, Classroom classroom) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.classroom = classroom;
	
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




	public Classroom getClassroom() {
		return classroom;
	}




	public void setClassroom(Classroom classroom) {
		this.classroom = classroom;
	}

}
