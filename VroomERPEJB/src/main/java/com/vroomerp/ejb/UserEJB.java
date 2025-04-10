package com.vroomerp.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.vroomerp.model.TRuoloUtente;
import com.vroomerp.model.TUser;

@Stateless
public class UserEJB implements UserEJBInterface {

	@PersistenceContext(unitName = "vroomerpPU")
	private EntityManager em;

	@Override
	public Integer insertUser(TUser user) {
		user.setFlagDeleted(0);
		em.persist(user);
		return user.getUserId();

	}

	@Override
	public TUser updateUser(TUser user) {

		return em.merge(user);
	}
	
	@Override
	public TUser deleteUser(TUser user) {
		user.setFlagDeleted(1);
		return em.merge(user);
	}
	
	@Override
	public TUser findById(Integer userId) {
		String query = "SELECT t FROM TUser t WHERE t.userId = :userId AND(t.flagDeleted = 0 OR t.flagDeleted IS NULL) ";

		TypedQuery<TUser> str = em.createQuery(query, TUser.class);
		str.setParameter("userId", userId);

		 List<TUser> results = str.getResultList();
		    return results.isEmpty() ? null : results.get(0);
	}

	
//	@Override
//	public TUser findByEmail(String email) {
//		String query = "SELECT t FROM TUser t WHERE t.email = :email";
//
//		TypedQuery<TUser> str = em.createQuery(query, TUser.class);
//		str.setParameter("email", email);
//
//		 List<TUser> results = str.getResultList();
//		    return results.isEmpty() ? null : results.get(0);
//	}
	
	public TUser findByEmail(String email) {
	    String query = "SELECT t FROM TUser t WHERE t.email = :email";
	    return em.createQuery(query, TUser.class)
	             .setParameter("email", email)
	             .getResultStream()
	             .findFirst()
	             .orElse(null);
	}

	
	@Override
	public TRuoloUtente findByRuoloId(Integer ruoloUtenteId) {
		String query = "SELECT t FROM TRuoloUtente t WHERE t.ruoloUtenteId = :ruoloUtenteId";

		TypedQuery<TRuoloUtente> str = em.createQuery(query, TRuoloUtente.class);
		str.setParameter("ruoloUtenteId", ruoloUtenteId);

		try {
			return str.getSingleResult();
		} catch (NoResultException e) {
			return null; 
		}
	}
	
	@Override 
	public List<TUser> findAll() {
		String query = "SELECT t FROM TUser t WHERE t.flagDeleted = 0 OR t.flagDeleted IS NULL";

		TypedQuery<TUser> str = em.createQuery(query, TUser.class);
		

		try {
			return str.getResultList();
		} catch (NoResultException e) {
			return null; 
		}
	}
	
	@Override 
	public List<TRuoloUtente> findAllRuoli() {
		String query = "SELECT t FROM TRuoloUtente t ";

		TypedQuery<TRuoloUtente> str = em.createQuery(query, TRuoloUtente.class);
		

		try {
			return str.getResultList();
		} catch (NoResultException e) {
			return null; 
		}
	}
}