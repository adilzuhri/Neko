package nus.iss.Neko.Server.repository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import nus.iss.Neko.Server.models.CatUser;
import static nus.iss.Neko.Server.utils.Queries.*;

@Repository
public class UserRepository {

	private PasswordEncoder passwordEncoder;

	public UserRepository(@Lazy PasswordEncoder encoder) {
		this.passwordEncoder = encoder;
	}

	@Autowired
	private JdbcTemplate template;

	public Optional <CatUser> checkUserExistsByEmail(String email) {
		SqlRowSet result = template.queryForRowSet(SQL_GET_USER, email);

		if (!result.next()) {
			return Optional.empty();
		} else {
			CatUser user = new CatUser();
			user.setUsername(result.getString("username"));
			user.setEmail(email);
			user.setPassword(result.getString("password"));
			return Optional.of(user);
		}
	}

	public boolean createNewUser(CatUser user) {
		int added = template.update(SQL_NEW_USER,
				user.getUsername(), user.getEmail(),
				passwordEncoder.encode(user.getPassword()));

		return added == 1;
	}

}
