package nus.iss.Neko.Server.utils;

public interface Queries {
	public static final String SQL_FIND_USERS_BY_EMAIL = "select * from users where email_address = ?";
}