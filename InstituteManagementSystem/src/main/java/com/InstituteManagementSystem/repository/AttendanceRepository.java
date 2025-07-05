package com.InstituteManagementSystem.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.InstituteManagementSystem.model.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Long>{
	
	List<Attendance> findByStudentId(Long studentId);
	  List<Attendance> findBySubjectClassroomId(Long classId);
	
	  

Optional<Attendance> findByStudentIdAndSubjectIdAndDate(Long studentId, Long subjectId, LocalDate date);

List<Attendance> findBySubjectIdAndSubjectClassroomIdAndDateBetween(Long subjectId, Long classroomId, LocalDate startDate, LocalDate endDate);

List<Attendance> findByStudentIdAndSubjectIdAndDateBetween(Long studentId, Long subjectId, LocalDate startDate, LocalDate endDate);

}
