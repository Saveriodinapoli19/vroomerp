package com.vroomerp.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the t_moto database table.
 * 
 */
@Entity
@Table(name="t_moto")
@NamedQuery(name="TMoto.findAll", query="SELECT t FROM TMoto t")
public class TMoto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="moto_id")
	private Integer motoId;

	@Column(name="ext_motore_euro_id")
	private Integer extMotoreEuroId;

	@Column(name="ext_tipo_moto_id")
	private Integer extTipoMotoId;

	@Column(name="ext_tipo_motore_id")
	private Integer extTipoMotoreId;

	@Column(name="flag_deleted")
	private Integer flagDeleted;

	private String peso;

	@Column(name="potenza_kw")
	private String potenzaKw;

	private String raffreddamento;

	
	@Column(name="cilindrata")
	private String cilindrata;

	@Column(name="ext_tipo_alimentazione")
	private Integer extTipoAlimentazione;
	
	public TMoto() {
	}

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

	

}