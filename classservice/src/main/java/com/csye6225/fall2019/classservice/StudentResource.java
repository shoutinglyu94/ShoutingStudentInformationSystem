package com.csye6225.fall2019.classservice;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.csye6225.fall2019.model.Professor;
import com.csye6225.fall2019.model.Student;
import com.csye6225.fall2019.service.StudentService;

@Path("students")
public class StudentResource {
	
	StudentService studentService = new StudentService();
	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStudents() {
		List<Student> res = studentService.getAllStudents();
		GenericEntity<List<Student>> entity = new GenericEntity<List<Student>>(res) {};
		return Response.status(Status.OK).entity(entity).build();
	}
	
	@GET
	@Path("/{studentId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Student getStudent(@PathParam("studentId") String studentId) {
		System.out.println("Student Resource: Looking for: " + studentId);
		return studentService.getStudent(studentId);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStudentByProgram(
			@QueryParam("program") String program) {
		List<Student> list = null;
		if (program == null) {
			list = studentService.getAllStudents();
		} else {
			list = studentService.searchStudentByProgram(program);
		}
		GenericEntity<List<Student>> entity = new GenericEntity<List<Student>>(list) {};
		return Response.status(Status.OK).entity(entity).build();
	}
	
	@DELETE
	@Path("/{studentId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Student deleteStudent(@PathParam("studentId") String studentId) {
		return studentService.deleteStudent(studentId);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Student addStudent(Student student) {
		long time = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");    
		Date curDate = new Date(time);
		return studentService.addStudent(student.getName(), student.getProgram(), curDate);
	}
	
	

}
