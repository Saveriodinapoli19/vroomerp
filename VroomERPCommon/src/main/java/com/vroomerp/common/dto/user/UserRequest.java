package com.vroomerp.common.dto.user;

import java.util.List;

import com.vroomerp.common.dto.basic.BasicRequest;

public class UserRequest extends BasicRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private UserBean userBean;
	private List<UserBean> userBeanList;

	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	public List<UserBean> getUserBeanList() {
		return userBeanList;
	}

	public void setUserBeanList(List<UserBean> userBeanList) {
		this.userBeanList = userBeanList;
	}

}
