package com.vroomerp.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.vroomerp.model.TAuto;
import com.vroomerp.model.TMezzo;
import com.vroomerp.model.TMoto;
import com.vroomerp.model.TMotoreEuro;
import com.vroomerp.model.TTipoAlimentazione;
import com.vroomerp.model.TTipoAuto;
import com.vroomerp.model.TTipoMoto;
import com.vroomerp.model.TTipoRimorchio;
import com.vroomerp.model.TTir;
import com.vroomerp.model.TUser;

@Stateless
public class AutomezziEJB implements AutomezziEJBInterface {

	@PersistenceContext(unitName = "vroomerpPU")
	private EntityManager em;

	@Override
	public TAuto insertAuto(TAuto auto) {
		if (auto == null) {
			throw new IllegalArgumentException("Oggetto auto nullo");
		}
		auto.setFlagDeleted(0);
		em.persist(auto);
		return auto;
	}

	@Override
	public TAuto updateAuto(TAuto auto) {
		if (auto == null || auto.getAutoId() == null) {
			throw new IllegalArgumentException("Oggetto auto nullo");
		}

		em.merge(auto);
		return auto;
	}

	@Override
	public TAuto findByAutoId(Integer autoId) {
		String query = "SELECT t FROM TAuto t WHERE t.autoId = :autoId AND(t.flagDeleted = 0 OR t.flagDeleted IS NULL) ";

		TypedQuery<TAuto> str = em.createQuery(query, TAuto.class);
		str.setParameter("autoId", autoId);

		List<TAuto> results = str.getResultList();
		return results.isEmpty() ? null : results.get(0);
	}

	@Override
	public List<TAuto> findAllAuto() {
		String query = "SELECT t FROM TAuto t WHERE t.flagDeleted = 0 OR t.flagDeleted IS NULL";

		TypedQuery<TAuto> str = em.createQuery(query, TAuto.class);

		try {
			return str.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public TAuto deleteAuto(TAuto auto) {
		auto.setFlagDeleted(1);
		em.merge(auto);
		TMezzo mezzo = findMezzoByAutoId(auto.getAutoId());
		if (mezzo != null) {
			mezzo.setFlagDeleted(1);
			em.merge(mezzo);
		}

		return auto;
	}

	@Override
	public TTipoAlimentazione findByTipoAlimentazioneId(Integer idTipoAlimentazione) {
		String query = "SELECT t FROM TTipoAlimentazione t WHERE t.idTipoAlimentazione = :idTipoAlimentazione  ";

		TypedQuery<TTipoAlimentazione> str = em.createQuery(query, TTipoAlimentazione.class);
		str.setParameter("idTipoAlimentazione", idTipoAlimentazione);

		List<TTipoAlimentazione> results = str.getResultList();
		return results.isEmpty() ? null : results.get(0);
	}

	@Override
	public TTipoRimorchio findByTipoRimorchioId(Integer tipoRimorchioId) {
		String query = "SELECT t FROM TTipoRimorchio t WHERE t.tipoRimorchioId = :tipoRimorchioId  ";

		TypedQuery<TTipoRimorchio> str = em.createQuery(query, TTipoRimorchio.class);
		str.setParameter("tipoRimorchioId", tipoRimorchioId);

		List<TTipoRimorchio> results = str.getResultList();
		return results.isEmpty() ? null : results.get(0);
	}

	@Override
	public TTipoAuto findByTipoAutoId(Integer tipoAutoId) {
		String query = "SELECT t FROM TTipoAuto t WHERE t.tipoAutoId = :tipoAutoId  ";

		TypedQuery<TTipoAuto> str = em.createQuery(query, TTipoAuto.class);
		str.setParameter("tipoAutoId", tipoAutoId);

		List<TTipoAuto> results = str.getResultList();
		return results.isEmpty() ? null : results.get(0);
	}

	@Override
	public TTipoMoto findByTipoMotoId(Integer tipoMotoId) {
		String query = "SELECT t FROM TTipoMoto t WHERE t.tipoMotoId = :tipoMotoId  ";

		TypedQuery<TTipoMoto> str = em.createQuery(query, TTipoMoto.class);
		str.setParameter("tipoMotoId", tipoMotoId);

		List<TTipoMoto> results = str.getResultList();
		return results.isEmpty() ? null : results.get(0);
	}

	@Override
	public TMotoreEuro findByMotoreEuroId(Integer motoreEuroId) {
		String query = "SELECT t FROM TMotoreEuro t WHERE t.motoreEuroId = :motoreEuroId  ";

		TypedQuery<TMotoreEuro> str = em.createQuery(query, TMotoreEuro.class);
		str.setParameter("motoreEuroId", motoreEuroId);

		List<TMotoreEuro> results = str.getResultList();
		return results.isEmpty() ? null : results.get(0);
	}

	@Override
	public TMezzo insertMezzo(TMezzo mezzo) {
		if (mezzo == null) {
			throw new IllegalArgumentException("Oggetto auto nullo");
		}
		mezzo.setFlagDeleted(0);
		em.persist(mezzo);
		return mezzo;
	}

	@Override
	public TMezzo findMezzoByAutoId(Integer autoId) {
		String query = "SELECT t FROM TMezzo t WHERE t.autoId = :autoId AND(t.flagDeleted = 0 OR t.flagDeleted IS NULL) ";

		TypedQuery<TMezzo> str = em.createQuery(query, TMezzo.class);
		str.setParameter("autoId", autoId);

		List<TMezzo> results = str.getResultList();
		return results.isEmpty() ? null : results.get(0);
	}

	@Override
	public TMezzo updateMezzo(TMezzo mezzo) {
		if (mezzo == null) {
			throw new IllegalArgumentException("Oggetto auto nullo");
		}

		em.merge(mezzo);
		return mezzo;
	}
	@Override
	public List<TAuto> findAllAutoFiltered(String marca, String modello, String targa, Integer limit) {
	    StringBuilder queryStr = new StringBuilder("SELECT a FROM TAuto a JOIN TMezzo m ON a.autoId = m.autoId WHERE a.flagDeleted = 0");

	    if (marca != null && !marca.isEmpty()) {
	        queryStr.append(" AND LOWER(m.marca) LIKE LOWER(:marca)");
	    }
	    if (modello != null && !modello.isEmpty()) {
	        queryStr.append(" AND LOWER(m.modello) LIKE LOWER(:modello)");
	    }
	    if (targa != null && !targa.isEmpty()) {
	        queryStr.append(" AND LOWER(m.targa) LIKE LOWER(:targa)");
	    }

	    TypedQuery<TAuto> query = em.createQuery(queryStr.toString(), TAuto.class);

	    if (marca != null && !marca.isEmpty()) {
	        query.setParameter("marca", "%" + marca + "%");
	    }
	    if (modello != null && !modello.isEmpty()) {
	        query.setParameter("modello", "%" + modello + "%");
	    }
	    if (targa != null && !targa.isEmpty()) {
	        query.setParameter("targa", "%" + targa + "%");
	    }

	    if (limit != null && limit > 0) {
	        query.setMaxResults(limit);
	    }

	    return query.getResultList();
	}
	@Override
	public List<TMoto> findAllMotoFiltered(String marca, String modello, String targa, Integer limit) {
	    StringBuilder queryStr = new StringBuilder("SELECT m FROM TMoto m JOIN TMezzo z ON m.motoId = z.motoId WHERE m.flagDeleted = 0");

	    if (marca != null && !marca.isEmpty()) {
	        queryStr.append(" AND LOWER(z.marca) LIKE LOWER(:marca)");
	    }
	    if (modello != null && !modello.isEmpty()) {
	        queryStr.append(" AND LOWER(z.modello) LIKE LOWER(:modello)");
	    }
	    if (targa != null && !targa.isEmpty()) {
	        queryStr.append(" AND LOWER(z.targa) LIKE LOWER(:targa)");
	    }

	    TypedQuery<TMoto> query = em.createQuery(queryStr.toString(), TMoto.class);

	    if (marca != null && !marca.isEmpty()) {
	        query.setParameter("marca", "%" + marca + "%");
	    }
	    if (modello != null && !modello.isEmpty()) {
	        query.setParameter("modello", "%" + modello + "%");
	    }
	    if (targa != null && !targa.isEmpty()) {
	        query.setParameter("targa", "%" + targa + "%");
	    }

	    if (limit != null && limit > 0) {
	        query.setMaxResults(limit);
	    }

	    return query.getResultList();
	}
	@Override
	public List<TTir> findAllTirFiltered(String marca, String modello, String targa, Integer limit) {
	    StringBuilder queryStr = new StringBuilder("SELECT t FROM TTir t JOIN TMezzo z ON t.tirId = z.tirId WHERE t.flagDeleted = 0");

	    if (marca != null && !marca.isEmpty()) {
	        queryStr.append(" AND LOWER(z.marca) LIKE LOWER(:marca)");
	    }
	    if (modello != null && !modello.isEmpty()) {
	        queryStr.append(" AND LOWER(z.modello) LIKE LOWER(:modello)");
	    }
	    if (targa != null && !targa.isEmpty()) {
	        queryStr.append(" AND LOWER(z.targa) LIKE LOWER(:targa)");
	    }

	    TypedQuery<TTir> query = em.createQuery(queryStr.toString(), TTir.class);

	    if (marca != null && !marca.isEmpty()) {
	        query.setParameter("marca", "%" + marca + "%");
	    }
	    if (modello != null && !modello.isEmpty()) {
	        query.setParameter("modello", "%" + modello + "%");
	    }
	    if (targa != null && !targa.isEmpty()) {
	        query.setParameter("targa", "%" + targa + "%");
	    }

	    if (limit != null && limit > 0) {
	        query.setMaxResults(limit);
	    }

	    return query.getResultList();
	}


}
