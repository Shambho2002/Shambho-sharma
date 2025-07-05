package com.InstituteManagementSystem.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Mark {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id", nullable = false)
    @JsonManagedReference("mark-student") // Add this
    private Student student;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id", nullable = false)
    @JsonManagedReference("mark-subject") // Add this
    private Subject subject;

    @Column(name = "marks", nullable = false)
    private int marks;
    
    @Column(name = "exam_type", nullable = false)
    private String examType; // "CA1", "CA2", "Midsem", "Final"

    // Default constructor
    public Mark() {
        super();
    }

    // Constructor without ID (for creating new marks)
    public Mark(Student student, Subject subject, int marks, String examType) {
        this.student = student;
        this.subject = subject;
        this.marks = marks;
        this.examType = examType;
    }

    // Constructor with ID (for existing marks)
    public Mark(Long id, Student student, Subject subject, int marks, String examType) {
        this.id = id;
        this.student = student;
        this.subject = subject;
        this.marks = marks;
        this.examType = examType;
    }

    // Getters and Setters
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

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    // Optional: Override toString for debugging
    @Override
    public String toString() {
        return "Mark{" +
                "id=" + id +
                ", student=" + (student != null ? student.getName() : "null") +
                ", subject=" + (subject != null ? subject.getName() : "null") +
                ", marks=" + marks +
                ", examType='" + examType + '\'' +
                '}';
    }

}
