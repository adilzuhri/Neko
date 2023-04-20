package nus.iss.Neko.Server.models;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class User {
	private int userId;
	private String username;
	private String email;
	private String password;

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return this.email;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return this.username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return this.password;
	}

	public static User create(SqlRowSet rs) {
		User user = new User();
		user.setUserId(rs.getInt("id"));
		user.setUsername("%s %s".formatted(rs.getString("first_name"), rs.getString("last_name")));
		user.setEmail(rs.getString("email_address"));
		user.setPassword(rs.getString("password"));
		return user;
	}
}
