package com.vroomerp.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the t_tipo_motore database table.
 * 
 */
@Entity
@Table(name="t_tipo_motore")
@NamedQuery(name="TTipoMotore.findAll", query="SELECT t FROM TTipoMotore t")
public class TTipoMotore implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="tipo_motore_id")
	private int tipoMotoreId;

	private String descrizione;

	public TTipoMotore() {
	}

	public int getTipoMotoreId() {
		return this.tipoMotoreId;
	}

	public void setTipoMotoreId(int tipoMotoreId) {
		this.tipoMotoreId = tipoMotoreId;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}