package com.vroomerp.common.dto.dashboard;

import java.util.List;

import com.vroomerp.common.dto.auto.AutoBean;
import com.vroomerp.common.dto.basic.BasicResponse;
import com.vroomerp.common.dto.moto.MotoBean;
import com.vroomerp.common.dto.tir.TirBean;

public class DashboardResponse extends BasicResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<AutoBean> autoList;
	private List<MotoBean> motoList;
	private List<TirBean> tirList;
	private int countAuto;
	private int countMoto;
	private int countTir;
	public List<AutoBean> getAutoList() {
		return autoList;
	}
	public void setAutoList(List<AutoBean> autoList) {
		this.autoList = autoList;
	}
	public List<MotoBean> getMotoList() {
		return motoList;
	}
	public void setMotoList(List<MotoBean> motoList) {
		this.motoList = motoList;
	}
	public List<TirBean> getTirList() {
		return tirList;
	}
	public void setTirList(List<TirBean> tirList) {
		this.tirList = tirList;
	}
	public int getCountAuto() {
		return countAuto;
	}
	public void setCountAuto(int countAuto) {
		this.countAuto = countAuto;
	}
	public int getCountMoto() {
		return countMoto;
	}
	public void setCountMoto(int countMoto) {
		this.countMoto = countMoto;
	}
	public int getCountTir() {
		return countTir;
	}
	public void setCountTir(int countTir) {
		this.countTir = countTir;
	}
	
	

}
