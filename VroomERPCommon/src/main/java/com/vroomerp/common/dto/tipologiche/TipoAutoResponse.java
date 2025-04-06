package com.vroomerp.common.dto.tipologiche;

import java.util.List;

import com.vroomerp.common.dto.basic.BasicResponse;

public class TipoAutoResponse extends BasicResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private TipoAutoBean tipoAutoBean;
	private List<TipoAutoBean> tipoAutoBeanList;
	public TipoAutoBean getTipoAutoBean() {
		return tipoAutoBean;
	}
	public void setTipoAutoBean(TipoAutoBean tipoAutoBean) {
		this.tipoAutoBean = tipoAutoBean;
	}
	public List<TipoAutoBean> getTipoAutoBeanList() {
		return tipoAutoBeanList;
	}
	public void setTipoAutoBeanList(List<TipoAutoBean> tipoAutoBeanList) {
		this.tipoAutoBeanList = tipoAutoBeanList;
	}

}
