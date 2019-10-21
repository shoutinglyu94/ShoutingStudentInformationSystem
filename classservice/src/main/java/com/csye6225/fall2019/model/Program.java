package com.csye6225.fall2019.model;

import java.util.HashSet;
import java.util.Set;

public class Program {
	public String id;
	public String name;
	public Set<String> courses;
	
	public Program(String id, String name) {
		this.id = id;
		this.name = name;
		this.courses = new HashSet<>();
	}
	
	public boolean enroll(String courseId) {
		if(courses.contains(courseId)) {
			return false;
		}
		courses.add(courseId);
		return true;
	}
	
	public boolean remove(String courseId) {
		return courses.remove(courseId);
	}
}
