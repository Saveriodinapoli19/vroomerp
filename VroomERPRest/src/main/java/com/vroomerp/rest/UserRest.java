package com.vroomerp.rest;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.vroomerp.common.dto.user.UserRequest;
import com.vroomerp.common.dto.user.UserResponse;
import com.vroomerp.ejb.UserEJBInterface;

@Stateless
@Path("/user")
public class UserRest {
	
	@EJB
	UserEJBInterface userEJB;
	
	
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/insertUser")
	public Response createUser(UserRequest request) {
		
		UserResponse response = new UserResponse();
		
		response.setErrorCode(99);
		response.setErrorMessage("Errore!!");
		return Response.ok().build();
		
	}
	
}
