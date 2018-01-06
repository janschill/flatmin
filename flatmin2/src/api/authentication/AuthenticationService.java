package api.authentication;

import java.sql.SQLException;

import api.database.Database;
import api.model.AuthenticationToken;

public class AuthenticationService
{
	public boolean validateToken(AuthenticationToken token) throws SQLException
	{
		return Database.validateToken(token);
	}
}
