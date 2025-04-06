package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the t_auto database table.
 * 
 */
@Entity
@Table(name="t_auto")
@NamedQuery(name="TAuto.findAll", query="SELECT t FROM TAuto t")
public class TAuto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="auto_id")
	private int autoId;

	@Column(name="consumo_medio")
	private double consumoMedio;

	@Column(name="ext_cilindrata_id")
	private String extCilindrataId;

	@Column(name="ext_motore_euro")
	private int extMotoreEuro;

	@Column(name="ext_tipo_alimentazione_id")
	private int extTipoAlimentazioneId;

	@Column(name="ext_tipo_auto")
	private int extTipoAuto;

	@Column(name="flag_deleted")
	private int flagDeleted;

	@Column(name="numero_porte")
	private int numeroPorte;

	@Column(name="numero_posti")
	private int numeroPosti;

	@Column(name="potenza_kw")
	private String potenzaKw;

	public TAuto() {
	}

	public int getAutoId() {
		return this.autoId;
	}

	public void setAutoId(int autoId) {
		this.autoId = autoId;
	}

	public double getConsumoMedio() {
		return this.consumoMedio;
	}

	public void setConsumoMedio(double consumoMedio) {
		this.consumoMedio = consumoMedio;
	}

	public String getExtCilindrataId() {
		return this.extCilindrataId;
	}

	public void setExtCilindrataId(String extCilindrataId) {
		this.extCilindrataId = extCilindrataId;
	}

	public int getExtMotoreEuro() {
		return this.extMotoreEuro;
	}

	public void setExtMotoreEuro(int extMotoreEuro) {
		this.extMotoreEuro = extMotoreEuro;
	}

	public int getExtTipoAlimentazioneId() {
		return this.extTipoAlimentazioneId;
	}

	public void setExtTipoAlimentazioneId(int extTipoAlimentazioneId) {
		this.extTipoAlimentazioneId = extTipoAlimentazioneId;
	}

	public int getExtTipoAuto() {
		return this.extTipoAuto;
	}

	public void setExtTipoAuto(int extTipoAuto) {
		this.extTipoAuto = extTipoAuto;
	}

	public int getFlagDeleted() {
		return this.flagDeleted;
	}

	public void setFlagDeleted(int flagDeleted) {
		this.flagDeleted = flagDeleted;
	}

	public int getNumeroPorte() {
		return this.numeroPorte;
	}

	public void setNumeroPorte(int numeroPorte) {
		this.numeroPorte = numeroPorte;
	}

	public int getNumeroPosti() {
		return this.numeroPosti;
	}

	public void setNumeroPosti(int numeroPosti) {
		this.numeroPosti = numeroPosti;
	}

	public String getPotenzaKw() {
		return this.potenzaKw;
	}

	public void setPotenzaKw(String potenzaKw) {
		this.potenzaKw = potenzaKw;
	}

}