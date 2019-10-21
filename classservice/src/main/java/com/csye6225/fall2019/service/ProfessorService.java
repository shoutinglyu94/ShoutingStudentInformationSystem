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
	
	// Get all professor from the system.
	public List<Professor> getAllProfessors() {	
		List<Professor> list = new ArrayList<>();
		for (Professor prof : profMap.values()) {
			list.add(prof);
		}
		return list;
	}

	// Add a new professor.
	public Professor addProfessor(String name, String department, Date joiningDate) {
		// Next id. 
		long nextAvailableId = profMap.size() + 1;
		//Create a Professor Object.
		Professor prof = new Professor(String.valueOf(nextAvailableId), name, 
				department, joiningDate.toString());
		profMap.put(String.valueOf(nextAvailableId), prof);
		return prof;
	}
	
	
	// Get a Professor by id.
	public Professor getProfessor(String profId) {
		 Professor professor = profMap.get(profId);
		 if(professor == null) {
			 System.out.println("Professor doesn't exist!");
		 } else {
			 System.out.println("Professor retrieved:");
		     System.out.println(professor.toString());
		 }
	     return professor;
	}
	
	// Delete a professor by id.
	public Professor deleteProfessor(String profId) {
		Professor professor = profMap.get(profId);
		
		if(professor == null) {
			System.out.println("Professor doesn't exist!");
		} else {
			System.out.println("Professor removing:");
			System.out.println(professor.toString());
		}
		profMap.remove(profId);
		return professor;
	}
	
	// Update professor information.
	public Professor updateProfessorInformation(String profId, Professor prof) {	
		Professor oldProfObject = profMap.get(profId);
		String oldProfId = oldProfObject.getId();
		prof.setId(oldProfId);
		profMap.put(oldProfId, prof);
		return prof;
	}
	
	// Search professors in a department 
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
