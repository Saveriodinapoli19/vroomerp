package com.vroomerp.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the t_tipo_rimorchio database table.
 * 
 */
@Entity
@Table(name="t_tipo_rimorchio")
@NamedQuery(name="TTipoRimorchio.findAll", query="SELECT t FROM TTipoRimorchio t")
public class TTipoRimorchio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="tipo_rimorchio_id")
	private Integer tipoRimorchioId;

	private String descrizione;

	public TTipoRimorchio() {
	}

	public Integer getTipoRimorchioId() {
		return this.tipoRimorchioId;
	}

	public void setTipoRimorchioId(Integer tipoRimorchioId) {
		this.tipoRimorchioId = tipoRimorchioId;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}