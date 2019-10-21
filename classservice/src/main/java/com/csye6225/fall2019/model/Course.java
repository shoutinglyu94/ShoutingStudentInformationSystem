package com.csye6225.fall2019.model;

import java.util.HashSet;
import java.util.Set;

public class Course {
	public String name;
	public String id;
	public String roster;
	public String board;
	public Set<String> tas;
	public Set<String> professors;
	public Set<String> students;
	
	public Course(String id, String name) {
		this.id = id;
		this.name = name;
		this.roster = null;
		this.board = null;
		this.tas = new HashSet<>();
		this.professors = new HashSet<>();
		this.students = new HashSet<>();
	}
	
	public boolean addStudent(String studentId) {
		return this.students.add(studentId);
	}
	
	public boolean addProfessor(String professorId) {
		return this.professors.add(professorId);
	}
	
	public boolean addTa(String studentId) {
		return this.tas.add(studentId);
	}
	
	public boolean removeStudent(String studentId) {
		return this.students.remove(studentId);
	}
	
	public boolean removeProfessor(String professorId) {
		return this.professors.remove(professorId);
	}
	
	public boolean removeTa(String studentId) {
		return this.tas.remove(studentId);
	}
	
	
}
