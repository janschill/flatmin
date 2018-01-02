package api.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import api.model.Users;

public class RestClient
{

	public static void main(String[] args)
	{
		Client client = ClientBuilder.newClient();

		Response response = client.target("http://localhost:8080/flatmin2/app/users/5").request().get();

		Users user = response.readEntity(Users.class);
		System.out.println(user.getUsername());
	}

}
