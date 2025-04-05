package com.vroomerp.common.dto.user;

public class UserBean {

	private Integer userId;

	private String cognome;

	private String email;

	private Integer extRuoloUtenteId;

	private Integer flagDeleted;

	private String nome;

	private String password;

	private String ruolo;

	private String telefono;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getExtRuoloUtenteId() {
		return extRuoloUtenteId;
	}

	public void setExtRuoloUtenteId(Integer extRuoloUtenteId) {
		this.extRuoloUtenteId = extRuoloUtenteId;
	}

	public Integer getFlagDeleted() {
		return flagDeleted;
	}

	public void setFlagDeleted(Integer flagDeleted) {
		this.flagDeleted = flagDeleted;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	

}
