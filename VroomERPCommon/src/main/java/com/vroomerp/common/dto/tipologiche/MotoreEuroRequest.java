package com.vroomerp.common.dto.tipologiche;

import java.util.List;

import com.vroomerp.common.dto.basic.BasicRequest;

public class MotoreEuroRequest extends BasicRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private MotoreEuroBean motoreEuroBean;
	private List<MotoreEuroBean> motoreEuroBeanList;

	public MotoreEuroBean getMotoreEuroBean() {
		return motoreEuroBean;
	}

	public void setMotoreEuroBean(MotoreEuroBean motoreEuroBean) {
		this.motoreEuroBean = motoreEuroBean;
	}

	public List<MotoreEuroBean> getMotoreEuroBeanList() {
		return motoreEuroBeanList;
	}

	public void setMotoreEuroBeanList(List<MotoreEuroBean> motoreEuroBeanList) {
		this.motoreEuroBeanList = motoreEuroBeanList;
	}

}
