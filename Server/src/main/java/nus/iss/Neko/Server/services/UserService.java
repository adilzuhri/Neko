package nus.iss.Neko.Server.services;

import java.util.List;
import nus.iss.Neko.Server.models.User;

public interface UserService {

	public User insert(User userVO);

	public List<User> findAll();

	public void delete(int id);

	public User findById(int id);

	public User updateUser(int id, User userVO);
}
