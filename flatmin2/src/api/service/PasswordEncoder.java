package api.service;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncoder
{
	public String hashPassword(String password)
	{
		String salt = BCrypt.gensalt();
		return BCrypt.hashpw(password, salt);
	}

	public boolean checkPassword(String password, String hash)
	{
		if (null == hash || !hash.startsWith("$2a$"))
		{
			throw new RuntimeException("Hashed password is invalid!");
		}

		return BCrypt.checkpw(password, hash);
	}
}
