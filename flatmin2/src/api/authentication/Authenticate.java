package api.authentication;

import java.net.URISyntaxException;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import api.database.Database;

@Path("/authenticate")
public class Authenticate
{
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response authenticateLogin(@FormParam("username") String username, @FormParam("password") String password)
			throws URISyntaxException
	{
		try
		{
			if (Database.checkLogin(username, password))
				return Response.ok().header("Authorization", "Basic amFuc2NoaWxsOjUwY2VudA==").build();
			else
				return Response.status(500).entity("Unable to login").build();
		} catch (Exception e)
		{
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}
	}

}
