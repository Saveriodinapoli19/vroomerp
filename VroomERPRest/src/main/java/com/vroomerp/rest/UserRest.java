package com.vroomerp.rest;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.beanutils.BeanUtils;

import com.vroomerp.common.dto.auth.LoginRequest;
import com.vroomerp.common.dto.user.UserBean;
import com.vroomerp.common.dto.user.UserRequest;
import com.vroomerp.common.dto.user.UserResponse;
import com.vroomerp.common.util.PasswordUtil;
import com.vroomerp.ejb.UserEJBInterface;
import com.vroomerp.model.TRuoloUtente;
import com.vroomerp.model.TUser;
import com.vroomerp.security.JWTTokenNeeded;
import com.vroomerp.security.JwtUtil;

@Stateless
@Path("/user")
public class UserRest {

	@EJB
	UserEJBInterface userEJB;
	

	
	@JWTTokenNeeded
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/insertUser")
	public Response createUser(UserRequest request) {
		UserResponse response = new UserResponse();

		try {
			TUser user = new TUser();

			user.setNome(request.getUserBean().getNome());
			user.setCognome(request.getUserBean().getCognome());
			user.setEmail(request.getUserBean().getEmail());
			user.setTelefono(request.getUserBean().getTelefono());
			String hashed = PasswordUtil.encodePassword(request.getUserBean().getPassword());
			user.setPassword(hashed);

			TRuoloUtente ruolo = userEJB.findByRuoloId(request.getUserBean().getExtRuoloUtenteId());
			if (ruolo == null) {
				response.setErrorCode(2);
				response.setErrorMessage("Attenzione: ruolo non presente");
				return Response.ok(response).build();
			}

			user.setExtRuoloUtenteId(ruolo.getRuoloUtenteId());

			TUser searchMail = userEJB.findByEmail(user.getEmail());
			if (searchMail != null) {
				response.setErrorCode(1);
				response.setErrorMessage("Attenzione: email già esistente");
				return Response.ok(response).build();
			}

			userEJB.insertUser(user);

			UserBean userBean = new UserBean();
			BeanUtils.copyProperties(userBean, user);

			response.setUserBean(userBean);
			response.setErrorCode(0);
			response.setErrorMessage("Utente creato con successo");

			return Response.ok(response).build();

		} catch (Exception e) {
			e.printStackTrace();
			response.setErrorCode(99);
			response.setErrorMessage("Errore interno del server");
			return Response.ok(response).build();
		}
	}

	@JWTTokenNeeded
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/updateUser")
	public Response updateUser(UserRequest request) {
	    UserResponse response = new UserResponse();

	    try {
	        TUser user = userEJB.findById(request.getUserBean().getUserId());

	        if (user == null) {
	            response.setErrorCode(-1);
	            response.setErrorMessage("Attenzione: L'Utente richiesto non esiste");
	            return Response.ok(response).build();
	        }

	       
	        if (request.getUserBean().getNome() != null) {
	            user.setNome(request.getUserBean().getNome());
	        }

	        if (request.getUserBean().getCognome() != null) {
	            user.setCognome(request.getUserBean().getCognome());
	        }

	        if (request.getUserBean().getTelefono() != null) {
	            user.setTelefono(request.getUserBean().getTelefono());
	        }
	        
	        if (request.getUserBean().getPassword() != null && !request.getUserBean().getPassword().isEmpty()) {
	            String hashedPassword = PasswordUtil.encodePassword(request.getUserBean().getPassword());
	            user.setPassword(hashedPassword);
	        }

	        userEJB.updateUser(user);

	        UserBean userBean = new UserBean();
	        BeanUtils.copyProperties(userBean, user);

	        response.setUserBean(userBean);
	        response.setErrorCode(0);
	        response.setErrorMessage("Utente aggiornato con successo");

	        return Response.ok(response).build();

	    } catch (Exception e) {
	        e.printStackTrace();
	        response.setErrorCode(99);
	        response.setErrorMessage("Errore interno del server");
	        return Response.ok(response).build();
	    }
	}

}
