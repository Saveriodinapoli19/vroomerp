package com.vroomerp.security;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.vroomerp.common.dto.basic.BasicResponse;


import java.io.IOException;

@JWTTokenNeeded
@Provider
@Priority(Priorities.AUTHENTICATION)
public class JwtFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authHeader = requestContext.getHeaderString("Authorization");
        BasicResponse response = new BasicResponse();
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        	response.setErrorCode(401);
        	response.setErrorMessage("Token mancante");
        	requestContext.abortWith(Response.ok(response).build());         
            return;
        }

        String token = authHeader.substring(7); // rimuove "Bearer "

        try {
            String email = JwtUtil.validateToken(token);
            // eventualmente puoi salvare l'email in un contesto
        } catch (Exception e) {
        	response.setErrorCode(401);
        	response.setErrorMessage("Token non valido o scaduto");
        	requestContext.abortWith(Response.ok(response).build());
        }
    }
}
