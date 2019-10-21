package com.csye6225.fall2019.model;

import java.util.HashSet;
import java.util.Set;

public class Student extends Person{
	
	public String program;
	private String enrollDate;
	public Set<String> enrolledCourses;
	
	public Student() {
		super();
	}
	
	public Student(String id, String name, String programId, String date) {
		super(id, name);
		enrolledCourses = new HashSet<>();
		this.program = programId;
		this.enrollDate = date;
	}
	
	public boolean enroll(String courseId) {
		return enrolledCourses.add(courseId);
	}
	
	public boolean remove(String courseId) {
		return enrolledCourses.remove(courseId);
	}

	public String getProgram() {
		return program;
	}

	public void setProgram(String program) {
		this.program = program;
	}

	public Set<String> getEnrolledCourses() {
		return enrolledCourses;
	}

	public String getEnrollDate() {
		return enrollDate;
	}

	public void setEnrollDate(String enrollDate) {
		this.enrollDate = enrollDate;
	}
	
	@Override
	public String toString() { 
		return "Student{" + "Id=" + this.getId() + ", Name=" + this.getName()
				+ ", Program=" + this.getProgram() + ", JoiningDate=" + this.getEnrollDate() + "}";
	}

	
}
