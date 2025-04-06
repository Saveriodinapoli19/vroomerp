package com.vroomerp.rest;

import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Path;

import com.vroomerp.ejb.AutomezziEJBInterface;
import com.vroomerp.ejb.UserEJBInterface;

@Stateless
@Path("/automezzi")
public class AutomezziRest {
	
	private static final Logger logger = Logger.getLogger(UserRest.class.getName());

	@EJB
	UserEJBInterface userEJB;
	//TVeicoloGruppo/TVeicoloGruppoMezzo creare tabella e informarsi/pensare in che modo utilizzarla
	
	@EJB
	AutomezziEJBInterface automezziEJB;
	
	
}
