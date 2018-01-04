package api.client;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHasher
{

	public static void main(String[] args)
	{
		String password = "50cent";
		String salt = BCrypt.gensalt();
		String hash = BCrypt.hashpw(password, salt);
		System.out.println(hash);

		if (null == hash || !hash.startsWith("$2a$"))
		{
			System.out.println("Hashed password is invalid!");
		}

		System.out.println(BCrypt.checkpw(password, hash));
	}

}
