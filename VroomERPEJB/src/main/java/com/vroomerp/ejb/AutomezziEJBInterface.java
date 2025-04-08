package com.vroomerp.ejb;

import java.util.List;

import javax.ejb.Remote;
import javax.persistence.TypedQuery;

import com.vroomerp.model.TAuto;
import com.vroomerp.model.TMezzo;
import com.vroomerp.model.TMotoreEuro;
import com.vroomerp.model.TTipoAlimentazione;
import com.vroomerp.model.TTipoAuto;
import com.vroomerp.model.TTipoMoto;
import com.vroomerp.model.TTipoRimorchio;


@Remote
public interface AutomezziEJBInterface {

	TAuto insertAuto(TAuto auto);

	TAuto updateAuto(TAuto auto);

	TAuto findByAutoId(Integer autoId);

	List<TAuto> findAllAuto();

	TAuto deleteAuto(TAuto auto);

	TTipoAlimentazione findByTipoAlimentazioneId(Integer idTipoAlimentazione);

	TTipoRimorchio findByTipoRimorchioId(Integer tipoRimorchioId);

	TTipoAuto findByTipoAutoId(Integer tipoAutoId);

	TTipoMoto findByTipoMotoId(Integer tipoMotoId);

	TMotoreEuro findByMotoreEuroId(Integer motoreEuroId);

	TMezzo insertMezzo(TMezzo mezzo);

	TMezzo findMezzoByAutoId(Integer autoId);

	TMezzo updateMezzo(TMezzo mezzo);
	
	
	


}
