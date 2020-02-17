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
import com.csye6225.fall2019.data.InMemoryDatabase;
import com.csye6225.fall2019.model.Professor;
import com.csye6225.fall2019.model.Student;

public class StudentService {
	// static Map<String, Student> studentMap = InMemoryDatabase.getStudentDB();
	static DynamoDbConnector dynamoDb;
	DynamoDBMapper mapper;

	public StudentService() {
		DynamoDbConnector.init();
		dynamoDb = new DynamoDbConnector();
		mapper = new DynamoDBMapper(dynamoDb.getClient());
	}

	// Get all students from the system.
	public List<Student> getAllStudents() {
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression().withConsistentRead(false).withLimit(10);
		List<Student> students = new ArrayList<>();
		try {
			students = mapper.scan(Student.class, scanExpression);
		} catch (AmazonServiceException ase) {
			System.err.println("Could not scan all students.");
			System.err.println("Error Message:  " + ase.getMessage());
			System.err.println("AWS Error Code: " + ase.getErrorCode());

		} catch (AmazonClientException ace) {
			System.err.println("Internal error occurred communicating with DynamoDB");
			System.out.println("Error Message:  " + ace.getMessage());
		}
		return students;
	}

	// Get a student by id.
	public Student getStudent(String studentId) {
		Student student = null;
		try {
			student = mapper.load(Student.class, studentId);
		} catch (AmazonServiceException ase) {
			System.err.println("Could not retrieve the student by the id.");
			System.err.println("Error Message:  " + ase.getMessage());
			System.err.println("AWS Error Code: " + ase.getErrorCode());

		} catch (AmazonClientException ace) {
			System.err.println("Internal error occurred communicating with DynamoDB");
			System.out.println("Error Message:  " + ace.getMessage());
		}
		return student;
	}

	// Search student by program name
	public List<Student> searchStudentByProgram(String program) {
		Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		eav.put(":val1", new AttributeValue().withS(program));

		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression().withFilterExpression("program = :val1")
				.withExpressionAttributeValues(eav);

		List<Student> students = new ArrayList<>();
		try {
			students = mapper.scan(Student.class, scanExpression);
		} catch (AmazonServiceException ase) {
			System.err.println("Could not scan students by program.");
			System.err.println("Error Message:  " + ase.getMessage());
			System.err.println("AWS Error Code: " + ase.getErrorCode());

		} catch (AmazonClientException ace) {
			System.err.println("Internal error occurred communicating with DynamoDB");
			System.out.println("Error Message:  " + ace.getMessage());
		}
		return students;
	}

	// Add a new student.
	public Student addStudent(String name, String program, Date enrollDate) {
		Student student = new Student(name, program, enrollDate.toString());
		try {
			// Save the new student to the table.
			mapper.save(student);
		} catch (AmazonServiceException ase) {
			System.err.println("Could not save a new student.");
			System.err.println("Error Message:  " + ase.getMessage());
			System.err.println("AWS Error Code: " + ase.getErrorCode());

		} catch (AmazonClientException ace) {
			System.err.println("Internal error occurred communicating with DynamoDB");
			System.out.println("Error Message:  " + ace.getMessage());
		}
		return student;
	}

	// Delete a student.
	public Student deleteStudent(String studentId) {
		Student student = getStudent(studentId);
		if (student == null) {
			System.out.println("Student doesn't exist!");
			return student;
		}
		try {
			mapper.delete(student);
		} catch (AmazonServiceException ase) {
			System.err.println("Could not delete the student.");
			System.err.println("Error Message:  " + ase.getMessage());
			System.err.println("AWS Error Code: " + ase.getErrorCode());

		} catch (AmazonClientException ace) {
			System.err.println("Internal error occurred communicating with DynamoDB");
			System.out.println("Error Message:  " + ace.getMessage());
		}
		return student;
	}

	public Student updateStudentInformation(String studentId, Student student) {
		Student oldStudent = getStudent(studentId);
		if (oldStudent == null) {
			System.out.println("Student doesn't exist!");
			return oldStudent;
		}
		DynamoDBMapperConfig dynamoDBMapperConfig = new DynamoDBMapperConfig.Builder()
				.withConsistentReads(DynamoDBMapperConfig.ConsistentReads.CONSISTENT)
				.withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.UPDATE_SKIP_NULL_ATTRIBUTES).build();
		student.setId(oldStudent.getId());
		try {
			// Update the professor with new information.
			mapper.save(student, dynamoDBMapperConfig);
		} catch (AmazonServiceException ase) {
			System.err.println("Could not update the student.");
			System.err.println("Error Message:  " + ase.getMessage());
			System.err.println("AWS Error Code: " + ase.getErrorCode());
		} catch (AmazonClientException ace) {
			System.err.println("Internal error occurred communicating with DynamoDB");
			System.out.println("Error Message:  " + ace.getMessage());
		}
		return student;
	}
}
