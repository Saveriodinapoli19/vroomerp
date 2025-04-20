package com.vroomerp.ejb;

import java.util.List;

import javax.ejb.Remote;

import com.vroomerp.model.TAcquisti;
import com.vroomerp.model.TRuoloUtente;
import com.vroomerp.model.TUser;

@Remote
public interface AcquistiEJBInterface {

	TAcquisti insertAcquisto(TAcquisti acquisto);

	TAcquisti updateAcquisto(TAcquisti acquisto);

	List<TAcquisti> findAll();

	TAcquisti findById(Integer acquisoId);

	TAcquisti findAcquistoByUserId(Integer extUserId);

	TAcquisti findAcquistoByMezzoId(Integer extMezzoId);

	TAcquisti findAcquistoByAutoId(Integer extAutoId);

	TAcquisti findAcquistoByMotoId(Integer extMotoId);

	TAcquisti findAcquistoByTirId(Integer extTirId);

	List<TAcquisti> findAcquistiByUserId(Integer userId);

}
