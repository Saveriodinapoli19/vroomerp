package model;

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
	@Column(name="tir_id")
	private int tirId;

	@Column(name="altezza_cassone")
	private String altezzaCassone;

	@Column(name="ext_motore_euro_id")
	private int extMotoreEuroId;

	@Column(name="ext_tipo_alimentazione_id")
	private int extTipoAlimentazioneId;

	@Column(name="ext_tipo_rimorchio_id")
	private int extTipoRimorchioId;

	private double lunghezza;

	@Column(name="numero_assi")
	private int numeroAssi;

	@Column(name="portata_max")
	private double portataMax;

	public TTir() {
	}

	public int getTirId() {
		return this.tirId;
	}

	public void setTirId(int tirId) {
		this.tirId = tirId;
	}

	public String getAltezzaCassone() {
		return this.altezzaCassone;
	}

	public void setAltezzaCassone(String altezzaCassone) {
		this.altezzaCassone = altezzaCassone;
	}

	public int getExtMotoreEuroId() {
		return this.extMotoreEuroId;
	}

	public void setExtMotoreEuroId(int extMotoreEuroId) {
		this.extMotoreEuroId = extMotoreEuroId;
	}

	public int getExtTipoAlimentazioneId() {
		return this.extTipoAlimentazioneId;
	}

	public void setExtTipoAlimentazioneId(int extTipoAlimentazioneId) {
		this.extTipoAlimentazioneId = extTipoAlimentazioneId;
	}

	public int getExtTipoRimorchioId() {
		return this.extTipoRimorchioId;
	}

	public void setExtTipoRimorchioId(int extTipoRimorchioId) {
		this.extTipoRimorchioId = extTipoRimorchioId;
	}

	public double getLunghezza() {
		return this.lunghezza;
	}

	public void setLunghezza(double lunghezza) {
		this.lunghezza = lunghezza;
	}

	public int getNumeroAssi() {
		return this.numeroAssi;
	}

	public void setNumeroAssi(int numeroAssi) {
		this.numeroAssi = numeroAssi;
	}

	public double getPortataMax() {
		return this.portataMax;
	}

	public void setPortataMax(double portataMax) {
		this.portataMax = portataMax;
	}

}