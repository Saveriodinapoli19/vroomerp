package com.vroomerp.common.dto.acquisti;

import com.vroomerp.common.dto.auto.AutoBean;
import com.vroomerp.common.dto.moto.MotoBean;
import com.vroomerp.common.dto.tir.TirBean;
import com.vroomerp.common.dto.user.UserBean;

public class AcquistiBean {


	private Integer acquisoId;

	private Integer extAutoId;
	
	private Integer extTirId;
	
	private Integer extMotoId;
	
	private UserBean userBean;
	
	private AutoBean autoBean;
	
	private TirBean tirBean;
	
	private MotoBean motoBean;
	
	private Double spesaTotale;


	private Integer extUserId;

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

	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	public AutoBean getAutoBean() {
		return autoBean;
	}

	public void setAutoBean(AutoBean autoBean) {
		this.autoBean = autoBean;
	}

	public TirBean getTirBean() {
		return tirBean;
	}

	public void setTirBean(TirBean tirBean) {
		this.tirBean = tirBean;
	}

	public MotoBean getMotoBean() {
		return motoBean;
	}

	public void setMotoBean(MotoBean motoBean) {
		this.motoBean = motoBean;
	}

	public Double getSpesaTotale() {
		return spesaTotale;
	}

	public void setSpesaTotale(Double spesaTotale) {
		this.spesaTotale = spesaTotale;
	}

	
	

}
