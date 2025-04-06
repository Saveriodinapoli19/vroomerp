package com.vroomerp.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class AutomezziEJB implements AutomezziEJBInterface {
	
	@PersistenceContext(unitName = "vroomerpPU")
	private EntityManager em;

}
