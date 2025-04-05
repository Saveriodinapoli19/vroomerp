package com.vroomerp.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.vroomerp.model.TUser;


@Stateless
public class UserEJB implements UserEJBInterface{
	
	 @PersistenceContext(unitName = "vroomerpPU")
	    private EntityManager em;
	 
	 @Override
	 public Integer insertUser(TUser user) {
		 em.persist(user);
		 return user.getUserId();
		 
	 }
	 
	 @Override
	 public TUser updateUser(TUser user) {
		 
		 return em.merge(user);
	 }

}
