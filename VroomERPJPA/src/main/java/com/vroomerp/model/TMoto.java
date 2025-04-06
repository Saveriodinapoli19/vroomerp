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

	@Column(name="ext_cilindrata_id")
	private Integer extCilindrataId;

	@Column(name="ext_motore_euro_id")
	private Integer extMotoreEuroId;

	@Column(name="ext_tipo_moto_id")
	private Integer extTipoMotoId;

	@Column(name="ext_tipo_motore_id")
	private Integer extTipoMotoreId;

	@Column(name="flag_deletd")
	private Integer flagDeletd;

	private String peso;

	@Column(name="potenza_kw")
	private String potenzaKw;

	private String raffreddamento;

	@Column(name="tipo_moto_id")
	private Integer tipoMotoId;

	public TMoto() {
	}

	public Integer getMotoId() {
		return motoId;
	}

	public void setMotoId(Integer motoId) {
		this.motoId = motoId;
	}

	public Integer getExtCilindrataId() {
		return extCilindrataId;
	}

	public void setExtCilindrataId(Integer extCilindrataId) {
		this.extCilindrataId = extCilindrataId;
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

	public Integer getFlagDeletd() {
		return flagDeletd;
	}

	public void setFlagDeletd(Integer flagDeletd) {
		this.flagDeletd = flagDeletd;
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

	public Integer getTipoMotoId() {
		return tipoMotoId;
	}

	public void setTipoMotoId(Integer tipoMotoId) {
		this.tipoMotoId = tipoMotoId;
	}

	

}