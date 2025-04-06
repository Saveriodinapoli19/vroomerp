package model;

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
	@Column(name="mezzo_id")
	private int mezzoId;

	@Column(name="anno_immatricolazione")
	private int annoImmatricolazione;

	@Column(name="auto_id")
	private int autoId;

	private String colore;

	@Temporal(TemporalType.DATE)
	@Column(name="data_acquisto")
	private Date dataAcquisto;

	@Temporal(TemporalType.DATE)
	@Column(name="data_inserimento")
	private Date dataInserimento;

	@Column(name="ext_tipo_alimentazione")
	private int extTipoAlimentazione;

	@Column(name="flag_acquistato")
	private int flagAcquistato;

	@Column(name="flag_deleted")
	private int flagDeleted;

	private String marca;

	private String modello;

	@Column(name="moto_id")
	private int motoId;

	@Column(name="t_prezzo")
	private BigDecimal tPrezzo;

	private String targa;

	@Column(name="tir_id")
	private int tirId;

	public TMezzo() {
	}

	public int getMezzoId() {
		return this.mezzoId;
	}

	public void setMezzoId(int mezzoId) {
		this.mezzoId = mezzoId;
	}

	public int getAnnoImmatricolazione() {
		return this.annoImmatricolazione;
	}

	public void setAnnoImmatricolazione(int annoImmatricolazione) {
		this.annoImmatricolazione = annoImmatricolazione;
	}

	public int getAutoId() {
		return this.autoId;
	}

	public void setAutoId(int autoId) {
		this.autoId = autoId;
	}

	public String getColore() {
		return this.colore;
	}

	public void setColore(String colore) {
		this.colore = colore;
	}

	public Date getDataAcquisto() {
		return this.dataAcquisto;
	}

	public void setDataAcquisto(Date dataAcquisto) {
		this.dataAcquisto = dataAcquisto;
	}

	public Date getDataInserimento() {
		return this.dataInserimento;
	}

	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	public int getExtTipoAlimentazione() {
		return this.extTipoAlimentazione;
	}

	public void setExtTipoAlimentazione(int extTipoAlimentazione) {
		this.extTipoAlimentazione = extTipoAlimentazione;
	}

	public int getFlagAcquistato() {
		return this.flagAcquistato;
	}

	public void setFlagAcquistato(int flagAcquistato) {
		this.flagAcquistato = flagAcquistato;
	}

	public int getFlagDeleted() {
		return this.flagDeleted;
	}

	public void setFlagDeleted(int flagDeleted) {
		this.flagDeleted = flagDeleted;
	}

	public String getMarca() {
		return this.marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModello() {
		return this.modello;
	}

	public void setModello(String modello) {
		this.modello = modello;
	}

	public int getMotoId() {
		return this.motoId;
	}

	public void setMotoId(int motoId) {
		this.motoId = motoId;
	}

	public BigDecimal getTPrezzo() {
		return this.tPrezzo;
	}

	public void setTPrezzo(BigDecimal tPrezzo) {
		this.tPrezzo = tPrezzo;
	}

	public String getTarga() {
		return this.targa;
	}

	public void setTarga(String targa) {
		this.targa = targa;
	}

	public int getTirId() {
		return this.tirId;
	}

	public void setTirId(int tirId) {
		this.tirId = tirId;
	}

}