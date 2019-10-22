package com.csye6225.fall2019.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.csye6225.fall2019.datamodel.InMemoryDatabase;
import com.csye6225.fall2019.model.Student;

public class StudentService {
	static Map<String, Student> studentMap = InMemoryDatabase.getStudentDB();
	public StudentService() {
	}
	
	// Get all students from the system.
	public List<Student> getAllStudents() {
		List<Student> students = new ArrayList<>();
		for(Student student : studentMap.values()) {
			students.add(student);
		}
		return students;
	}
	
	// Add a new student in the system.
	public Student addStudent(String name, String program, Date enrollDate) {
		long nextAvailableId = studentMap.size() + 1;
		Student student = new Student(String.valueOf(nextAvailableId), name, program, enrollDate.toString());
		studentMap.put(String.valueOf(nextAvailableId), student);
		return student;
	}
	
	// Delete a student.
	public Student deleteStudent(String id) {
		Student student = studentMap.remove(id);
		if(student == null) {
			System.out.println("Student doesn't exist!");
		} else {
			System.out.println("Student removing:");
		    System.out.println(student.toString());
		}
	    return student;
	}
	
	// Get a student by id.
	public Student getStudent(String id) {
		Student student = studentMap.get(id);
		if(student == null) {
			System.out.println("Student doesn't exist!");
		} else {
			System.out.println("Student retrieving:");
		    System.out.println(student.toString());
		}
	    return student;
	}
	
	// Search student by program name
	public List<Student> searchStudentByProgram(String program) {
		List<Student> students = new ArrayList<>();
		for(Student student : studentMap.values()) {
			if(student.getProgram().equals(program)) {
				students.add(student);
			}
		}
		return students;
	}
	
	public Student updateStudentInformation(String studentId, Student student) {
		Student oldStudent = studentMap.get(studentId);
		if(oldStudent == null) {
			System.out.println("Student doesn't exist, can't update!");
			return null;
		}
		if(student.getName() != null) {
			oldStudent.setName(student.getName());
		}
		if(student.getProgram() != null) {
			oldStudent.setProgram(student.getProgram());
		}
		if(student.getImageUrl()!= null) {
			oldStudent.setImageUrl(student.getImageUrl());
		}
		return oldStudent;
	}
}
