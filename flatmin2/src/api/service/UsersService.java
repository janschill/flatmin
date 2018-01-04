package api.service;

import java.sql.SQLException;
import java.util.List;

import api.database.Database;
import api.model.AuthenticationToken;
import api.model.Users;

public class UsersService
{
	private final static String TAG = "UsersService: ";

	public List<Users> getUsers() throws SQLException
	{
		return Database.getUsers();
	}

	public Users getUser(long id) throws SQLException
	{
		System.out.println(TAG + id);
		return Database.getUser(id);
	}

	public Users insertUser(Users user) throws SQLException
	{
		return Database.insertUser(user);
	}

	public Users updateUser(Users user) throws SQLException
	{
		return Database.updateUser(user);
	}

	public void deleteUser(long id) throws SQLException
	{
		Database.deleteUser(id);
	}

	public Users findUserByUsername(String username) throws SQLException
	{
		return Database.findUserByUsername(username);
	}

	public void setTokenToUser(Users user, AuthenticationToken token) throws SQLException
	{
		Database.setTokenToUser(user, token);
	}

}
