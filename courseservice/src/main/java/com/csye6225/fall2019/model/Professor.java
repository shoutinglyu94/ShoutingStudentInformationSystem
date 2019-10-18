package com.csye6225.fall2019.model;

import java.util.HashSet;
import java.util.Set;

public class Professor extends Person {
	public Set<String> teachCourses;
	
	public Professor(String id, String name) {
		super(id, name);
		this.teachCourses = new HashSet<>();
	}
	
	public boolean teach(String courseId) {
		return teachCourses.add(courseId);
	}

	public boolean remove(String courseId) {
		return teachCourses.remove(courseId);
	}
}
