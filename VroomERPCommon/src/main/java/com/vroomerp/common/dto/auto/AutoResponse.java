package com.vroomerp.common.dto.auto;

import java.util.List;

import com.vroomerp.common.dto.basic.BasicResponse;

public class AutoResponse extends BasicResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private AutoBean autoBean;
	private List<AutoBean> autoBeanList;

	public AutoBean getAutoBean() {
		return autoBean;
	}

	public void setAutoBean(AutoBean autoBean) {
		this.autoBean = autoBean;
	}

	public List<AutoBean> getAutoBeanList() {
		return autoBeanList;
	}

	public void setAutoBeanList(List<AutoBean> autoBeanList) {
		this.autoBeanList = autoBeanList;
	}

}
