package com.csye6225.fall2019.model;

import java.util.HashSet;
import java.util.Set;

public class Student extends Person{
	
	public String program;
	public Set<String> enrolledCourses;
	
	public Student(String id, String name, String programId) {
		super(id, name);
		enrolledCourses = new HashSet<>();
		this.program = programId;
	}
	
	public boolean enroll(String courseId) {
		return enrolledCourses.add(courseId);
	}
	
	public boolean remove(String courseId) {
		return enrolledCourses.remove(courseId);
	}
}
