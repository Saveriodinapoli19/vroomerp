package com.vroomerp.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the t_tir database table.
 * 
 */
@Entity
@Table(name="t_tir")
@NamedQuery(name="TTir.findAll", query="SELECT t FROM TTir t")
public class TTir implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="tir_id")
	private Integer tirId;

	@Column(name="altezza_cassone")
	private String altezzaCassone;

	@Column(name="ext_motore_euro_id")
	private Integer extMotoreEuroId;

	@Column(name="ext_tipo_alimentazione_id")
	private Integer extTipoAlimentazioneId;

	@Column(name="ext_tipo_rimorchio_id")
	private Integer extTipoRimorchioId;

	private Double lunghezza;

	@Column(name="numero_assi")
	private Integer numeroAssi;

	@Column(name="portata_max")
	private Double portataMax;

	public TTir() {
	}

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

	

}