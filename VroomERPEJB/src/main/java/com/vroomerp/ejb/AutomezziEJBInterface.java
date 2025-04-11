package com.vroomerp.ejb;

import java.util.List;

import javax.ejb.Remote;
import javax.persistence.TypedQuery;

import com.vroomerp.model.TAuto;
import com.vroomerp.model.TMezzo;
import com.vroomerp.model.TMoto;
import com.vroomerp.model.TMotoreEuro;
import com.vroomerp.model.TTipoAlimentazione;
import com.vroomerp.model.TTipoAuto;
import com.vroomerp.model.TTipoMoto;
import com.vroomerp.model.TTipoMotore;
import com.vroomerp.model.TTipoRimorchio;
import com.vroomerp.model.TTir;


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

	List<TAuto> findAllAutoFiltered(String marca, String modello, String targa, Integer limit);

	List<TMoto> findAllMotoFiltered(String marca, String modello, String targa, Integer limit);

	List<TTir> findAllTirFiltered(String marca, String modello, String targa, Integer limit);

	List<TTipoAlimentazione> findAllTipoAlimentazione();

	List<TTipoAuto> findAllTipoAuto();

	List<TMotoreEuro> findAllMotoreEuro();

	TMoto insertMoto(TMoto moto);

	TTipoMotore findByTipoMotoreId(Integer tipoMotoreId);

	TMoto findByMotoId(Integer motoId);

	TMezzo findMezzoByMotoId(Integer motoId);

	TMoto updateMoto(TMoto moto);

	List<TMoto> findAllMoto();

	TMoto deleteMoto(TMoto moto);
	
	
	


}
