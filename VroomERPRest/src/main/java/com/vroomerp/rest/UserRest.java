package com.vroomerp.rest;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.beanutils.BeanUtils;

import com.vroomerp.common.dto.auth.LoginRequest;
import com.vroomerp.common.dto.user.RuoloUtenteBean;
import com.vroomerp.common.dto.user.RuoloUtenteResponse;
import com.vroomerp.common.dto.user.UserBean;
import com.vroomerp.common.dto.user.UserRequest;
import com.vroomerp.common.dto.user.UserResponse;
import com.vroomerp.common.util.Md5Generator;
import com.vroomerp.common.util.PasswordUtil;
import com.vroomerp.ejb.UserEJBInterface;
import com.vroomerp.model.TRuoloUtente;
import com.vroomerp.model.TUser;
import com.vroomerp.security.Admin;
import com.vroomerp.security.JWTTokenNeeded;
import com.vroomerp.security.JwtUtil;

@Stateless
@Path("/user")
public class UserRest {

	private static final Logger logger = Logger.getLogger(UserRest.class.getName());

	@EJB
	UserEJBInterface userEJB;

	@Context
	HttpServletRequest httpRequest;

	@Admin
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
//			String hashed = PasswordUtil.encodePassword(request.getUserBean().getPassword());
//			user.setPassword(hashed);
			String hashedPassword = Md5Generator.convertPass(request.getUserBean().getPassword());
			user.setPassword(hashedPassword);

			TRuoloUtente ruolo = userEJB.findByRuoloId(request.getUserBean().getExtRuoloUtenteId());
			if (ruolo == null) {
				response.setErrorCode(2);
				response.setErrorMessage("Attenzione: ruolo non presente");
				return Response.ok(response).build();
			}

			user.setExtRuoloUtenteId(ruolo.getRuoloUtenteId());

			TUser existingUser = userEJB.findByEmail(user.getEmail()); // include anche deleted

			if (existingUser != null) {
				if (existingUser.getFlagDeleted() != null && existingUser.getFlagDeleted() == 1) {
					// Riattivazione utente eliminato
					existingUser.setFlagDeleted(0);
					existingUser.setNome(user.getNome());
					existingUser.setCognome(user.getCognome());
					existingUser.setTelefono(user.getTelefono());
					existingUser.setPassword(user.getPassword());
					existingUser.setExtRuoloUtenteId(user.getExtRuoloUtenteId());

					userEJB.updateUser(existingUser);

					UserBean userBean = new UserBean();
					BeanUtils.copyProperties(userBean, existingUser);

					response.setUserBean(userBean);
					response.setErrorCode(0);
					response.setErrorMessage("Utente riattivato con successo");

					return Response.ok(response).build();
				} else {
					// Utente esiste e non è stato eliminato
					response.setErrorCode(1);
					response.setErrorMessage("Attenzione: email già esistente");
					return Response.ok(response).build();
				}
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

	@Admin
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
				String hashedPassword = Md5Generator.convertPass(request.getUserBean().getPassword());
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

	@Admin
	@JWTTokenNeeded
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/deleteUser/{userId}")
	public Response deleteUser(@PathParam("userId") Integer userId) {
		UserResponse response = new UserResponse();

		try {

			TUser user = userEJB.findById(userId);

			if (user == null) {
				response.setErrorCode(-1);
				response.setErrorMessage("Attenzione: L'Utente con id [" + userId + "]  non esiste");
				return Response.ok(response).build();
			}

			userEJB.deleteUser(user);

			UserBean userBean = new UserBean();
			BeanUtils.copyProperties(userBean, user);

			response.setUserBean(userBean);
			response.setErrorCode(0);
			response.setErrorMessage("Utente con id [" + userId + "] eliminato con successo");

			return Response.ok(response).build();

		} catch (Exception e) {
			e.printStackTrace();
			response.setErrorCode(99);
			response.setErrorMessage("Errore interno del server");
			return Response.ok(response).build();
		}
	}

	@JWTTokenNeeded
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/findById/{userId}")
	public Response findById(@PathParam("userId") Integer userId) {
		UserResponse response = new UserResponse();

		try {

			TUser user = userEJB.findById(userId);

			if (user == null) {
				response.setErrorCode(-1);
				response.setErrorMessage("Attenzione: L'Utente con id [" + userId + "]  non esiste");
				return Response.ok(response).build();
			}

			UserBean userBean = new UserBean();
			BeanUtils.copyProperties(userBean, user);

			TRuoloUtente ruolo = userEJB.findByRuoloId(user.getExtRuoloUtenteId());
			if (ruolo != null) {
				userBean.setRuolo(ruolo.getDescrizione());
			}

			response.setUserBean(userBean);
			response.setErrorCode(0);
			response.setErrorMessage("OK");

			return Response.ok(response).build();

		} catch (Exception e) {
			e.printStackTrace();
			response.setErrorCode(99);
			response.setErrorMessage("Errore interno del server");
			return Response.ok(response).build();
		}
	}

	@JWTTokenNeeded
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/findAllUsers")
	public Response findAllUsers() {
		UserResponse response = new UserResponse();

		try {

			List<UserBean> userList = userEJB.findAll().stream().map(u -> {

				UserBean bean = new UserBean();
				try {
					BeanUtils.copyProperties(bean, u);
				} catch (IllegalAccessException | InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				TRuoloUtente ruolo = userEJB.findByRuoloId(u.getExtRuoloUtenteId());
				if (ruolo != null) {
					bean.setRuolo(ruolo.getDescrizione());
				}
				return bean;

			}).collect(Collectors.toList());

			response.setUserBeanList(userList);
			response.setErrorCode(0);
			response.setErrorMessage("OK");

			return Response.ok(response).build();

		} catch (Exception e) {
			e.printStackTrace();
			response.setErrorCode(99);
			response.setErrorMessage("Errore interno del server");
			return Response.ok(response).build();
		}
	}
	
	@JWTTokenNeeded
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/findAllRuoloUtente")
	public Response findAllRuoloUtente() {
		RuoloUtenteResponse response = new RuoloUtenteResponse();

		try {

			List<RuoloUtenteBean> ruoloList = userEJB.findAllRuoli().stream().map(u -> {

				RuoloUtenteBean bean = new RuoloUtenteBean();
				try {
					BeanUtils.copyProperties(bean, u);
				} catch (IllegalAccessException | InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return bean;

			}).collect(Collectors.toList());

			response.setListaRuoloUtenteBean(ruoloList);
			response.setErrorCode(0);
			response.setErrorMessage("OK");

			return Response.ok(response).build();

		} catch (Exception e) {
			e.printStackTrace();
			response.setErrorCode(99);
			response.setErrorMessage("Errore interno del server");
			return Response.ok(response).build();
		}
	}

}
