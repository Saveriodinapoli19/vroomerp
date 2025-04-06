package com.vroomerp.common.dto.tipologiche;

import java.util.List;

import com.vroomerp.common.dto.basic.BasicRequest;

public class TipoAlimentazioneRequest extends BasicRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private TipoAlimentazioneBean tipoAlimentazioneBean;
	private List<TipoAlimentazioneBean> tipoAlimentazioneBeanList;

	public TipoAlimentazioneBean getTipoAlimentazioneBean() {
		return tipoAlimentazioneBean;
	}

	public void setTipoAlimentazioneBean(TipoAlimentazioneBean tipoAlimentazioneBean) {
		this.tipoAlimentazioneBean = tipoAlimentazioneBean;
	}

	public List<TipoAlimentazioneBean> getTipoAlimentazioneBeanList() {
		return tipoAlimentazioneBeanList;
	}

	public void setTipoAlimentazioneBeanList(List<TipoAlimentazioneBean> tipoAlimentazioneBeanList) {
		this.tipoAlimentazioneBeanList = tipoAlimentazioneBeanList;
	}

}
