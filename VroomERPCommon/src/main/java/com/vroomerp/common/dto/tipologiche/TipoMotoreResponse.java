package com.vroomerp.common.dto.tipologiche;

import java.util.List;

import com.vroomerp.common.dto.basic.BasicResponse;

public class TipoMotoreResponse extends BasicResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private TipoMotoreBean tipoMotoreBean;
	private List<TipoMotoreBean> tipoMotoreBeanList;
	public TipoMotoreBean getTipoMotoreBean() {
		return tipoMotoreBean;
	}
	public void setTipoMotoreBean(TipoMotoreBean tipoMotoreBean) {
		this.tipoMotoreBean = tipoMotoreBean;
	}
	public List<TipoMotoreBean> getTipoMotoreBeanList() {
		return tipoMotoreBeanList;
	}
	public void setTipoMotoreBeanList(List<TipoMotoreBean> tipoMotoreBeanList) {
		this.tipoMotoreBeanList = tipoMotoreBeanList;
	}


}
