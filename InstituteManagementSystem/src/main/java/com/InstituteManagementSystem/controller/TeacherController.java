package com.InstituteManagementSystem.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.InstituteManagementSystem.model.Attendance;
import com.InstituteManagementSystem.model.Classroom;
import com.InstituteManagementSystem.model.Institute;
import com.InstituteManagementSystem.model.Mark;
import com.InstituteManagementSystem.model.Student;
import com.InstituteManagementSystem.model.Subject;
import com.InstituteManagementSystem.model.Teacher;
import com.InstituteManagementSystem.repository.StudentRepository;
import com.InstituteManagementSystem.repository.SubjectRepository;
import com.InstituteManagementSystem.services.AdminService;
import com.InstituteManagementSystem.services.TeacherService;

@Controller
@RequestMapping("/teacher")
public class TeacherController {
	
	@Autowired
	  private SubjectRepository subjectRepo;
	    
	    @Autowired
	    private StudentRepository studentRepo;
	    
	    @Autowired
	    private TeacherService teacherService;
	    
	    @Autowired
	    private AdminService adminService;
		
	    @GetMapping("/login")
	    public String showTeacherLoginForm() {
	        return "teacher"; 
	    }
	    
	    @GetMapping("/signin")
	    public String showTeacherSigninForm() {
	        return "teacher_signin";
	    }
	    
	    @PostMapping("/signin")
	    public String loginTeacher(@RequestParam String name,
	                              @RequestParam String email,
	                              Model model) {
	        Optional<Teacher> teacherOpt = teacherService.findByNameAndEmail(name, email);
	        if (teacherOpt.isPresent()) {
	            Teacher teacher = teacherOpt.get();
	            return "redirect:/teacher/features?teacherId=" + teacher.getId();
	        } else {
	            model.addAttribute("error", "Invalid credentials. Please try again.");
	            return "teacher_signin";
	        }
	    }

	    @PostMapping("/add")
	    public String addTeacher(@RequestParam("name") String name,
	                            @RequestParam("instituteId") Long Id,
	                            @RequestParam("email") String email, Model model) {
	        Teacher teacher = new Teacher();
	        teacher.setName(name);
	        teacher.setEmail(email);
	        
	        Institute institute = adminService.instFindbyId(Id);
	        teacher.setInstitute(institute);
	        
	        teacherService.addTeacher(teacher);
	        
	        Long teacherId = teacher.getId();
	        model.addAttribute("teacherId", teacherId);
	        model.addAttribute("classes", teacherService.getAssignedClassrooms(teacherId));
	        
	        return "teacher_features";
	    }

	    @ResponseBody
	    @PostMapping("/attendance")
	    public Attendance markAttendance(@RequestBody Attendance attendance) {
	        return teacherService.markAttendance(attendance);
	    }

	    @ResponseBody
	    @PostMapping("/mark")
	    public Mark addMark(@RequestBody Mark mark) {
	        return teacherService.addMark(mark);
	    }

	    @ResponseBody
	    @GetMapping("/class/{classId}/attendance")
	    public List<Attendance> getClassAttendance(@PathVariable Long classId) {
	        return teacherService.getClassAttendance(classId);
	    }
	    
	    @GetMapping("/features")
	    public String teacherFeatureScreen(Model model, @RequestParam Long teacherId) {
	        List<Classroom> classes = teacherService.getAssignedClassrooms(teacherId);
	        model.addAttribute("classes", classes);
	        model.addAttribute("teacherId", teacherId); 
	        return "teacher_features";
	    }

	    @GetMapping("/marks/add")
	    public String showAddMarksForm(@RequestParam Long teacherId, 
	                                   @RequestParam(required = false) Long classId, 
	                                   Model model) {
	        try {
	            // Get assigned classes for the teacher
	            List<Classroom> classList = teacherService.getAssignedClassrooms(teacherId);
	            
	            // Get subjects taught by teacher - simplified approach
	          //  List<Subject> subjectList = subjectRepo.findAll(); // or create custom method
	            
	            List<Subject> subjectList = new ArrayList<>();
	            List<Student> studentList = new ArrayList<>();
	            
	            // Get students based on selected class
	          //  List<Student> studentList = new ArrayList<>();
	            if (classId != null) {
	                // Find students by classroom - you may need to adjust this based on your entity relationships
	            	
	            	  subjectList = teacherService.getSubjectsByTeacherAndClassroom(teacherId, classId);
	            	  studentList = studentRepo.findByClassroomId(classId); 
	              //  studentList = studentRepo.findAll().stream()
	                //    .filter(s -> s.getClassroom() != null && s.getClassroom().getId().equals(classId))
	                //    .toList();
	            }

	            model.addAttribute("classList", classList);
	            model.addAttribute("subjectList", subjectList);
	            model.addAttribute("studentList", studentList);
	            model.addAttribute("examTypes", List.of("CA1", "CA2", "Midsem", "Final"));
	            model.addAttribute("teacherId", teacherId);
	            model.addAttribute("classId", classId);

	            return "teacher_marks";
	            
	        } catch (Exception e) {
	            model.addAttribute("error", "Error loading form: " + e.getMessage());
	            return "error"; // Create an error page or redirect
	        }
	    }

	    @PostMapping("/marks/add")
	    public String addMarkForStudent(@RequestParam Long studentId,
	                                    @RequestParam Long subjectId,
	                                    @RequestParam String examType,
	                                    @RequestParam Integer marksObtained,
	                                    
	                                    @RequestParam(required = false) Long classId,
	                                    @RequestParam Long teacherId,
	                                    RedirectAttributes redirectAttributes) {

	        try {
	            // Validate input parameters
	            if (studentId == null || subjectId == null || examType == null || examType.trim().isEmpty()) {
	                throw new RuntimeException("All fields are required");
	            }

	            // Find student
	            Student student = studentRepo.findById(studentId)
	                    .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));

	            // Find subject
	            Subject subject = subjectRepo.findById(subjectId)
	                    .orElseThrow(() -> new RuntimeException("Subject not found with ID: " + subjectId));

	            // Validate marks range
	            if (marksObtained < 0 || marksObtained > 100) {
	                throw new RuntimeException("Marks should be between 0 and 100");
	            }

	            // Create and save mark
	            Mark mark = new Mark();
	            mark.setStudent(student);
	            mark.setSubject(subject);
	            mark.setExamType(examType);
	            mark.setMarks(marksObtained);

	            teacherService.addMark(mark);
	            
	            redirectAttributes.addFlashAttribute("success", "Marks added successfully!");
	            
	        } catch (Exception e) {
	            redirectAttributes.addFlashAttribute("error", "Error adding marks: " + e.getMessage());
	        }

	        // Build redirect URL
	        String redirectUrl = "redirect:/teacher/marks/add?teacherId=" + teacherId;
	        if (classId != null) {
	            redirectUrl += "&classId=" + classId;
	        }
	        
	        return redirectUrl;
	    }

	    @GetMapping("/marks/view")
	    public String viewMarksPage(@RequestParam Long teacherId, Model model) {
	        List<Classroom> classes = teacherService.getAssignedClassrooms(teacherId);
	        model.addAttribute("classes", classes);
	        model.addAttribute("examTypes", List.of("CA1", "CA2", "Midsem", "Final"));
	        model.addAttribute("teacherId", teacherId);
	        return "teacher_view_marks";
	    }

	    @GetMapping("/marks/list")
	    @ResponseBody
	    public List<Mark> getMarks(@RequestParam Long classId, @RequestParam String examType) {
	        return teacherService.getMarksByClassroomAndExamType(classId, examType);
	    }

	    @PostMapping("/marks/edit")
	    public String editMark(@RequestParam Long markId, 
	                           @RequestParam Integer newMark,
	                           
	                           @RequestParam(required = false) Long teacherId,
	                           RedirectAttributes redirectAttributes) {
	        try {
	            Mark mark = teacherService.getMarkById(markId);
	            if (mark == null) {
	                throw new RuntimeException("Mark not found with ID: " + markId);
	            }
	            
	            // Validate new mark
	            if (newMark < 0 || newMark > 100) {
	                throw new RuntimeException("Marks should be between 0 and 100");
	            }
	            
	            mark.setMarks(newMark);
	            teacherService.updateMark(mark);
	            
	            redirectAttributes.addFlashAttribute("success", "Mark updated successfully!");
	            
	        } catch (Exception e) {
	            redirectAttributes.addFlashAttribute("error", "Error updating mark: " + e.getMessage());
	        }
	        
	        String redirectUrl = "redirect:/teacher/marks/view";
	        if (teacherId != null) {
	            redirectUrl += "?teacherId=" + teacherId;
	        }
	        return redirectUrl;
	    }

	    @PostMapping("/marks/delete")
	    public String deleteMark(@RequestParam Long markId,
	                            @RequestParam(required = false) Long teacherId,
	                            RedirectAttributes redirectAttributes) {
	        try {
	            teacherService.deleteMark(markId);
	            redirectAttributes.addFlashAttribute("success", "Mark deleted successfully!");
	        } catch (Exception e) {
	            redirectAttributes.addFlashAttribute("error", "Error deleting mark: " + e.getMessage());
	        }
	        
	        String redirectUrl = "redirect:/teacher/marks/view";
	        if (teacherId != null) {
	            redirectUrl += "?teacherId=" + teacherId;
	        }
	        return redirectUrl;
	    }
	    
	    
	    @GetMapping("/attendance/add")
	    public String showAddAttendanceForm(@RequestParam Long teacherId, 
	                                       @RequestParam(required = false) Long classId, 
	                                       Model model) {
	        try {
	            List<Classroom> classList = teacherService.getAssignedClassrooms(teacherId);
	            
	            List<Subject> subjectList = new ArrayList<>();
	            List<Student> studentList = new ArrayList<>();
	            
	            if (classId != null) {
	                subjectList = teacherService.getSubjectsByTeacherAndClassroom(teacherId, classId);
	                studentList = studentRepo.findByClassroomId(classId);
	            }

	            model.addAttribute("classList", classList);
	            model.addAttribute("subjectList", subjectList);
	            model.addAttribute("studentList", studentList);
	            model.addAttribute("teacherId", teacherId);
	            model.addAttribute("classId", classId);
	            model.addAttribute("todayDate", LocalDate.now());

	            return "teacher_add_attendance";
	            
	        } catch (Exception e) {
	            model.addAttribute("error", "Error loading form: " + e.getMessage());
	            return "error";
	        }
	    }

	    @PostMapping("/attendance/mark")
	    @ResponseBody
	    public Map<String, Object> markStudentAttendance(@RequestParam Long studentId,
	                                                    @RequestParam Long subjectId,
	                                                    @RequestParam boolean present,
	                                                    @RequestParam Long teacherId) {
	        Map<String, Object> response = new HashMap<>();
	        try {
	            LocalDate today = LocalDate.now();
	            
	            // Check if attendance already exists for today
	            Optional<Attendance> existingAttendance = teacherService.findAttendanceByStudentSubjectAndDate(studentId, subjectId, today);
	            
	            if (existingAttendance.isPresent()) {
	                // Update existing attendance
	                Attendance attendance = existingAttendance.get();
	                attendance.setPresent(present);
	                teacherService.updateAttendance(attendance);
	                response.put("success", true);
	                response.put("message", "Attendance updated successfully");
	                response.put("action", "updated");
	            } else {
	                // Create new attendance
	                Student student = teacherService.findStudentById(studentId);
	                Subject subject = teacherService.findSubjectById(subjectId);
	                
	                Attendance attendance = new Attendance();
	                attendance.setStudent(student);
	                attendance.setSubject(subject);
	                attendance.setDate(today);
	                attendance.setPresent(present);
	                
	                teacherService.markAttendance(attendance);
	                response.put("success", true);
	                response.put("message", "Attendance marked successfully");
	                response.put("action", "created");
	            }
	        } catch (Exception e) {
	            response.put("success", false);
	            response.put("message", "Error marking attendance: " + e.getMessage());
	        }
	        
	        return response;
	    }

	    @GetMapping("/attendance/check")
	    @ResponseBody
	    public Map<String, Object> checkExistingAttendance(@RequestParam Long studentId, 
	                                                      @RequestParam Long subjectId) {
	        Map<String, Object> response = new HashMap<>();
	        try {
	            LocalDate today = LocalDate.now();
	            Optional<Attendance> attendance = teacherService.findAttendanceByStudentSubjectAndDate(studentId, subjectId, today);
	            
	            if (attendance.isPresent()) {
	                response.put("exists", true);
	                response.put("present", attendance.get().isPresent());
	            } else {
	                response.put("exists", false);
	            }
	        } catch (Exception e) {
	            response.put("exists", false);
	            response.put("error", e.getMessage());
	        }
	        
	        return response;
	    }

	    @GetMapping("/attendance/view")
	    public String viewAttendancePage(@RequestParam Long teacherId, Model model) {
	        List<Classroom> classes = teacherService.getAssignedClassrooms(teacherId);
	        model.addAttribute("classes", classes);
	        model.addAttribute("teacherId", teacherId);
	        return "teacher_view_attendance";
	    }

	    @GetMapping("/attendance/monthly")
	    @ResponseBody
	    public Map<String, Object> getMonthlyAttendance(@RequestParam Long classId,
	                                                   @RequestParam Long subjectId,
	                                                   @RequestParam int year,
	                                                   @RequestParam int month) {
	        Map<String, Object> response = new HashMap<>();
	        try {
	            List<Attendance> attendanceList = teacherService.getAttendanceByClassSubjectAndMonth(classId, subjectId, year, month);
	            
	            // Group attendance by student
	            Map<Long, List<Attendance>> studentAttendanceMap = attendanceList.stream()
	                .collect(Collectors.groupingBy(att -> att.getStudent().getId()));
	            
	            List<Map<String, Object>> studentData = new ArrayList<>();
	            
	            for (Map.Entry<Long, List<Attendance>> entry : studentAttendanceMap.entrySet()) {
	                Map<String, Object> studentInfo = new HashMap<>();
	                List<Attendance> studentAttendances = entry.getValue();
	                
	                if (!studentAttendances.isEmpty()) {
	                    Student student = studentAttendances.get(0).getStudent();
	                    studentInfo.put("studentId", student.getId());
	                    studentInfo.put("studentName", student.getName());
	                    
	                    long presentCount = studentAttendances.stream().mapToLong(att -> att.isPresent() ? 1 : 0).sum();
	                    long absentCount = studentAttendances.size() - presentCount;
	                    
	                    studentInfo.put("totalPresent", presentCount);
	                    studentInfo.put("totalAbsent", absentCount);
	                    studentInfo.put("attendanceRecords", studentAttendances);
	                    
	                    studentData.add(studentInfo);
	                }
	            }
	            
	            response.put("success", true);
	            response.put("studentData", studentData);
	            
	        } catch (Exception e) {
	            response.put("success", false);
	            response.put("message", "Error fetching attendance: " + e.getMessage());
	        }
	        
	        return response;
	    }
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    @GetMapping("/attendance/student-detail")
	    @ResponseBody
	    public Map<String, Object> getStudentMonthlyAttendance(@RequestParam Long studentId,
	                                                          @RequestParam Long subjectId,
	                                                          @RequestParam int year,
	                                                          @RequestParam int month) {
	        Map<String, Object> response = new HashMap<>();
	        try {
	            List<Attendance> attendanceList = teacherService.getStudentAttendanceBySubjectAndMonth(studentId, subjectId, year, month);
	            
	            response.put("success", true);
	            response.put("attendanceData", attendanceList);
	            
	            long presentCount = attendanceList.stream().mapToLong(att -> att.isPresent() ? 1 : 0).sum();
	            long absentCount = attendanceList.size() - presentCount;
	            
	            response.put("totalPresent", presentCount);
	            response.put("totalAbsent", absentCount);
	            
	        } catch (Exception e) {
	            response.put("success", false);
	            response.put("message", "Error fetching student attendance: " + e.getMessage());
	        }
	        
	        return response;
	    }
	    
	    
	    
	    
	    
	    
	    
	    @GetMapping("/get-students")
	    public String getStudents(@RequestParam Long classId, Model model) {
	        List<Student> students = studentRepo.findByClassroomId(classId);
	        model.addAttribute("studentList", students);
	        return "fragments/student_list :: studentListFragment";
	    }

	    @GetMapping("/get-subjects")
	    @ResponseBody
	    public List<Subject> getSubjects(@RequestParam Long classId, @RequestParam Long teacherId) {
	        return teacherService.getSubjectsByTeacherAndClassroom(teacherId, classId);
	    }

	  



}
