package com.misra.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.misra.model.Student;
import com.misra.service.StudentService;

@Controller
public class StudentController {
	@Autowired
	private StudentService studentSerice;
	
	@RequestMapping("/index")
	public String setupFrom(Map<String, Object> map){
		Student student = new Student();
		map.put("student", student);
		map.put("studentList", studentSerice.getAllStudents());
		return "student";
	}
	
	@RequestMapping(value="/student.do", method=RequestMethod.POST)
	public String doActions(@ModelAttribute Student student, BindingResult result, @RequestParam String action, Map<String, Object> map){
		Student studentResult = new Student();
		switch (action.toLowerCase()) {
		case "add":
			studentSerice.add(student);
			studentResult = student;
			break;
		case "edit":
			studentSerice.edit(student);
			studentResult = student;
			break;
		case "delete":
			studentSerice.delete(student.getStudentId());
			studentResult = new Student();
			break;
		case "search":
			Student searchedStudent = studentSerice.getStudent(student.getStudentId());
			studentResult = searchedStudent!=null ?searchedStudent :new Student();
			break;
		}
		map.put("student", studentResult);
		map.put("studentList", studentSerice.getAllStudents());
		return "student";
	}
}
