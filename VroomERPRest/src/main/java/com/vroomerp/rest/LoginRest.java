package com.vroomerp.rest;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.vroomerp.common.dto.auth.LoginRequest;
import com.vroomerp.common.util.PasswordUtil;
import com.vroomerp.ejb.UserEJBInterface;
import com.vroomerp.model.TUser;
import com.vroomerp.security.JWTTokenNeeded;
import com.vroomerp.security.JwtUtil;

@Stateless
@Path("/login")
public class LoginRest {
	
	@EJB
	UserEJBInterface userEJB;
	
	
	
	
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(LoginRequest request) {
	    TUser user = userEJB.findByEmail(request.getEmail());


	    if (user == null || !PasswordUtil.checkPassword(request.getPassword(), user.getPassword())) {
	        return Response.status(Response.Status.UNAUTHORIZED)
	                .entity("Credenziali errate").build();
	    }

	    String token = JwtUtil.generateToken(user.getEmail());

	    Map<String, String> result = new HashMap<>();
	    result.put("token", token);

	    return Response.ok(result).build();
	}
	
}
