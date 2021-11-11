package vn.edu.fptu.PrimaryEducationManagementSystem.dto;

import java.util.List;

public class JwtResponse {

	private String token;
	private String type = "Baerer";
	private int id;
	private String username;
	private List<String> roles;

	public JwtResponse() {
		super();
	}

	public JwtResponse(String token, int id, String username, List<String> roles) {
		super();
		this.token = token;
		this.id = id;
		this.username = username;
		this.roles = roles;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

}
