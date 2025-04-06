package com.vroomerp.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the t_tipo_moto database table.
 * 
 */
@Entity
@Table(name="t_tipo_moto")
@NamedQuery(name="TTipoMoto.findAll", query="SELECT t FROM TTipoMoto t")
public class TTipoMoto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="tipo_moto_id")
	private Integer tipoMotoId;

	private String descrizione;

	public TTipoMoto() {
	}

	public Integer getTipoMotoId() {
		return this.tipoMotoId;
	}

	public void setTipoMotoId(Integer tipoMotoId) {
		this.tipoMotoId = tipoMotoId;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}