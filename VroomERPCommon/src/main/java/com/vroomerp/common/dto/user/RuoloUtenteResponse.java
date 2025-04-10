package com.vroomerp.common.dto.user;

import java.util.List;

import com.vroomerp.common.dto.basic.BasicResponse;

public class RuoloUtenteResponse extends BasicResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private RuoloUtenteBean ruoloUtenteBean;
	private List<RuoloUtenteBean> listaRuoloUtenteBean;
	public RuoloUtenteBean getRuoloUtenteBean() {
		return ruoloUtenteBean;
	}
	public void setRuoloUtenteBean(RuoloUtenteBean ruoloUtenteBean) {
		this.ruoloUtenteBean = ruoloUtenteBean;
	}
	public List<RuoloUtenteBean> getListaRuoloUtenteBean() {
		return listaRuoloUtenteBean;
	}
	public void setListaRuoloUtenteBean(List<RuoloUtenteBean> listaRuoloUtenteBean) {
		this.listaRuoloUtenteBean = listaRuoloUtenteBean;
	}

	

}
