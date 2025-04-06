package com.vroomerp.common.dto.tipologiche;

import java.util.List;

import com.vroomerp.common.dto.basic.BasicResponse;

public class TipoMotoResponse extends BasicResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private TipoMotoBean tipoMotoBean;
	private List<TipoMotoBean> tipoMotoBeanList;

	public TipoMotoBean getTipoMotoBean() {
		return tipoMotoBean;
	}

	public void setTipoMotoBean(TipoMotoBean tipoMotoBean) {
		this.tipoMotoBean = tipoMotoBean;
	}

	public List<TipoMotoBean> getTipoMotoBeanList() {
		return tipoMotoBeanList;
	}

	public void setTipoMotoBeanList(List<TipoMotoBean> tipoMotoBeanList) {
		this.tipoMotoBeanList = tipoMotoBeanList;
	}

}
