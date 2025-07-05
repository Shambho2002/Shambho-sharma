package com.InstituteManagementSystem.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.InstituteManagementSystem.model.Attendance;
import com.InstituteManagementSystem.model.Classroom;
import com.InstituteManagementSystem.model.Mark;
import com.InstituteManagementSystem.model.Student;
import com.InstituteManagementSystem.model.Subject;
import com.InstituteManagementSystem.model.Teacher;
import com.InstituteManagementSystem.repository.AttendanceRepository;
import com.InstituteManagementSystem.repository.MarkRepository;
import com.InstituteManagementSystem.repository.StudentRepository;
import com.InstituteManagementSystem.repository.SubjectRepository;
import com.InstituteManagementSystem.repository.TeacherRepository;

@Service
public class TeacherService {
	
	@Autowired
    private AttendanceRepository attendanceRepo;
    @Autowired
    private MarkRepository markRepo;
    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private TeacherRepository teacherRepo;
    
    @Autowired
    private SubjectRepository subjectRepo;

  
    public Attendance markAttendance(Attendance attendance) {
        return attendanceRepo.save(attendance);
    }

    public Mark addMark(Mark mark) {
        return markRepo.save(mark);
    }

    public List<Attendance> getClassAttendance(Long classId) {
        return attendanceRepo.findBySubjectClassroomId(classId);
    }
    public Teacher addTeacher(Teacher teacher) {
        return teacherRepo.save(teacher);
    }
    
    
    public Optional<Teacher> findByNameAndEmail(String name, String email) {
        return teacherRepo.findByNameAndEmail(name, email);
    }
    
    public List<Student> getStudentsByClassId(Long classId) {
        return studentRepo.findByClassroomId(classId);
        // Or however your Student-Classroom relationship is set up
    }
    public List<Subject> getSubjectsByTeacherId(Long teacherId) {
        return subjectRepo.findByTeacherId(teacherId); // implement this in repo
    }
    
    public List<Subject> getSubjectsByTeacherAndClassroom(Long teacherId, Long classroomId) {
        return subjectRepo.findByTeacherIdAndClassroomId(teacherId, classroomId);
    }

    public boolean hasMarkAlreadyEntered(Long studentId, Long subjectId, String examType) {
        return markRepo.findByStudentIdAndSubjectIdAndExamType(studentId, subjectId, examType) != null;
    }

    public List<Mark> getMarksByClassroomAndExamType(Long classId, String examType) {
        return markRepo.findBySubjectClassroomIdAndExamType(classId, examType);
    }

    public List<Mark> getMarksBySubjectAndExamType(Long subjectId, String examType) {
        return markRepo.findBySubjectIdAndExamType(subjectId, examType);
    }

    public Mark updateMark(Mark mark) {
        return markRepo.save(mark);
    }

    public void deleteMark(Long id) {
        markRepo.deleteById(id);
    }
    
    public List<Classroom> getAssignedClassrooms(Long teacherId) {
        List<Subject> subjects = subjectRepo.findByTeacherId(teacherId);
     
        return subjects.stream()
                       .map(Subject::getClassroom)
                       .distinct()
                       .collect(Collectors.toList());
    }
    
    public Mark getMarkById(Long markId) {
        return markRepo.findById(markId)
                .orElseThrow(() -> new RuntimeException("Mark not found with ID: " + markId));
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

public Optional<Attendance> findAttendanceByStudentSubjectAndDate(Long studentId, Long subjectId, LocalDate date) {
return attendanceRepo.findByStudentIdAndSubjectIdAndDate(studentId, subjectId, date);
}

public Attendance updateAttendance(Attendance attendance) {
return attendanceRepo.save(attendance);
}

public Student findStudentById(Long studentId) {
return studentRepo.findById(studentId)
        .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));
}

public Subject findSubjectById(Long subjectId) {
return subjectRepo.findById(subjectId)
        .orElseThrow(() -> new RuntimeException("Subject not found with ID: " + subjectId));
}

public List<Attendance> getAttendanceByClassSubjectAndMonth(Long classId, Long subjectId, int year, int month) {
LocalDate startDate = LocalDate.of(year, month, 1);
LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

return attendanceRepo.findBySubjectIdAndSubjectClassroomIdAndDateBetween(subjectId, classId, startDate, endDate);
}

public List<Attendance> getStudentAttendanceBySubjectAndMonth(Long studentId, Long subjectId, int year, int month) {
LocalDate startDate = LocalDate.of(year, month, 1);
LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

return attendanceRepo.findByStudentIdAndSubjectIdAndDateBetween(studentId, subjectId, startDate, endDate);
}

}
