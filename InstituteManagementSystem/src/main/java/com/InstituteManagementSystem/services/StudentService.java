package com.InstituteManagementSystem.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.InstituteManagementSystem.model.Attendance;
import com.InstituteManagementSystem.model.Mark;
import com.InstituteManagementSystem.model.Student;
import com.InstituteManagementSystem.model.Subject;
import com.InstituteManagementSystem.repository.AttendanceRepository;
import com.InstituteManagementSystem.repository.MarkRepository;
import com.InstituteManagementSystem.repository.StudentRepository;
import com.InstituteManagementSystem.repository.SubjectRepository;

@Service
public class StudentService {
	
	@Autowired
    private StudentRepository studentRepo;

    @Autowired
    private AttendanceRepository attendanceRepo;
    
    @Autowired
    private MarkRepository markRepo;
    

    public List<Attendance> getAttendanceByStudent(Long studentId) {
        return attendanceRepo.findByStudentId(studentId);
    }

    public List<Mark> getMarksByStudent(Long studentId) {
        return markRepo.findByStudentId(studentId);
    }
    
    public Student addStudent(Student student) {
        return studentRepo.save(student);
    }
    
    public Optional<Student> findByNameAndEmail(String name, String email) {
        return studentRepo.findByNameAndEmail(name, email);
    }
    
    public Student findById(Long studentId) {
        return studentRepo.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));
    }
    
    public List<Mark> getMarksByStudentClassroomAndExamType(Long studentId, Long classId, String examType) {
        return markRepo.findByStudentIdAndSubjectClassroomIdAndExamType(studentId, classId, examType);
    }
    
   
    
    public boolean existsById(Long id) {
        return studentRepo.existsById(id);
    }
    
    
@Autowired
private SubjectRepository subjectRepo;

public List<Subject> getSubjectsByStudent(Long studentId) {
    Student student = findById(studentId);
    return subjectRepo.findByClassroomId(student.getClassroom().getId());
}

public List<Attendance> getStudentAttendanceBySubjectAndMonth(Long studentId, Long subjectId, int year, int month) {
    LocalDate startDate = LocalDate.of(year, month, 1);
    LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
    
    return attendanceRepo.findByStudentIdAndSubjectIdAndDateBetween(studentId, subjectId, startDate, endDate);
}

}
