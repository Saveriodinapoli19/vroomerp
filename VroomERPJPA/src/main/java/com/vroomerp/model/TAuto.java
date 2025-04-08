package com.vroomerp.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the t_auto database table.
 * 
 */
@Entity
@Table(name="t_auto")
@NamedQuery(name="TAuto.findAll", query="SELECT t FROM TAuto t")
public class TAuto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="auto_id")
	private Integer autoId;

	@Column(name="consumo_medio")
	private Double consumoMedio;

	@Column(name="cilindrata")
	private String cilindrata;

	@Column(name="ext_motore_euro")
	private Integer extMotoreEuro;

	@Column(name="ext_tipo_alimentazione_id")
	private Integer extTipoAlimentazioneId;

	@Column(name="ext_tipo_auto")
	private Integer extTipoAuto;

	@Column(name="flag_deleted")
	private Integer flagDeleted;

	@Column(name="numero_porte")
	private Integer numeroPorte;

	@Column(name="numero_posti")
	private Integer numeroPosti;

	@Column(name="potenza_kw")
	private String potenzaKw;

	public TAuto() {
	}

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

	public Integer getExtTipoAlimentazioneId() {
		return extTipoAlimentazioneId;
	}

	public void setExtTipoAlimentazioneId(Integer extTipoAlimentazioneId) {
		this.extTipoAlimentazioneId = extTipoAlimentazioneId;
	}

	public Integer getExtTipoAuto() {
		return extTipoAuto;
	}

	public void setExtTipoAuto(Integer extTipoAuto) {
		this.extTipoAuto = extTipoAuto;
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



}