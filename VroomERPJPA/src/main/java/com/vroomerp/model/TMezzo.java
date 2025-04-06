package com.vroomerp.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the t_mezzo database table.
 * 
 */
@Entity
@Table(name="t_mezzo")
@NamedQuery(name="TMezzo.findAll", query="SELECT t FROM TMezzo t")
public class TMezzo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="mezzo_id")
	private Integer mezzoId;

	@Column(name="anno_immatricolazione")
	private Integer annoImmatricolazione;

	@Column(name="auto_id")
	private Integer autoId;

	private String colore;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_acquisto")
	private Date dataAcquisto;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_inserimento")
	private Date dataInserimento;

	@Column(name="ext_tipo_alimentazione")
	private Integer extTipoAlimentazione;

	@Column(name="flag_acquistato")
	private Integer flagAcquistato;

	@Column(name="flag_deleted")
	private Integer flagDeleted;

	private String marca;

	private String modello;

	@Column(name="moto_id")
	private Integer motoId;

	@Column(name="prezzo")
	private Double prezzo;

	private String targa;

	@Column(name="tir_id")
	private Integer tirId;

	public TMezzo() {
	}

	public Integer getMezzoId() {
		return mezzoId;
	}

	public void setMezzoId(Integer mezzoId) {
		this.mezzoId = mezzoId;
	}

	public Integer getAnnoImmatricolazione() {
		return annoImmatricolazione;
	}

	public void setAnnoImmatricolazione(Integer annoImmatricolazione) {
		this.annoImmatricolazione = annoImmatricolazione;
	}

	public Integer getAutoId() {
		return autoId;
	}

	public void setAutoId(Integer autoId) {
		this.autoId = autoId;
	}

	public String getColore() {
		return colore;
	}

	public void setColore(String colore) {
		this.colore = colore;
	}

	public Date getDataAcquisto() {
		return dataAcquisto;
	}

	public void setDataAcquisto(Date dataAcquisto) {
		this.dataAcquisto = dataAcquisto;
	}

	public Date getDataInserimento() {
		return dataInserimento;
	}

	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	public Integer getExtTipoAlimentazione() {
		return extTipoAlimentazione;
	}

	public void setExtTipoAlimentazione(Integer extTipoAlimentazione) {
		this.extTipoAlimentazione = extTipoAlimentazione;
	}

	public Integer getFlagAcquistato() {
		return flagAcquistato;
	}

	public void setFlagAcquistato(Integer flagAcquistato) {
		this.flagAcquistato = flagAcquistato;
	}

	public Integer getFlagDeleted() {
		return flagDeleted;
	}

	public void setFlagDeleted(Integer flagDeleted) {
		this.flagDeleted = flagDeleted;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModello() {
		return modello;
	}

	public void setModello(String modello) {
		this.modello = modello;
	}

	public Integer getMotoId() {
		return motoId;
	}

	public void setMotoId(Integer motoId) {
		this.motoId = motoId;
	}

	public Double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(Double prezzo) {
		this.prezzo = prezzo;
	}

	public String getTarga() {
		return targa;
	}

	public void setTarga(String targa) {
		this.targa = targa;
	}

	public Integer getTirId() {
		return tirId;
	}

	public void setTirId(Integer tirId) {
		this.tirId = tirId;
	}

	

}