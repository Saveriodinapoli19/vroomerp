package com.vroomerp.security;

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

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Token mancante").build());
            return;
        }

        String token = authHeader.substring(7);
        try {
            String email = JwtUtil.validateToken(token);

            TUser user = userEJB.findByEmail(email);
            if (user == null || user.getExtRuoloUtenteId() != ROLE_ADMIN_ID) {
                requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                        .entity("Accesso riservato agli amministratori").build());
            }

        } catch (Exception e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Token non valido").build());
        }
    }
}
