package com.vroomerp.common.dto.tir;

import com.vroomerp.common.dto.mezzo.MezzoBean;

public class TirBean {


	private Integer tirId;

	private String altezzaCassone;

	private Integer extMotoreEuroId;
	private String motoreEuro;

	private Integer extTipoAlimentazioneId;
	private String alimentazione;

	private Integer extTipoRimorchioId;
	private String rimorchio;
	
	
	private Double lunghezza;

	private Integer numeroAssi;
	
	private Double portataMax;

	private String cilindrata;
	
	private Integer flagDeleted;
	
	private MezzoBean mezzoBean;
	
	
	public Integer getTirId() {
		return tirId;
	}

	public void setTirId(Integer tirId) {
		this.tirId = tirId;
	}

	public String getAltezzaCassone() {
		return altezzaCassone;
	}

	public void setAltezzaCassone(String altezzaCassone) {
		this.altezzaCassone = altezzaCassone;
	}

	public Integer getExtMotoreEuroId() {
		return extMotoreEuroId;
	}

	public void setExtMotoreEuroId(Integer extMotoreEuroId) {
		this.extMotoreEuroId = extMotoreEuroId;
	}

	public Integer getExtTipoAlimentazioneId() {
		return extTipoAlimentazioneId;
	}

	public void setExtTipoAlimentazioneId(Integer extTipoAlimentazioneId) {
		this.extTipoAlimentazioneId = extTipoAlimentazioneId;
	}

	public Integer getExtTipoRimorchioId() {
		return extTipoRimorchioId;
	}

	public void setExtTipoRimorchioId(Integer extTipoRimorchioId) {
		this.extTipoRimorchioId = extTipoRimorchioId;
	}

	public Double getLunghezza() {
		return lunghezza;
	}

	public void setLunghezza(Double lunghezza) {
		this.lunghezza = lunghezza;
	}

	public Integer getNumeroAssi() {
		return numeroAssi;
	}

	public void setNumeroAssi(Integer numeroAssi) {
		this.numeroAssi = numeroAssi;
	}

	public Double getPortataMax() {
		return portataMax;
	}

	public void setPortataMax(Double portataMax) {
		this.portataMax = portataMax;
	}

	public String getCilindrata() {
		return cilindrata;
	}

	public void setCilindrata(String cilindrata) {
		this.cilindrata = cilindrata;
	}

	public Integer getFlagDeleted() {
		return flagDeleted;
	}

	public void setFlagDeleted(Integer flagDeleted) {
		this.flagDeleted = flagDeleted;
	}

	public MezzoBean getMezzoBean() {
		return mezzoBean;
	}

	public void setMezzoBean(MezzoBean mezzoBean) {
		this.mezzoBean = mezzoBean;
	}

	public String getMotoreEuro() {
		return motoreEuro;
	}

	public void setMotoreEuro(String motoreEuro) {
		this.motoreEuro = motoreEuro;
	}

	public String getAlimentazione() {
		return alimentazione;
	}

	public void setAlimentazione(String alimentazione) {
		this.alimentazione = alimentazione;
	}

	public String getRimorchio() {
		return rimorchio;
	}

	public void setRimorchio(String rimorchio) {
		this.rimorchio = rimorchio;
	}
	
	

}
