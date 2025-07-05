package com.InstituteManagementSystem.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Attendance {
	
	@Id @GeneratedValue
    private Long id;

    @ManyToOne
  //  @JsonIgnore
    private Student student;

    @ManyToOne
  //  @JsonIgnore
    private Subject subject;



    private LocalDate date;
    private boolean present;
	public Attendance() {
		super();
	}
	public Attendance(Long id, Student student, Subject subject,  LocalDate date,
			boolean present) {
		super();
		this.id = id;
		this.student = student;
		this.subject = subject;
		
		this.date = date;
		this.present = present;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public Subject getSubject() {
		return subject;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public boolean isPresent() {
		return present;
	}
	public void setPresent(boolean present) {
		this.present = present;
	}

}
