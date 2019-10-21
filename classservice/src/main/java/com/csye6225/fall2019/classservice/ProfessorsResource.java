package com.csye6225.fall2019.classservice;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.csye6225.fall2019.model.Professor;
import com.csye6225.fall2019.service.ProfessorService;

@Path("professors")
public class ProfessorsResource {

	ProfessorService profService = new ProfessorService();
	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProfessors() {
		List<Professor> list = profService.getAllProfessors();
		GenericEntity<List<Professor>> entity = new GenericEntity<List<Professor>>(list) {};
		return Response.status(Status.OK).entity(entity).build();
	}	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProfessorsByDeparment(
			@QueryParam("department") String department) {
		List<Professor> list = null;
		if (department == null) {
			list = profService.getAllProfessors();
		} else {
			list = profService.getProfessorsByDepartment(department);
		}
		GenericEntity<List<Professor>> entity = new GenericEntity<List<Professor>>(list) {};
		return Response.status(Status.OK).entity(entity).build();
	}
	
	// ... webapi/professor/1 
	@GET
	@Path("/{professorId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Professor getProfessor(@PathParam("professorId") String profId) {
		System.out.println("Professor Resource: Looking for: " + profId);
		return profService.getProfessor(profId);
	}
	
	@DELETE
	@Path("/{professorId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Professor deleteProfessor(@PathParam("professorId") String profId) {
		return profService.deleteProfessor(profId);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Professor addProfessor(Professor prof) {
		long yourmilliseconds = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");    
		Date curTime = new Date(yourmilliseconds);
		return profService.addProfessor(prof.getName(), prof.getDepartment(), curTime);
	}
	
	@PUT
	@Path("/{professorId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Professor updateProfessor(@PathParam("professorId") String profId, 
			Professor prof) {
		return profService.updateProfessorInformation(profId, prof);
	}
}
