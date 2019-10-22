package com.csye6225.fall2019.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.csye6225.fall2019.datamodel.InMemoryDatabase;
import com.csye6225.fall2019.model.Course;
import com.csye6225.fall2019.model.Professor;
import com.csye6225.fall2019.model.Student;

public class CourseService {
	static Map<String, Course> courseMap = InMemoryDatabase.getCourseDB();
	static Map<String, Professor> profMap = InMemoryDatabase.getProfessorDB();
	static Map<String, Student> studentMap = InMemoryDatabase.getStudentDB();
	public CourseService() {
	}
	
	// Get all courses from system.
	public List<Course> getAllCourses() {
		List<Course> list = new ArrayList<>(courseMap.values());
		return list;
	}
	
	// Add a new course into system.
	// Each course has at least a professor and a TA.
	public Course addCourse(String courseName, String program, String professorId, String taId) {
		long nextAvailableId = courseMap.size() + 1;
		Professor professor = profMap.get(professorId);
		Student ta = studentMap.get(taId);
		if(professor == null) {
			System.out.println("Professor doesn't exist!");
			return null;
		}
		if(ta == null) {
			System.out.println("Student TA doesn't exist!");
			return null;
		}
		// Create a new course object.
		Course course = new Course(String.valueOf(nextAvailableId), 
				courseName, program, professor, ta);
		courseMap.put(String.valueOf(nextAvailableId), course);
		// Put this course in the teaching list of the professor.
		professor.getTeachCourses().add(course.id);
		return course;
	}
	
	// Get course by id.
	public Course getCourse(String courseId) {
		Course course = courseMap.get(courseId);
		if(course == null) {
			System.out.println("Course doesn't exist!");
		} else {
			System.out.println("Course retrieved:");
		    System.out.println(course.toString());
		}
		return course;
	}
	
	// Get course by professor name.
	public List<Course> getCoursesByProfessorName(String professorName) {
		List<Course> list = new ArrayList<>();
		for(Professor prof : profMap.values()) {
			if(prof.getName().equals(professorName)) {
				Set<String> teachingCourses = prof.getTeachCourses();
				for(String courseid : teachingCourses) {
					list.add(courseMap.get(courseid));
				}
			}
		}
		return list;
	}
	
	// Get courses by program name.
	public List<Course> getCoursesByProgram(String program) {
		List<Course> list = new ArrayList<>();
		for(Course course : courseMap.values()) {
			if(course.getProgram().equals(program)) {
				list.add(course);
			}
		}
		return list;
	}
	
	public Course enrollStudent(String courseId, String studentId) {
		Course course = courseMap.get(courseId);
		Student student = studentMap.get(studentId);
		if(course == null) {
			System.out.println("Course doesn't exist!");
			return null;
		}
		if(student == null) {
			System.out.println("Student doesn't exist!");
			return course;
		}
		course.addStudent(studentId);
		student.enroll(courseId);
		return course;
	}
	
	// Delete course by id.
	public Course deleteCourse(String courseId) {
		Course course = courseMap.get(courseId);
		if(course == null) {
			System.out.println("Course doesn't exist!");
			return null;
		}
		System.out.println("Course removing:");
		System.out.println(course.toString());
		// Remove course from course table
		courseMap.remove(courseId);
		// Remove course from professor's course list.
		Professor professor = profMap.get(course.getProfessor().getId());
		if(professor != null) {
			professor.getTeachCourses().remove(course.getId());
		}
		// Remove course reference from students' course list.
		for(String studentId : course.getStudents()) {
			studentMap.get(studentId).remove(courseId);
		}
		return course;
	}
	
	public Course updateCourseInformation(String courseId, Course course) {
		Course oldCourse = courseMap.get(courseId);
		if(oldCourse == null) {
			System.out.println("Course doesn't exist, can't update!");
			return null;
		}
		if(course.getName() != null) {
			oldCourse.setName(course.getName());
		}
		if(course.getProfessor() != null) {
			oldCourse.setProfessor(course.getProfessor());
		}
		if(course.getTa() != null) {
			oldCourse.setTa(course.getTa());
		}
		if(course.getProgram() != null) {
			oldCourse.setProgram(course.getProgram());
		}
		return oldCourse;
	}
	
	
	
}
