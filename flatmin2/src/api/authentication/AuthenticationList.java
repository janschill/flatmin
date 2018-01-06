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

		list.add(new SecuredContext("DELETE", "users"));
		list.add(new SecuredContext("DELETE", "shoppinglist"));
		list.add(new SecuredContext("DELETE", "income"));
		list.add(new SecuredContext("DELETE", "expenses"));

		list.add(new SecuredContext("UPDATE", "users"));
		list.add(new SecuredContext("UPDATE", "shoppinglist"));
		list.add(new SecuredContext("UPDATE", "income"));
		list.add(new SecuredContext("UPDATE", "expenses"));

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