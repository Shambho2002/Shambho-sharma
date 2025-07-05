package com.InstituteManagementSystem.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Subject {
	
	@Id @GeneratedValue
    private Long id;
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    
   // @JsonBackReference
    @JsonBackReference("subject-teacher") // Add this
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.EAGER)
   // @JsonBackReference
    @JsonBackReference("subject-classroom") 
    private Classroom classroom;
    
    
    
    @OneToMany(mappedBy = "subject")
    @JsonBackReference("mark-subject") // Add this
    private List<Mark> marks;



	public Subject() {
		super();
	}



	public Subject(Long id, String name, Teacher teacher, Classroom classroom, List<Mark> marks) {
		super();
		this.id = id;
		this.name = name;
		this.teacher = teacher;
		this.classroom = classroom;
		this.marks = marks;
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



	public Teacher getTeacher() {
		return teacher;
	}



	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}



	public Classroom getClassroom() {
		return classroom;
	}



	public void setClassroom(Classroom classroom) {
		this.classroom = classroom;
	}



	public List<Mark> getMarks() {
		return marks;
	}



	public void setMarks(List<Mark> marks) {
		this.marks = marks;
	}

}
