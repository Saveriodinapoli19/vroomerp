package com.vroomerp.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.vroomerp.model.TAcquisti;
import com.vroomerp.model.TRuoloUtente;
import com.vroomerp.model.TUser;

@Stateless
public class AcquistiEjb implements AcquistiEJBInterface {

	@PersistenceContext(unitName = "vroomerpPU")
	private EntityManager em;

	@Override
	public TAcquisti insertAcquisto(TAcquisti acquisto) {
		if (acquisto == null) {
			throw new IllegalArgumentException("Oggetto acquisto nullo");
		}
		em.persist(acquisto);
		return acquisto;

	}

	@Override
	public TAcquisti updateAcquisto(TAcquisti acquisto) {
		if (acquisto == null) {
			throw new IllegalArgumentException("Oggetto acquisto nullo");
		}
		em.merge(acquisto);
		return acquisto;

	}

	
	@Override
	public TAcquisti findById(Integer acquisoId) {
		String query = "SELECT t FROM TAcquisti t WHERE t.acquisoId = :acquisoId ";

		TypedQuery<TAcquisti> str = em.createQuery(query, TAcquisti.class);
		str.setParameter("acquisoId", acquisoId);

		 List<TAcquisti> results = str.getResultList();
		    return results.isEmpty() ? null : results.get(0);
	}

	@Override
	public TAcquisti findAcquistoByMezzoId(Integer extMezzoId) {
		String query = "SELECT t FROM TAcquisti t WHERE t.extMezzoId = :extMezzoId";

		TypedQuery<TAcquisti> str = em.createQuery(query, TAcquisti.class);
		str.setParameter("extMezzoId", extMezzoId);

		try {
			return str.getSingleResult();
		} catch (NoResultException e) {
			return null; 
		}
	}

	@Override
	public TAcquisti findAcquistoByAutoId(Integer extAutoId) {
		String query = "SELECT t FROM TAcquisti t WHERE t.extAutoId = :extAutoId";

		TypedQuery<TAcquisti> str = em.createQuery(query, TAcquisti.class);
		str.setParameter("extAutoId", extAutoId);

		try {
			return str.getSingleResult();
		} catch (NoResultException e) {
			return null; 
		}
	}
	
	@Override
	public TAcquisti findAcquistoByTirId(Integer extTirId) {
		String query = "SELECT t FROM TAcquisti t WHERE t.extTirId = :extTirId";

		TypedQuery<TAcquisti> str = em.createQuery(query, TAcquisti.class);
		str.setParameter("extTirId", extTirId);

		try {
			return str.getSingleResult();
		} catch (NoResultException e) {
			return null; 
		}
	}
	
	@Override
	public TAcquisti findAcquistoByMotoId(Integer extMotoId) {
		String query = "SELECT t FROM TAcquisti t WHERE t.extMotoId = :extMotoId";

		TypedQuery<TAcquisti> str = em.createQuery(query, TAcquisti.class);
		str.setParameter("extMotoId", extMotoId);

		try {
			return str.getSingleResult();
		} catch (NoResultException e) {
			return null; 
		}
	}
	
	@Override
	public TAcquisti findAcquistoByUserId(Integer extUserId) {
		String query = "SELECT t FROM TAcquisti t WHERE t.extUserId = :extUserId";

		TypedQuery<TAcquisti> str = em.createQuery(query, TAcquisti.class);
		str.setParameter("extUserId", extUserId);

		try {
			return str.getSingleResult();
		} catch (NoResultException e) {
			return null; 
		}
	}
	
	@Override 
	public List<TAcquisti> findAll() {
		String query = "SELECT t FROM TAcquisti t ";

		TypedQuery<TAcquisti> str = em.createQuery(query, TAcquisti.class);
		

		try {
			return str.getResultList();
		} catch (NoResultException e) {
			return null; 
		}
	}
	
	@Override
	public List<TAcquisti> findAcquistiByUserId(Integer userId) {
	    String jpql = "SELECT a FROM TAcquisti a WHERE a.extUserId = :userId ";
	    TypedQuery<TAcquisti> query = em.createQuery(jpql, TAcquisti.class);
	    query.setParameter("userId", userId);
	    return query.getResultList();
	}

	
}