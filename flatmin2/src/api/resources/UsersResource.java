package api.resources;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.mindrot.jbcrypt.BCrypt;

import api.model.Users;
import api.service.UsersService;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsersResource
{

	UsersService usersService = new UsersService();

	@GET
	public List<Users> getUsers(@Context UriInfo uriInfo) throws SQLException
	{
		List<Users> list = usersService.getUsers();
		for (Users user : list)
		{
			user.addLink(getUriForSelf(uriInfo, user), "self");
		}
		return list;
	}

	@GET
	@Path("/{id}")
	public Users getUser(@PathParam("id") long id, @Context UriInfo uriInfo) throws SQLException
	{
		Users user = usersService.getUser(id);
		user.addLink(getUriForSelf(uriInfo, user), "self");
		return user;
	}

	@POST
	public Response insertUser(Users user, @Context UriInfo uriInfo) throws Exception
	{
		user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
		Users newUser = usersService.insertUser(user);
		String id = String.valueOf(newUser.getIdusers());
		URI uri = uriInfo.getAbsolutePathBuilder().path(id).build();
		return Response.created(uri).entity(newUser).build();
	}

	@PUT
	@Path("/{id}")
	public Users updateUser(@PathParam("id") long id, Users user) throws SQLException
	{
		user.setIdusers(id);
		return usersService.updateUser(user);
	}

	@DELETE
	@Path("/{id}")
	public void deleteUser(@PathParam("id") long id) throws SQLException
	{
		usersService.deleteUser(id);
	}

	private String getUriForSelf(UriInfo uriInfo, Users user)
	{
		String uri = uriInfo.getBaseUriBuilder().path(UsersResource.class).path(Long.toString(user.getIdusers()))
				.build().toString();
		return uri;
	}
}
