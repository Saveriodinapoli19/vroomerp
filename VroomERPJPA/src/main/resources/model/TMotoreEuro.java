package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the t_motore_euro database table.
 * 
 */
@Entity
@Table(name="t_motore_euro")
@NamedQuery(name="TMotoreEuro.findAll", query="SELECT t FROM TMotoreEuro t")
public class TMotoreEuro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="motore_uro_id")
	private int motoreUroId;

	private String descrizione;

	public TMotoreEuro() {
	}

	public int getMotoreUroId() {
		return this.motoreUroId;
	}

	public void setMotoreUroId(int motoreUroId) {
		this.motoreUroId = motoreUroId;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}