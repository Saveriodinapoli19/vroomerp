package com.vroomerp.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the t_tipo_alimentazione database table.
 * 
 */
@Entity
@Table(name="t_tipo_alimentazione")
@NamedQuery(name="TTipoAlimentazione.findAll", query="SELECT t FROM TTipoAlimentazione t")
public class TTipoAlimentazione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_tipo_alimentazione")
	private Integer idTipoAlimentazione;

	private String descrizione;

	public TTipoAlimentazione() {
	}

	public Integer getIdTipoAlimentazione() {
		return this.idTipoAlimentazione;
	}

	public void setIdTipoAlimentazione(Integer idTipoAlimentazione) {
		this.idTipoAlimentazione = idTipoAlimentazione;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}