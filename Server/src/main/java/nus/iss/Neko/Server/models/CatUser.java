package nus.iss.Neko.Server.models;

import java.util.Collection;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CatUser implements UserDetails {
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

	public static CatUser create(SqlRowSet rs) {
		CatUser user = new CatUser();
		user.setUserId(rs.getInt("id"));
		user.setUsername("%s %s".formatted(rs.getString("first_name"), rs.getString("last_name")));
		user.setEmail(rs.getString("email_address"));
		user.setPassword(rs.getString("password"));
		return user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		throw new UnsupportedOperationException("Unimplemented method 'getAuthorities'");
	}

	@Override
	public boolean isAccountNonExpired() {
		throw new UnsupportedOperationException("Unimplemented method 'isAccountNonExpired'");
	}

	@Override
	public boolean isAccountNonLocked() {
		throw new UnsupportedOperationException("Unimplemented method 'isAccountNonLocked'");
	}

	@Override
	public boolean isCredentialsNonExpired() {
		throw new UnsupportedOperationException("Unimplemented method 'isCredentialsNonExpired'");
	}

	@Override
	public boolean isEnabled() {
		throw new UnsupportedOperationException("Unimplemented method 'isEnabled'");
	}
}
