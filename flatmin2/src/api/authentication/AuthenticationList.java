package api.authentication;

import java.util.ArrayList;
import java.util.List;

public class AuthenticationList
{
	private static List<SecuredContext> list = new ArrayList<SecuredContext>();

	public AuthenticationList()
	{
		list.add(new SecuredContext("GET", "users"));
		list.add(new SecuredContext("POST", "income"));
		list.add(new SecuredContext("POST", "expenses"));
		list.add(new SecuredContext("POST", "shoppinglist"));
	}

	public List<SecuredContext> getAuthenticationList()
	{
		return list;
	}

	public void addSecuredContext(SecuredContext securedContext)
	{
		list.add(securedContext);
	}
}