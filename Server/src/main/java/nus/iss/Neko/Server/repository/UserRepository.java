package nus.iss.Neko.Server.repository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import nus.iss.Neko.Server.models.User;
import static nus.iss.Neko.Server.utils.Queries.*;

@Repository
public class UserRepository {

	@Autowired
	private JdbcTemplate template;

	public Optional<User> findUserByEmail(String email) {
		SqlRowSet rs = template.queryForRowSet(SQL_FIND_USERS_BY_EMAIL, email);
		if (!rs.next())
			return Optional.empty();
		return Optional.of(User.create(rs));
	}

}
