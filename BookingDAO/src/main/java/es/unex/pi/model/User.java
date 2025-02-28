package es.unex.pi.model;

import java.util.Map;

public class User {

	private long id;
	private String name;
	private String surname;
	private String email;
	private String password;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean validate(Map<String, String> messages) {
	    if (password == null || password.trim().isEmpty()) {
	        messages.put("password", "Empty password");
	    } else if (!password.trim().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()\\-\\[\\]{}:;',?/*~$^+=<>\\.]).{8,20}$")) {
	        messages.put("password", "Invalid password: " + password.trim());
	    }
	    if (messages.isEmpty()) return true;
	    else return false;
	}
}
