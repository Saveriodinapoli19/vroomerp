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

import org.apache.commons.codec.digest.DigestUtils;

import com.vroomerp.common.dto.auth.LoginRequest;
import com.vroomerp.common.dto.basic.BasicResponse;
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
	    BasicResponse response = new BasicResponse();
	    TUser user = userEJB.findByEmail(request.getEmail());

	    if (user == null) {
	        response.setErrorCode(99);
	        response.setErrorMessage("Utente non presente in piattaforma");
	        return Response.ok(response).build();
	    }

	    if (user.getFlagDeleted() != null && user.getFlagDeleted() == 1) {
	        response.setErrorCode(98);
	        response.setErrorMessage("Utente eliminato o non presente in piattaforma");
	        return Response.ok(response).build();
	    }

	    String inputPassword = request.getPassword();
	    String storedPassword = user.getPassword();

	    boolean validPassword = false;

	    // Se la password in DB è BCryptata (inizia con $2a$)
	    if (storedPassword != null && storedPassword.startsWith("$2a$")) {
	        validPassword = PasswordUtil.checkPassword(inputPassword, storedPassword);
	    } else {
	        // fallback MD5 per utenti vecchi
	        String hashedInput = org.apache.commons.codec.digest.DigestUtils.md5Hex(inputPassword);
	        validPassword = hashedInput.equalsIgnoreCase(storedPassword);

	        // se è giusta, aggiorna a BCRYPT
	        if (validPassword) {
	            String bcrypt = PasswordUtil.createPassword(inputPassword);
	            user.setPassword(bcrypt);
	            userEJB.updateUser(user); // salva la nuova password
	        }
	    }

	    if (!validPassword) {
	        response.setErrorCode(99);
	        response.setErrorMessage("Credenziali errate");
	        return Response.ok(response).build();
	    }

	    
	  
	    String token = JwtUtil.generateToken(user);


	    Map<String, Object> result = new HashMap<>();
	    result.put("token", token);

	    Map<String, Object> utente = new HashMap<>();
	    utente.put("id", user.getUserId());
	    utente.put("email", user.getEmail());
	    utente.put("extRuoloUtenteId", user.getExtRuoloUtenteId()); // o il nome corretto del campo

	    result.put("utente", utente);

	    return Response.ok(result).build();
	}

	
}
