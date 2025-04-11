package com.vroomerp.common.dto.moto;

import com.vroomerp.common.dto.mezzo.MezzoBean;

public class MotoBean {

	
	private Integer motoId;

	private String cilindrata;

	private Integer extMotoreEuroId;

	private Integer extTipoMotoId;

	private Integer extTipoMotoreId;

	private Integer flagDeleted;

	private String peso;

	private String potenzaKw;

	private String raffreddamento;
	
	private Integer extTipoAlimentazione;
	
	private String alimentazione;
	private String motoreEuro;
	private String tipoMoto;
	private String tipoMotore;
	
	private MezzoBean mezzoBean;
	
	
	

	public Integer getMotoId() {
		return motoId;
	}

	public void setMotoId(Integer motoId) {
		this.motoId = motoId;
	}


	public Integer getExtMotoreEuroId() {
		return extMotoreEuroId;
	}

	public void setExtMotoreEuroId(Integer extMotoreEuroId) {
		this.extMotoreEuroId = extMotoreEuroId;
	}

	public Integer getExtTipoMotoId() {
		return extTipoMotoId;
	}

	public void setExtTipoMotoId(Integer extTipoMotoId) {
		this.extTipoMotoId = extTipoMotoId;
	}

	public Integer getExtTipoMotoreId() {
		return extTipoMotoreId;
	}

	public void setExtTipoMotoreId(Integer extTipoMotoreId) {
		this.extTipoMotoreId = extTipoMotoreId;
	}

	public Integer getFlagDeleted() {
		return flagDeleted;
	}

	public void setFlagDeleted(Integer flagDeleted) {
		this.flagDeleted = flagDeleted;
	}

	public String getPeso() {
		return peso;
	}

	public void setPeso(String peso) {
		this.peso = peso;
	}

	public String getPotenzaKw() {
		return potenzaKw;
	}

	public void setPotenzaKw(String potenzaKw) {
		this.potenzaKw = potenzaKw;
	}

	public String getRaffreddamento() {
		return raffreddamento;
	}

	public void setRaffreddamento(String raffreddamento) {
		this.raffreddamento = raffreddamento;
	}

	public String getCilindrata() {
		return cilindrata;
	}

	public void setCilindrata(String cilindrata) {
		this.cilindrata = cilindrata;
	}

	public Integer getExtTipoAlimentazione() {
		return extTipoAlimentazione;
	}

	public void setExtTipoAlimentazione(Integer extTipoAlimentazione) {
		this.extTipoAlimentazione = extTipoAlimentazione;
	}

	public MezzoBean getMezzoBean() {
		return mezzoBean;
	}

	public void setMezzoBean(MezzoBean mezzoBean) {
		this.mezzoBean = mezzoBean;
	}

	public String getAlimentazione() {
		return alimentazione;
	}

	public void setAlimentazione(String alimentazione) {
		this.alimentazione = alimentazione;
	}

	public String getMotoreEuro() {
		return motoreEuro;
	}

	public void setMotoreEuro(String motoreEuro) {
		this.motoreEuro = motoreEuro;
	}

	public String getTipoMoto() {
		return tipoMoto;
	}

	public void setTipoMoto(String tipoMoto) {
		this.tipoMoto = tipoMoto;
	}

	public String getTipoMotore() {
		return tipoMotore;
	}

	public void setTipoMotore(String tipoMotore) {
		this.tipoMotore = tipoMotore;
	}
	
	

}
