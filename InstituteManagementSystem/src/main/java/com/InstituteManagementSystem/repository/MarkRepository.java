package com.InstituteManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.InstituteManagementSystem.model.Mark;

public interface MarkRepository extends JpaRepository<Mark, Long>{
	
	@Query("SELECT m FROM Mark m WHERE m.student.id = :studentId")
    List<Mark> findByStudentId(@Param("studentId") Long studentId);
    
    @Query("SELECT m FROM Mark m WHERE m.subject.classroom.id = :classId AND m.examType = :examType")
    List<Mark> findBySubjectClassroomIdAndExamType(@Param("classId") Long classId, @Param("examType") String examType);
    
    List<Mark> findBySubjectIdAndExamType(Long subjectId, String examType);
    
    Mark findByStudentIdAndSubjectIdAndExamType(Long studentId, Long subjectId, String examType);
    
    @Query("SELECT m FROM Mark m WHERE m.subject.classroom.id = :classId")
    List<Mark> findBySubjectClassroomId(@Param("classId") Long classId);
    
    @Query("SELECT m FROM Mark m WHERE m.student.id = :studentId AND m.subject.classroom.id = :classId AND m.examType = :examType")
    List<Mark> findByStudentIdAndSubjectClassroomIdAndExamType(@Param("studentId") Long studentId, @Param("classId") Long classId, @Param("examType") String examType);

}
