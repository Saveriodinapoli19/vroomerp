package com.vroomerp.security;



import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.vroomerp.common.dto.basic.BasicResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Admin
@Provider
@Priority(Priorities.AUTHORIZATION)
public class AdminFilter implements ContainerRequestFilter {

    private static final long ROLE_ADMIN_ID = 1;

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
            Claims claims = Jwts.parser()
                    .setSigningKey("vroomerpSuperSecretKey123") // stessa chiave del JwtUtil
                    .parseClaimsJws(token)
                    .getBody();

            Integer ruolo = claims.get("ruolo", Integer.class);

            if (ruolo == null || ruolo != ROLE_ADMIN_ID) {
                response.setErrorCode(403);
                response.setErrorMessage("Operazione consentita solo agli amministratori");
                requestContext.abortWith(Response.ok(response).build());
            }

        } catch (Exception e) {
            response.setErrorCode(401);
            response.setErrorMessage("Token non valido o scaduto");
            requestContext.abortWith(Response.ok(response).build());
        }
    }
}
