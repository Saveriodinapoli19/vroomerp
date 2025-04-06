package model;

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
	@Column(name="moto_id")
	private int motoId;

	@Column(name="ext_cilindrata_id")
	private int extCilindrataId;

	@Column(name="ext_motore_euro_id")
	private int extMotoreEuroId;

	@Column(name="ext_tipo_moto_id")
	private int extTipoMotoId;

	@Column(name="ext_tipo_motore_id")
	private int extTipoMotoreId;

	@Column(name="flag_deletd")
	private int flagDeletd;

	private String peso;

	@Column(name="potenza_kw")
	private String potenzaKw;

	private String raffreddamento;

	@Column(name="tipo_moto_id")
	private int tipoMotoId;

	public TMoto() {
	}

	public int getMotoId() {
		return this.motoId;
	}

	public void setMotoId(int motoId) {
		this.motoId = motoId;
	}

	public int getExtCilindrataId() {
		return this.extCilindrataId;
	}

	public void setExtCilindrataId(int extCilindrataId) {
		this.extCilindrataId = extCilindrataId;
	}

	public int getExtMotoreEuroId() {
		return this.extMotoreEuroId;
	}

	public void setExtMotoreEuroId(int extMotoreEuroId) {
		this.extMotoreEuroId = extMotoreEuroId;
	}

	public int getExtTipoMotoId() {
		return this.extTipoMotoId;
	}

	public void setExtTipoMotoId(int extTipoMotoId) {
		this.extTipoMotoId = extTipoMotoId;
	}

	public int getExtTipoMotoreId() {
		return this.extTipoMotoreId;
	}

	public void setExtTipoMotoreId(int extTipoMotoreId) {
		this.extTipoMotoreId = extTipoMotoreId;
	}

	public int getFlagDeletd() {
		return this.flagDeletd;
	}

	public void setFlagDeletd(int flagDeletd) {
		this.flagDeletd = flagDeletd;
	}

	public String getPeso() {
		return this.peso;
	}

	public void setPeso(String peso) {
		this.peso = peso;
	}

	public String getPotenzaKw() {
		return this.potenzaKw;
	}

	public void setPotenzaKw(String potenzaKw) {
		this.potenzaKw = potenzaKw;
	}

	public String getRaffreddamento() {
		return this.raffreddamento;
	}

	public void setRaffreddamento(String raffreddamento) {
		this.raffreddamento = raffreddamento;
	}

	public int getTipoMotoId() {
		return this.tipoMotoId;
	}

	public void setTipoMotoId(int tipoMotoId) {
		this.tipoMotoId = tipoMotoId;
	}

}