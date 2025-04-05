package com.vroomerp.ejb;

import javax.ejb.Remote;

import com.vroomerp.model.TUser;

@Remote
public interface UserEJBInterface {

	Integer insertUser(TUser user);

	TUser updateUser(TUser user);

}
