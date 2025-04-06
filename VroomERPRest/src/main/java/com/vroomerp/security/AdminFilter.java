package com.vroomerp.security;

import com.vroomerp.common.dto.basic.BasicResponse;
import com.vroomerp.ejb.UserEJB;
import com.vroomerp.ejb.UserEJBInterface;
import com.vroomerp.model.TUser;


import javax.annotation.Priority;
import javax.ejb.EJB;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Admin
@Provider
@Priority(Priorities.AUTHORIZATION)
public class AdminFilter implements ContainerRequestFilter {

    @EJB
    private UserEJBInterface userEJB;

    private static final long ROLE_ADMIN_ID = 1; // ID del ruolo admin

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

        String token = authHeader.substring(7);
        try {
            String email = JwtUtil.validateToken(token);

            TUser user = userEJB.findByEmail(email);
            if (user == null || user.getExtRuoloUtenteId() != ROLE_ADMIN_ID) {
            	response.setErrorCode(403);
            	response.setErrorMessage("Operazione consesnita solo agli amministratori");
            	requestContext.abortWith(Response.ok(response).build());
            }

        } catch (Exception e) {
        	response.setErrorCode(401);
        	response.setErrorMessage("Token non valido o scaduto");
        	requestContext.abortWith(Response.ok(response).build());
        }
    }
}
