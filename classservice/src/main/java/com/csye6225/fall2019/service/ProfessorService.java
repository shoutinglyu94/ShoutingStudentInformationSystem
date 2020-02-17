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
import com.csye6225.fall2019.model.Professor;

public class ProfessorService {

//	static Map<String, Course> courseMap = InMemoryDatabase.getCourseDB();
//	static Map<String, Professor> profMap = InMemoryDatabase.getProfessorDB();

	static DynamoDbConnector dynamoDb;
	DynamoDBMapper mapper;

	public ProfessorService() {
		DynamoDbConnector.init();
		dynamoDb = new DynamoDbConnector();
		mapper = new DynamoDBMapper(dynamoDb.getClient());
	}

	// Add a new professor.
	public Professor addProfessor(String name, String department, Date joiningDate) {
		// Create a professor object.
		Professor prof = new Professor(name, department, joiningDate.toString());
		try {
			// Save the new professor to the table.
			mapper.save(prof);
		} catch (AmazonServiceException ase) {
			System.err.println("Could not save a new professor.");
			System.err.println("Error Message:  " + ase.getMessage());
			System.err.println("AWS Error Code: " + ase.getErrorCode());

		} catch (AmazonClientException ace) {
			System.err.println("Internal error occurred communicating with DynamoDB");
			System.out.println("Error Message:  " + ace.getMessage());
		}
		return prof;
	}

	// Get all professors from the database.
	public List<Professor> getAllProfessors() {
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression().withConsistentRead(false).withLimit(10);
		List<Professor> professors = new ArrayList<>();
		try {
			professors = mapper.scan(Professor.class, scanExpression);
		} catch (AmazonServiceException ase) {
			System.err.println("Could not scan all professors.");
			System.err.println("Error Message:  " + ase.getMessage());
			System.err.println("AWS Error Code: " + ase.getErrorCode());

		} catch (AmazonClientException ace) {
			System.err.println("Internal error occurred communicating with DynamoDB");
			System.out.println("Error Message:  " + ace.getMessage());
		}
		return professors;
	}

	// Query a professor by an id.
	public Professor getProfessor(String profId) {
		Professor professor = null;
		try {
			professor = mapper.load(Professor.class, profId);
		} catch (AmazonServiceException ase) {
			System.err.println("Could not retrieve the professor by the id.");
			System.err.println("Error Message:  " + ase.getMessage());
			System.err.println("AWS Error Code: " + ase.getErrorCode());
		} catch (AmazonClientException ace) {
			System.err.println("Internal error occurred communicating with DynamoDB");
			System.out.println("Error Message:  " + ace.getMessage());
		}
		return professor;
	}

	// Search professors in a department
	public List<Professor> getProfessorsByDepartment(String department) {
		Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withS(department));
        
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
            .withFilterExpression("department = :val1").withExpressionAttributeValues(eav);

		List<Professor> professors = new ArrayList<>();
		try {
			professors = mapper.scan(Professor.class, scanExpression);
		} catch (AmazonServiceException ase) {
			System.err.println("Could not scan all professors by department.");
			System.err.println("Error Message:  " + ase.getMessage());
			System.err.println("AWS Error Code: " + ase.getErrorCode());

		} catch (AmazonClientException ace) {
			System.err.println("Internal error occurred communicating with DynamoDB");
			System.out.println("Error Message:  " + ace.getMessage());
		}
		return professors;
	}

	// Delete a professor by id.
	public Professor deleteProfessor(String profId) {
		try {
			Professor professor = mapper.load(Professor.class, profId);
			if(professor == null) {
				System.out.println("Professor doesn't exist!");
				return null;
			}
			if(!professor.getTeachCourses().isEmpty()) {
				System.out.println("Please delete or update the teaching professor of courses"
						+ "before delete this professor!");
				return null;
			}
			mapper.delete(professor);
		} catch (AmazonServiceException ase) {
			System.err.println("Could not delete the professor");
			System.err.println("Error Message:  " + ase.getMessage());
			System.err.println("AWS Error Code: " + ase.getErrorCode());

		} catch (AmazonClientException ace) {
			System.err.println("Internal error occurred communicating with DynamoDB");
			System.out.println("Error Message:  " + ace.getMessage());
		}
		return null;
	}

	// Update professor information.
	public Professor updateProfessorInformation(String profId, Professor prof) {
		Professor professor = getProfessor(profId);
		if (professor == null) {
			System.out.println("Professor doesn't exist!");
			return professor;
		}
		prof.setId(professor.getId());
		
		try {
			DynamoDBMapperConfig dynamoDBMapperConfig = new DynamoDBMapperConfig.Builder()
					  .withConsistentReads(DynamoDBMapperConfig.ConsistentReads.CONSISTENT)
					  .withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.UPDATE_SKIP_NULL_ATTRIBUTES)
					  .build();
			// Update the professor with new information.
			mapper.save(prof, dynamoDBMapperConfig);
		} catch (AmazonServiceException ase) {
			System.err.println("Could not update the professor.");
			System.err.println("Error Message:  " + ase.getMessage());
			System.err.println("AWS Error Code: " + ase.getErrorCode());

		} catch (AmazonClientException ace) {
			System.err.println("Internal error occurred communicating with DynamoDB");
			System.out.println("Error Message:  " + ace.getMessage());
		}
		
		return prof;
	}

}
