package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the t_tipo_auto database table.
 * 
 */
@Entity
@Table(name="t_tipo_auto")
@NamedQuery(name="TTipoAuto.findAll", query="SELECT t FROM TTipoAuto t")
public class TTipoAuto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="tipo_auto_id")
	private int tipoAutoId;

	private String descrizione;

	public TTipoAuto() {
	}

	public int getTipoAutoId() {
		return this.tipoAutoId;
	}

	public void setTipoAutoId(int tipoAutoId) {
		this.tipoAutoId = tipoAutoId;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}