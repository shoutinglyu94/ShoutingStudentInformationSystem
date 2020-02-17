package com.csye6225.fall2019.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.csye6225.fall2019.data.DynamoDbConnector;
import com.csye6225.fall2019.model.Course;
import com.csye6225.fall2019.model.Professor;
import com.csye6225.fall2019.model.Student;

public class CourseService {

	static DynamoDbConnector dynamoDb;
	DynamoDBMapper mapper;

	public CourseService() {
		DynamoDbConnector.init();
		dynamoDb = new DynamoDbConnector();
		mapper = new DynamoDBMapper(dynamoDb.getClient());
	}

	// Get all courses from system.
	public List<Course> getAllCourses() {
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression().withConsistentRead(false).withLimit(10);
		List<Course> courses = new ArrayList<>();
		try {
			courses = mapper.scan(Course.class, scanExpression);
		} catch (AmazonServiceException ase) {
			System.err.println("Could not retrieve all courses.");
			System.err.println("Error Message:  " + ase.getMessage());
			System.err.println("AWS Error Code: " + ase.getErrorCode());
		} catch (AmazonClientException ace) {
			System.err.println("Internal error occurred communicating with DynamoDB");
			System.out.println("Error Message:  " + ace.getMessage());
		}
		return courses;
	}

	// Get course by id.
	public Course getCourse(String courseId) {
		Course course = null;
		try {
			course = mapper.load(Course.class, courseId);
		} catch (AmazonServiceException ase) {
			System.err.println("Could not retrieve the course by the id.");
			System.err.println("Error Message:  " + ase.getMessage());
			System.err.println("AWS Error Code: " + ase.getErrorCode());
		} catch (AmazonClientException ace) {
			System.err.println("Internal error occurred communicating with DynamoDB");
			System.out.println("Error Message:  " + ace.getMessage());
		}
		return course;
	}

	// Get courses by program name.
	public List<Course> getCoursesByProgram(String program) {
		Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		eav.put(":val1", new AttributeValue().withS(program));

		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
				.withFilterExpression("program = :val1")
				.withExpressionAttributeValues(eav);

		List<Course> courses = new ArrayList<>();
		try {
			courses = mapper.scan(Course.class, scanExpression);
		} catch (AmazonServiceException ase) {
			System.err.println("Could not retrieve courses by program.");
			System.err.println("Error Message:  " + ase.getMessage());
			System.err.println("AWS Error Code: " + ase.getErrorCode());
		} catch (AmazonClientException ace) {
			System.err.println("Internal error occurred communicating with DynamoDB");
			System.out.println("Error Message:  " + ace.getMessage());
		}
		return courses;
	}

	// Get course by professor name.
	public List<Course> getCoursesByProfessorId(String profId) {
		Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		eav.put(":val1", new AttributeValue().withS(profId));

		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
				.withFilterExpression("professor_id = :val1").withExpressionAttributeValues(eav);

		List<Course> courses = new ArrayList<>();
		try {
			courses = mapper.scan(Course.class, scanExpression);
		} catch (AmazonServiceException ase) {
			System.err.println("Could not retrieve courses by professor id.");
			System.err.println("Error Message:  " + ase.getMessage());
			System.err.println("AWS Error Code: " + ase.getErrorCode());
		} catch (AmazonClientException ace) {
			System.err.println("Internal error occurred communicating with DynamoDB");
			System.out.println("Error Message:  " + ace.getMessage());
		}
		return courses;
	}

	// Add a new course into system.
	// Each course must have at least a professor and a student TA.
	public Course addCourse(String courseName, String program, String professorId, String taId, Date curDate) {
		Course course = null;
		try {
			Professor professor = mapper.load(Professor.class, professorId);
			if (professor == null) {
				System.out.println("Professor doesn't exist!");
				return null;
			}
			Student ta = mapper.load(Student.class, taId);
			if (ta == null) {
				System.out.println("Student TA doesn't exist!");
				return null;
			}
			// Create a new course object and save it into the course table.
			course = new Course(courseName, program, professorId, taId, curDate.toString());
			mapper.save(course);
			// Add this course in the professor's teaching list.
			professor.getTeachCourses().add(course.getId());
			mapper.save(professor);
		} catch (AmazonServiceException ase) {
			System.err.println("Could not add a new course with a professor and a ta.");
			System.err.println("Error Message:  " + ase.getMessage());
			System.err.println("AWS Error Code: " + ase.getErrorCode());
		} catch (AmazonClientException ace) {
			System.err.println("Internal error occurred communicating with DynamoDB");
			System.out.println("Error Message:  " + ace.getMessage());
		}
		return course;
	}

	// Enroll a new student to a course.
	public Course enrollStudent(String courseId, String studentId) {
		try {
			Course course = mapper.load(Course.class, courseId);
			if (course == null) {
				System.out.println("Course doesn't exist!");
				return null;
			}
			Student student = mapper.load(Student.class, studentId);
			if (student == null) {
				System.out.println("Student doesn't exist!");
				return null;
			}
			
			if(!course.getStudents().contains(studentId)) {
				course.getStudents().add(studentId);
			}
			
			if(!student.getEnrolledCourses().contains(courseId)) {
				student.getEnrolledCourses().add(courseId);
			}
			
			mapper.save(course);
			mapper.save(student);
			return course;
		} catch (AmazonServiceException ase) {
			System.err.println("Could not enroll a student into a course.");
			System.err.println("Error Message:  " + ase.getMessage());
			System.err.println("AWS Error Code: " + ase.getErrorCode());
		} catch (AmazonClientException ace) {
			System.err.println("Internal error occurred communicating with DynamoDB");
			System.out.println("Error Message:  " + ace.getMessage());
		}
		return null;
	}
	
	public Course removeStudentFromCourse(String courseId, String studentId) {
		try {
			Course course = mapper.load(Course.class, courseId);
			if (course == null || !course.getStudents().contains(studentId)) {
				System.out.println("Course doesn't exist or course doesn't contain student!");
				return null;
			}
			Student student = mapper.load(Student.class, studentId);
			if (student == null || !student.getEnrolledCourses().contains(courseId)) {
				System.out.println("Student doesn't exist or student doesn't enroll the course!");
				return null;
			}
			course.getStudents().remove(studentId);
			student.getEnrolledCourses().remove(courseId);
			mapper.save(course);
			mapper.save(student);
			return course;
		} catch (AmazonServiceException ase) {
			System.err.println("Could not remove a student from a course.");
			System.err.println("Error Message:  " + ase.getMessage());
			System.err.println("AWS Error Code: " + ase.getErrorCode());
		} catch (AmazonClientException ace) {
			System.err.println("Internal error occurred communicating with DynamoDB");
			System.out.println("Error Message:  " + ace.getMessage());
		}
		return null;
	}

	// Delete course by id.
	public Course deleteCourse(String courseId) {
		try {
			Course course = mapper.load(Course.class, courseId);
			if (course == null) {
				System.out.println("Course doesn't exist!");
				return null;
			}
			// Delete course from each student who got enrolled this course.
			List<Student> modifiedStudents = new ArrayList<>();
			for(String studentId : course.getStudents()) {
				Student student = mapper.load(Student.class, studentId);
				if(student != null) {
					student.getEnrolledCourses().remove(courseId);
					modifiedStudents.add(student);
				}
			}
			mapper.batchSave(modifiedStudents);
			
			// Delete course from professor's teaching list.
			Professor professor = mapper.load(Professor.class, course.getProfId());
			if(professor != null) {
				professor.getTeachCourses().remove(courseId);
				mapper.save(professor);
			}
			
			// Delete course from course table.
			mapper.delete(course);
			
			return course;
		} catch (AmazonServiceException ase) {
			System.err.println("Could not delete a course with a course id.");
			System.err.println("Error Message:  " + ase.getMessage());
			System.err.println("AWS Error Code: " + ase.getErrorCode());
		} catch (AmazonClientException ace) {
			System.err.println("Internal error occurred communicating with DynamoDB");
			System.out.println("Error Message:  " + ace.getMessage());
		}
		return null;
	}

	public Course updateCourseInformation(String courseId, Course course) {
		try {
			Course oldCourse = mapper.load(Course.class, courseId);
			if (oldCourse == null) {
				System.out.println("Course doesn't exist!");
				return null;
			}
			// Students and professor id can not be change in this api.
			course.setProfId(oldCourse.getProfId());
			course.setStudents(oldCourse.getStudents());
			course.setId(oldCourse.getId());
			DynamoDBMapperConfig dynamoDBMapperConfig = new DynamoDBMapperConfig.Builder()
					  .withConsistentReads(DynamoDBMapperConfig.ConsistentReads.CONSISTENT)
					  .withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.UPDATE_SKIP_NULL_ATTRIBUTES)
					  .build();
			// Update the course with new information.
			mapper.save(course, dynamoDBMapperConfig);
			return course;
		} catch (AmazonServiceException ase) {
			System.err.println("Could not update the course.");
			System.err.println("Error Message:  " + ase.getMessage());
			System.err.println("AWS Error Code: " + ase.getErrorCode());

		} catch (AmazonClientException ace) {
			System.err.println("Internal error occurred communicating with DynamoDB");
			System.out.println("Error Message:  " + ace.getMessage());
		}
		return null;
	}

}
