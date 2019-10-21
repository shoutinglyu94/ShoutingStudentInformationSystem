package com.csye6225.fall2019.model;

import java.util.HashSet;
import java.util.Set;

public class Professor extends Person {
	private Set<String> teachCourses;
	private String department;
	private String joiningDate;
	
	public Professor() {
		super();
	}
	
	public Professor(String id, String name, String department, String date) {
		super(id, name);
		this.teachCourses = new HashSet<>();
		this.department = department;
		this.joiningDate = date;
	}
	
	public boolean teach(String courseId) {
		return teachCourses.add(courseId);
	}

	public boolean remove(String courseId) {
		return teachCourses.remove(courseId);
	}

	public Set<String> getTeachCourses() {
		return teachCourses;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(String joiningDate) {
		this.joiningDate = joiningDate;
	}
	
	@Override
	public String toString() { 
		return "Professor{" + "Id=" + this.getId() + ", Name=" + this.getName()
				+ ", Department=" + this.getDepartment() + ", JoiningDate=" + this.getJoiningDate() + "}";
	}
}
