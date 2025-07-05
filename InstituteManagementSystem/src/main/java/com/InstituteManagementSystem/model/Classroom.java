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
public class Classroom {
	
	@Id @GeneratedValue
    private Long id;
    private String name;

    @OneToMany(mappedBy = "classroom")
  //  @JsonBackReference
    @JsonManagedReference("student-classroom") // Add this
    private List<Student> students;

    @OneToMany(mappedBy = "classroom")
  //  @JsonBackReference
    @JsonManagedReference("subject-classroom") // Add this
    private List<Subject> subjects;
    
    

@ManyToOne
@JoinColumn(name = "institute")
private Institute institute;



public Classroom() {
super();
}



public Classroom(Long id, String name, List<Student> students, List<Subject> subjects, Institute institute) {
super();
this.id = id;
this.name = name;
this.students = students;
this.subjects = subjects;
this.institute = institute;
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



public List<Student> getStudents() {
return students;
}



public void setStudents(List<Student> students) {
this.students = students;
}



public List<Subject> getSubjects() {
return subjects;
}



public void setSubjects(List<Subject> subjects) {
this.subjects = subjects;
}



public Institute getInstitute() {
return institute;
}



public void setInstitute(Institute institute) {
this.institute = institute;
}

}
