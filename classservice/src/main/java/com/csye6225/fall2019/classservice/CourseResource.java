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

import com.csye6225.fall2019.model.Course;
import com.csye6225.fall2019.model.Professor;
import com.csye6225.fall2019.model.Student;
import com.csye6225.fall2019.service.CourseService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@Path("courses")
public class CourseResource {
	CourseService courseService = new CourseService();
	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCourses() {
		List<Course> res = courseService.getAllCourses();
		GenericEntity<List<Course>> entity = new GenericEntity<List<Course>>(res) {};
		return Response.status(Status.OK).entity(entity).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCoursesByProgram(@QueryParam("program") String program) {
		List<Course> res = null;
		if(program == null || program.isEmpty()) {
			res = courseService.getAllCourses();
		} else {
			res = courseService.getCoursesByProgram(program);
		}
		GenericEntity<List<Course>> entity = new GenericEntity<List<Course>>(res) {};
		return Response.status(Status.OK).entity(entity).build();
	}
	
	@GET
	@Path("/{courseId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Course getCourse(@PathParam("courseId") String courseId) {
		if(courseId == null) {
			System.out.println("No course id in url.");
			return null;
		}
		System.out.println("Course Resource: Looking for: " + courseId);
		return courseService.getCourse(courseId);
	}
	
	@DELETE
	@Path("/{courseId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Course deleteCourse(@PathParam("courseId") String courseId) {
		if(courseId == null) {
			System.out.println("No course id in url.");
			return null;
		}
		return courseService.deleteCourse(courseId);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Course addCourse(String body) {
		long time = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");    
		Date curDate = new Date(time);
		JsonObject jobj = new Gson().fromJson(body, JsonObject.class);
		JsonElement cnJE = jobj.get("courseName");
		JsonElement pfJE = jobj.get("professorId");
		JsonElement taJE = jobj.get("taId");
		JsonElement pgJE = jobj.get("program");
		if(cnJE == null || pfJE == null || taJE == null || pgJE == null) {
			System.out.println("Json inputs are not enough.");
			return null;
		}
		return courseService.addCourse(cnJE.toString(), pgJE.toString(), 
				pfJE.toString(), taJE.toString());
	}
	
	@PUT
	@Path("/enroll/{courseId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Course enrollStudent(@PathParam("courseId") String courseId, Student student) {
		if(courseId == null) {
			System.out.println("No courseId in url.");
			return null;
		}
		if(student == null) {
			System.out.println("No course in json.");
			return null;
		}
		courseService.enrollStudent(courseId, student.getId());
		return courseService.getCourse(courseId);
	}
	
//	@PUT
//	@Path("/{courseId}")
//	@Produces(MediaType.APPLICATION_JSON)
//	@Consumes(MediaType.APPLICATION_JSON)
//	public Course updateCourseInformation(@PathParam("courseId") String courseId, Course course) {
//		if(courseId == null) {
//			System.out.println("No courseId in url.");
//			return null;
//		}
//		if(course == null) {
//			System.out.println("No course in json.");
//			return null;
//		}
//		return courseService.updateCourseInformation(courseId, course);
//	}
	
}
