package com.csye6225.fall2019.datamodel;

import java.util.HashMap;
import java.util.Map;

import com.csye6225.fall2019.model.*;

public class InMemoryDatabase {

	private static Map<String, Professor> professorDB = new HashMap<> ();
	private static Map<String, Student> studentDB = new HashMap<>();
	private static Map<String, Course> courseDB = new HashMap<>();
	private static Map<String, Program> programDB = new HashMap<>();
	
	public static Map<String, Professor> getProfessorDB() {
		return professorDB;
	}

	public static Map<String, Student> getStudentDB() {
		return studentDB;
	}

	public static Map<String, Program> getProgramDB() {
		return programDB;
	}
	
	public static Map<String, Course> getCourseDB() {
		return courseDB;
	}
	
}
