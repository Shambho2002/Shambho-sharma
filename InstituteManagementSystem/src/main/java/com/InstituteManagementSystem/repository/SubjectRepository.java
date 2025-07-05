package com.InstituteManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.InstituteManagementSystem.model.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long>{
	
	List<Subject> findByTeacherIdAndClassroomId(Long teacherId, Long classroomId);
	List<Subject> findByTeacherId(Long teacherId);
    
    List<Subject> findByClassroomId(Long classroomId);

}
