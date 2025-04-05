package com.vroomerp.ejb;

import java.util.List;

import javax.ejb.Remote;

import com.vroomerp.model.TRuoloUtente;
import com.vroomerp.model.TUser;

@Remote
public interface UserEJBInterface {

	Integer insertUser(TUser user);

	TUser updateUser(TUser user);

	TUser findByEmail(String email);

	TRuoloUtente findByRuoloId(Integer ruoloUtenteId);

	TUser findById(Integer userId);

	TUser deleteUser(TUser user);

	List<TUser> findAll();

}
