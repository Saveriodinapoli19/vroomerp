package com.vroomerp.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the t_acquisti database table.
 * 
 */
@Entity
@Table(name="t_acquisti")
@NamedQuery(name="TAcquisti.findAll", query="SELECT t FROM TAcquisti t")
public class TAcquisti implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="acquiso_id")
	private Integer acquisoId;

	@Column(name="ext_auto_id")
	private Integer extAutoId;

	@Column(name="ext_user_id")
	private Integer extUserId;
	
	@Column(name="ext_tir_id")
	private Integer extTirId;
	
	@Column(name="ext_moto_id")
	private Integer extMotoId;
	
	public TAcquisti() {
	}

	public Integer getAcquisoId() {
		return acquisoId;
	}

	public void setAcquisoId(Integer acquisoId) {
		this.acquisoId = acquisoId;
	}

	public Integer getExtUserId() {
		return extUserId;
	}

	public void setExtUserId(Integer extUserId) {
		this.extUserId = extUserId;
	}

	public Integer getExtAutoId() {
		return extAutoId;
	}

	public void setExtAutoId(Integer extAutoId) {
		this.extAutoId = extAutoId;
	}

	public Integer getExtTirId() {
		return extTirId;
	}

	public void setExtTirId(Integer extTirId) {
		this.extTirId = extTirId;
	}

	public Integer getExtMotoId() {
		return extMotoId;
	}

	public void setExtMotoId(Integer extMotoId) {
		this.extMotoId = extMotoId;
	}

	

}