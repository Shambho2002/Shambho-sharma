package com.InstituteManagementSystem.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.InstituteManagementSystem.model.Admin;
import com.InstituteManagementSystem.model.Classroom;
import com.InstituteManagementSystem.model.Institute;
import com.InstituteManagementSystem.model.Subject;
import com.InstituteManagementSystem.model.Teacher;
import com.InstituteManagementSystem.repository.AdminRepository;
import com.InstituteManagementSystem.repository.ClassroomRepository;
import com.InstituteManagementSystem.repository.InstituteRepository;
import com.InstituteManagementSystem.repository.SubjectRepository;
import com.InstituteManagementSystem.repository.TeacherRepository;

@Service
public class AdminService {
	
	@Autowired
    private TeacherRepository teacherRepo;
    @Autowired
    private ClassroomRepository classroomRepo;
    @Autowired
    private SubjectRepository subjectRepo;
    @Autowired
    private AdminRepository adminRepo;
    @Autowired
    private  InstituteRepository instRepo;
    
    
    
    public Institute instAdd(Institute institute) {
		return instRepo.save(institute);
    	
    }
    
    public Institute instFindbyId(Long id) {
		return instRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Institute not found with id: " + id));
		
    	
		
    }
    
    public Admin adminLogin(Admin admin) {
		return adminRepo.save(admin);
		
    	
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepo.findAll();
    }

    public List<Classroom> getAllClassrooms() {
        return classroomRepo.findAll();
    }

    public Classroom addClassroom(Classroom classroom) {
        return classroomRepo.save(classroom);
    }

    public Subject addSubject(Subject subject) {
        return subjectRepo.save(subject);
    }
    
    
    public Classroom findById(Long id) {
    	
        return classroomRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Classroom not found with id: " + id));
    }
 
    
    public Teacher findteacherById(Long id) {
        return teacherRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher Id not found with id: " + id));
    }
 
    
    
  

    public void assignTeacherToSubject(Long subjectId, Long teacherId) {
        Subject subject = subjectRepo.findById(subjectId).orElseThrow();
        Teacher teacher = teacherRepo.findById(teacherId).orElseThrow();
        subject.setTeacher(teacher);
        subjectRepo.save(subject);
    }

}
