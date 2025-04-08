package com.vroomerp.common.dto.auto;

import com.vroomerp.common.dto.mezzo.MezzoBean;

public class AutoBean {

	private Integer autoId;

	private Double consumoMedio;

	
	private String cilindrata;

	private Integer extMotoreEuro;
	private String motoreEuro;
		
	private Integer extTipoAlimentazioneId;
	private String alimentazione;
	
	private Integer extTipoAuto;
	private String descTipoAuto;
	
	private Integer flagDeleted;

	private Integer numeroPorte;

	private Integer numeroPosti;

	private String potenzaKw;
	
	private MezzoBean mezzoBean;

	public Integer getAutoId() {
		return autoId;
	}

	public void setAutoId(Integer autoId) {
		this.autoId = autoId;
	}

	public Double getConsumoMedio() {
		return consumoMedio;
	}

	public void setConsumoMedio(Double consumoMedio) {
		this.consumoMedio = consumoMedio;
	}

	public String getCilindrata() {
		return cilindrata;
	}

	public void setCilindrata(String cilindrata) {
		this.cilindrata = cilindrata;
	}

	public Integer getExtMotoreEuro() {
		return extMotoreEuro;
	}

	public void setExtMotoreEuro(Integer extMotoreEuro) {
		this.extMotoreEuro = extMotoreEuro;
	}

	public String getMotoreEuro() {
		return motoreEuro;
	}

	public void setMotoreEuro(String motoreEuro) {
		this.motoreEuro = motoreEuro;
	}

	public Integer getExtTipoAlimentazioneId() {
		return extTipoAlimentazioneId;
	}

	public void setExtTipoAlimentazioneId(Integer extTipoAlimentazioneId) {
		this.extTipoAlimentazioneId = extTipoAlimentazioneId;
	}

	public String getAlimentazione() {
		return alimentazione;
	}

	public void setAlimentazione(String alimentazione) {
		this.alimentazione = alimentazione;
	}

	public Integer getExtTipoAuto() {
		return extTipoAuto;
	}

	public void setExtTipoAuto(Integer extTipoAuto) {
		this.extTipoAuto = extTipoAuto;
	}

	public String getDescTipoAuto() {
		return descTipoAuto;
	}

	public void setDescTipoAuto(String descTipoAuto) {
		this.descTipoAuto = descTipoAuto;
	}

	public Integer getFlagDeleted() {
		return flagDeleted;
	}

	public void setFlagDeleted(Integer flagDeleted) {
		this.flagDeleted = flagDeleted;
	}

	public Integer getNumeroPorte() {
		return numeroPorte;
	}

	public void setNumeroPorte(Integer numeroPorte) {
		this.numeroPorte = numeroPorte;
	}

	public Integer getNumeroPosti() {
		return numeroPosti;
	}

	public void setNumeroPosti(Integer numeroPosti) {
		this.numeroPosti = numeroPosti;
	}

	public String getPotenzaKw() {
		return potenzaKw;
	}

	public void setPotenzaKw(String potenzaKw) {
		this.potenzaKw = potenzaKw;
	}

	public MezzoBean getMezzoBean() {
		return mezzoBean;
	}

	public void setMezzoBean(MezzoBean mezzoBean) {
		this.mezzoBean = mezzoBean;
	}
	
	
	
	
}
