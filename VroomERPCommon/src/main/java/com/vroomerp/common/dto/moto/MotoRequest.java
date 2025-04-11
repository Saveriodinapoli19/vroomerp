package com.vroomerp.common.dto.moto;

import java.util.List;

import com.vroomerp.common.dto.basic.BasicRequest;
import com.vroomerp.common.dto.mezzo.MezzoBean;

public class MotoRequest extends BasicRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private MotoBean motoBean;
	private MezzoBean mezzoBean;
	private List<MotoBean> listaMoto;
	public MotoBean getMotoBean() {
		return motoBean;
	}
	public void setMotoBean(MotoBean motoBean) {
		this.motoBean = motoBean;
	}
	public List<MotoBean> getListaMoto() {
		return listaMoto;
	}
	public void setListaMoto(List<MotoBean> listaMoto) {
		this.listaMoto = listaMoto;
	}
	public MezzoBean getMezzoBean() {
		return mezzoBean;
	}
	public void setMezzoBean(MezzoBean mezzoBean) {
		this.mezzoBean = mezzoBean;
	}



}
