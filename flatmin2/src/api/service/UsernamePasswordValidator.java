package api.service;

import java.sql.SQLException;

import api.exception.AuthenticationException;
import api.model.Users;

public class UsernamePasswordValidator
{
	private UsersService usersSerivce = new UsersService();
	private PasswordEncoder passwordEncoder = new PasswordEncoder();

	public Users validateCredentials(String username, String password) throws SQLException
	{
		Users user = usersSerivce.findUserByUsername(username);

		if (user == null)
			throw new AuthenticationException("Bad credentials.");

		if (!passwordEncoder.checkPassword(password, user.getPassword()))
			throw new AuthenticationException("Bad credentials.");

		System.out.println(user.getPassword());
		return user;
	}
}
