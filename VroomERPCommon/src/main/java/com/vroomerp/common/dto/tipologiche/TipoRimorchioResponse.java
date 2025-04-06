package com.vroomerp.common.dto.tipologiche;

import java.util.List;

import com.vroomerp.common.dto.basic.BasicResponse;

public class TipoRimorchioResponse extends BasicResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private TipoRimorchioBean tipoRimorchioBean;
	private List<TipoRimorchioBean> tipoRimorchioBeanList;

	public TipoRimorchioBean getTipoRimorchioBean() {
		return tipoRimorchioBean;
	}

	public void setTipoRimorchioBean(TipoRimorchioBean tipoRimorchioBean) {
		this.tipoRimorchioBean = tipoRimorchioBean;
	}

	public List<TipoRimorchioBean> getTipoRimorchioBeanList() {
		return tipoRimorchioBeanList;
	}

	public void setTipoRimorchioBeanList(List<TipoRimorchioBean> tipoRimorchioBeanList) {
		this.tipoRimorchioBeanList = tipoRimorchioBeanList;
	}


}
