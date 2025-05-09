package com.vroomerp.common.dto.auto;

import java.util.List;

import com.vroomerp.common.dto.basic.BasicRequest;
import com.vroomerp.common.dto.mezzo.MezzoBean;

public class AutoRequest extends BasicRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private AutoBean autoBean;
	private List<AutoBean> autoBeanList;
	private MezzoBean mezzoBean;
	
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

	public MezzoBean getMezzoBean() {
		return mezzoBean;
	}

	public void setMezzoBean(MezzoBean mezzoBean) {
		this.mezzoBean = mezzoBean;
	}

}
