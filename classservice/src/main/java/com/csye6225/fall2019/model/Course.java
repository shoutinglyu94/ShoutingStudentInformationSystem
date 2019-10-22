package com.csye6225.fall2019.model;

import java.util.HashSet;
import java.util.Set;

public class Course {
	public String name;
	public String id;
	public String program;
	public Student ta;
	public Professor professor;
	public Set<String> students;
	
	public Course() {
		this.students = new HashSet<>();
	}
	
	public Course(String id, String name, String program, Professor prof, Student ta) {
		this.id = id;
		this.name = name;
		this.professor = prof;
		this.ta = ta;
		this.program = program;
		this.students = new HashSet<>();
	}
	
	public boolean addStudent(String studentId) {
		return this.students.add(studentId);
	}
	
	public boolean removeStudent(String studentId) {
		return this.students.remove(studentId);
	}
	
	public Set<String> getStudents() {
		return students;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getProgram() {
		return program;
	}

	public void setProgram(String program) {
		this.program = program;
	}

	public Student getTa() {
		return ta;
	}

	public void setTa(Student ta) {
		this.ta = ta;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	@Override
	public String toString() { 
		return "Course{" + "Id=" + this.getId() + ", Name=" + this.getName() + "}";
	}
}
