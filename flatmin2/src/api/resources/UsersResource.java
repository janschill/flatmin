package api.resources;

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
import javax.ws.rs.core.MediaType;

import api.model.Users;
import api.service.UsersService;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsersResource
{
	private final static String TAG = "UsersResource: ";

	UsersService usersService = new UsersService();

	@GET
	public List<Users> getUsers() throws SQLException
	{
		return usersService.getUsers();
	}

	@GET
	@Path("/{id}")
	public Users getUser(@PathParam("id") long id) throws SQLException
	{
		System.out.println(TAG + id);
		return usersService.getUser(id);
	}

	@POST
	public Users insertUser(Users user) throws SQLException
	{
		return usersService.insertUser(user);
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

}
