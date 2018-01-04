package api.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import api.model.Users;
import sun.misc.BASE64Encoder;

public class RestAuthClient
{

	public static void main(String[] args)
	{
		Client client = ClientBuilder.newClient();

		String url = "http://localhost:8080/flatmin2/app/users";
		String username = "janschill";
		String password = "50cent";
		String login = username + ":" + password;
		String encoded64 = new BASE64Encoder().encode(login.getBytes());

		
		
		Response response = Response.ok().header("Authorization", "Basic " + encoded64).build();

		System.out.println(response);
	}

}