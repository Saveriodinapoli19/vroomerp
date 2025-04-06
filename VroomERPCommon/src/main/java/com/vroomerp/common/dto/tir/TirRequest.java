package com.vroomerp.common.dto.tir;

import java.util.List;

import com.vroomerp.common.dto.basic.BasicRequest;

public class TirRequest extends BasicRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private TirBean tirBean;
	private List<TirBean> tirBeanList;

	public TirBean getTirBean() {
		return tirBean;
	}

	public void setTirBean(TirBean tirBean) {
		this.tirBean = tirBean;
	}

	public List<TirBean> getTirBeanList() {
		return tirBeanList;
	}

	public void setTirBeanList(List<TirBean> tirBeanList) {
		this.tirBeanList = tirBeanList;
	}

}
