package com.InstituteManagementSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.InstituteManagementSystem.model.Admin;
import com.InstituteManagementSystem.model.Classroom;
import com.InstituteManagementSystem.model.Institute;
import com.InstituteManagementSystem.model.Subject;
import com.InstituteManagementSystem.model.Teacher;
import com.InstituteManagementSystem.services.AdminService;

@Controller

@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
    private AdminService adminService;


   @GetMapping("/login")
    public String showAdminLoginForm() {
	   
	   
	   
        return "admin_login";
    }
   @GetMapping("/classrooms")
    public String getAllClassrooms(Model model) {
       List<Classroom> classroom = adminService.getAllClassrooms();
      
       model.addAttribute("classroom", classroom);
       return "classroom";
    }

    
    @GetMapping("/getteachers")
    public String getAllTeachers(Model model) {
        List<Teacher> teachers = adminService.getAllTeachers();
        List<Classroom> classrooms = adminService.getAllClassrooms();
        model.addAttribute("teachers", teachers);
        model.addAttribute("classroom", classrooms);
        		return"subject";
        				
    }
    
    
    
    @GetMapping("/institute")
    public String showInstituteForm() {
        return "institute";
    }

    
    @PostMapping("/institute")
    public String add_Institute(@RequestParam("universityname") String uName,
    		@RequestParam("name") String name,
    		@RequestParam("type") String type,
    		@RequestParam("adress") String adress,
    		@RequestParam("phone") String phone,
    		@RequestParam("email") String email,Model model) {
    	
    	Institute inst = new Institute();
    	inst.setAdress(adress);
    	inst.setEmail(email);
    	inst.setName(name);
    	inst.setPhone(phone);
    	inst.setType(type);
    	inst.setUniversityname(uName);
    	
    	
    	adminService.instAdd(inst);
				return "admin";
				
    	
    	
    }
    
    
   @PostMapping("/login_admin")
   public String login_admin(@RequestParam("name")String name,
		   @RequestParam("designation")String designation,
		   @RequestParam("email")String email ,
		   Model model
		
		  ) {
	   
	   Admin admin = new Admin();
	   admin.setName(name);
	   admin.setDesignstion(designation);
	   admin.setEmail(email);
	   
	   adminService.adminLogin(admin);
	   
	return "admin";
			}
 
  

  
    @PostMapping("/classroom")
    public String addClassroom(@RequestParam("classname")String name,
    		@RequestParam("instituteId") Long Id	,
    		Model model ) {
    	
    	Classroom classroom = new Classroom();
    	classroom.setName(name);
    	
    	
    	Institute institute = adminService.instFindbyId(Id);
    	classroom.setInstitute(institute);
    	
    	adminService.addClassroom(classroom);
    	
        return "redirect:/admin/classroom";
    }

 
    @PostMapping("/subject")
    public String addSubject(@RequestParam("subjname")String name,
    		@RequestParam("classroom")Long classroomId,
    		@RequestParam("teacher")Long teacherId,
    		Model model) {
    	
    	Subject subject = new Subject();
    	subject.setName(name);
    	
    	Classroom classroom = adminService.findById(classroomId);
    	subject.setClassroom(classroom);
    	Teacher teacher = adminService.findteacherById(teacherId);
    	subject.setTeacher(teacher);
    	
    	adminService.addSubject(subject);
    	
        return"redirect:/admin/subject";
    }
    


@ResponseBody
@PostMapping("/assign-teacher")
public ResponseEntity<String> assignTeacherToSubject(@RequestParam Long subjectId, @RequestParam Long teacherId) {
    adminService.assignTeacherToSubject(subjectId, teacherId);
    return ResponseEntity.ok("Teacher assigned successfully.");
}

}
