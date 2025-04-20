package com.vroomerp.common.dto.acquisti;

import java.util.List;

import com.vroomerp.common.dto.basic.BasicResponse;
import com.vroomerp.common.dto.mezzo.MezzoBean;
import com.vroomerp.common.dto.user.UserBean;

public class AcquistiResponse extends BasicResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private AcquistiBean acquistiBean;
	private List<AcquistiBean> acquistiBeanList;
	private MezzoBean mezzoBean;
	private UserBean userBean;
	private Double spesaTotale;
	public AcquistiBean getAcquistiBean() {
		return acquistiBean;
	}
	public void setAcquistiBean(AcquistiBean acquistiBean) {
		this.acquistiBean = acquistiBean;
	}
	public List<AcquistiBean> getAcquistiBeanList() {
		return acquistiBeanList;
	}
	public void setAcquistiBeanList(List<AcquistiBean> acquistiBeanList) {
		this.acquistiBeanList = acquistiBeanList;
	}
	public MezzoBean getMezzoBean() {
		return mezzoBean;
	}
	public void setMezzoBean(MezzoBean mezzoBean) {
		this.mezzoBean = mezzoBean;
	}
	public UserBean getUserBean() {
		return userBean;
	}
	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}
	public Double getSpesaTotale() {
		return spesaTotale;
	}
	public void setSpesaTotale(Double spesaTotale) {
		this.spesaTotale = spesaTotale;
	}
	
}
