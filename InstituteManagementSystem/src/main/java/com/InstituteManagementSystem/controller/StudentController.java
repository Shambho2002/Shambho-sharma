package com.InstituteManagementSystem.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.InstituteManagementSystem.model.Attendance;
import com.InstituteManagementSystem.model.Classroom;
import com.InstituteManagementSystem.model.Mark;
import com.InstituteManagementSystem.model.Student;
import com.InstituteManagementSystem.services.AdminService;
import com.InstituteManagementSystem.services.StudentService;

@Controller
@RequestMapping("/student")
public class StudentController {
	
	@Autowired
    private StudentService studentService;
    
    @Autowired
    private AdminService adminService;

    
    @GetMapping("/login")
    public String showStudentSignupForm() {
        return "student"; // Shows the signup form (student.html)
    }
    
    @GetMapping("/signin")
    public String showStudentSigninForm() {
        return "student_signin"; // Shows the login form
    }
    
    @PostMapping("/signin")
    public String loginStudent(@RequestParam String name,
                              @RequestParam String email,
                              Model model) {
        Optional<Student> studentOpt = studentService.findByNameAndEmail(name, email);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            return "redirect:/student/features?studentId=" + student.getId();
        } else {
            model.addAttribute("error", "Invalid credentials. Please try again.");
            return "student_signin";
        }
    }

    @PostMapping("/add")
    public String addStudent(@RequestParam("name") String name,
                            @RequestParam("classroom") Long classroomId, 
                            @RequestParam("email") String email,
                            Model model) {
        
        Student student = new Student();
        student.setName(name);
        student.setEmail(email);
        
        Classroom classroom = adminService.findById(classroomId);
        student.setClassroom(classroom);
        
        studentService.addStudent(student);
        
        Long studentId = student.getId();
        model.addAttribute("studentId", studentId);
        
        return "student_features";
    }
    
    @GetMapping("/features")
    public String studentFeaturePage(@RequestParam Long studentId, Model model) {
        model.addAttribute("studentId", studentId);
        return "student_features";
    }

    @GetMapping("/marks/view")
    public String studentViewMarks(@RequestParam Long studentId, Model model) {
        Student student = studentService.findById(studentId);
        model.addAttribute("student", student);
        model.addAttribute("studentId", studentId);
        model.addAttribute("examTypes", List.of("CA1", "CA2", "Midsem", "Final"));
        return "student_marks";
    }

    @GetMapping("/marks/list")
    @ResponseBody
    public List<Mark> getStudentMarks(@RequestParam Long classId, 
                                     @RequestParam String examType,
                                     @RequestParam Long studentId) {
        return studentService.getMarksByStudentClassroomAndExamType(studentId, classId, examType);
    }

    @ResponseBody
    @GetMapping("/{studentId}/attendance")
    public List<Attendance> getAttendance(@PathVariable Long studentId) {
        return studentService.getAttendanceByStudent(studentId);
    }

    @ResponseBody
    @GetMapping("/{studentId}/marks")
    public List<Mark> getMarks(@PathVariable Long studentId) {
        return studentService.getMarksByStudent(studentId);
    }

}
