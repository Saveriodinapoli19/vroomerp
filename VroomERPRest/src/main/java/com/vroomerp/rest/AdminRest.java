package com.vroomerp.rest;

import com.vroomerp.ejb.UserEJB;
import com.vroomerp.ejb.UserEJBInterface;
import com.vroomerp.model.TUser;
import com.vroomerp.security.JwtUtil;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

public abstract class AdminRest {

    @Context
    protected HttpHeaders headers;

    @EJB
    protected UserEJBInterface userEJB;

    protected TUser getCurrentUser() {
        try {
            String token = headers.getHeaderString("Authorization");
            if (token != null && token.startsWith("Bearer ")) {
                String jwt = token.substring(7);
                String email = JwtUtil.validateToken(jwt);
                return userEJB.findByEmail(email);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected boolean isAdmin() {
        TUser user = getCurrentUser();
        return user != null && user.getExtRuoloUtenteId() == 1; // ID ruolo admin
    }
}
