package com.csye6225.fall2019.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.csye6225.fall2019.datamodel.InMemoryDatabase;
import com.csye6225.fall2019.model.Professor;

public class ProfessorService {

	static Map<String, Professor> profMap = InMemoryDatabase.getProfessorDB();
	public ProfessorService() {
	}
	
	// Getting a list of all professor 
	// GET "..webapi/professors"
	public List<Professor> getAllProfessors() {	
		//Getting the list
		List<Professor> list = new ArrayList<>();
		for (Professor prof : profMap.values()) {
			list.add(prof);
		}
		return list;
	}

	// Adding a professor
	public Professor addProfessor(String name, String department, Date joiningDate) {
		// Next Id 
		long nextAvailableId = profMap.size() + 1;
		//Create a Professor Object
		Professor prof = new Professor(String.valueOf(nextAvailableId), name, 
				department, joiningDate.toString());
		profMap.put(String.valueOf(nextAvailableId), prof);
		return prof;
	}
	
	
	// Getting One Professor
	public Professor getProfessor(String profId) {
		 //Simple HashKey Load
		 Professor prof = profMap.get(profId);
	     System.out.println("Item retrieved:");
	     System.out.println(prof.toString());
		return prof;
	}
	
	// Deleting a professor
	public Professor deleteProfessor(String profId) {
		Professor deletedProfDetails = profMap.get(profId);
		profMap.remove(profId);
		return deletedProfDetails;
	}
	
	// Updating Professor Info
	public Professor updateProfessorInformation(String profId, Professor prof) {	
		Professor oldProfObject = profMap.get(profId);
		String oldProfId = oldProfObject.getId();
		prof.setId(oldProfId);
		return prof;
	}
	
	// Get professors in a department 
	public List<Professor> getProfessorsByDepartment(String department) {	
		//Getting the list
		ArrayList<Professor> list = new ArrayList<>();
		for (Professor prof : profMap.values()) {
			if (prof.getDepartment().equals(department)) {
				list.add(prof);
			}
		}
		return list ;
	}
}
