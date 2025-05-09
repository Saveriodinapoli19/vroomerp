package com.vroomerp.common.dto.moto;

import java.util.List;

import com.vroomerp.common.dto.basic.BasicResponse;

public class MotoResponse extends BasicResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private MotoBean motoBean;
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

}
