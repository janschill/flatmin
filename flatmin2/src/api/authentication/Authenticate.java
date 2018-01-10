package api.authentication;

import java.net.URISyntaxException;
import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import api.model.AuthenticationToken;
import api.model.UserCredentials;
import api.model.Users;
import api.service.AuthenticationTokenService;
import api.service.UsernamePasswordValidator;
import api.service.UsersService;

@Path("/authenticate")
public class Authenticate
{
	private UsernamePasswordValidator usernamePasswordValidator = new UsernamePasswordValidator();
	private AuthenticationTokenService authenticationTokenService = new AuthenticationTokenService();
	private UsersService usersService = new UsersService();

	/** the token generation is not taking in any user params, yet
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws URISyntaxException
	 * @throws SQLException
	 */
	@POST
	// @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response authenticateLogin(UserCredentials userCredentials) throws URISyntaxException, SQLException
	{
		// @FormParam("username") String username, @FormParam("password") String
		// password
		// UserCredentials userCredentials = new UserCredentials();
		// userCredentials.setUsername(username);
		// userCredentials.setPassword(password);

		Users user = usernamePasswordValidator.validateCredentials(userCredentials.getUsername(),
				userCredentials.getPassword());
		if (user != null)
		{
			String token = authenticationTokenService.issueToken();
			AuthenticationToken authenticationToken = new AuthenticationToken();
			authenticationToken.setToken(token);
			usersService.setTokenToUser(user, authenticationToken);
			return Response.ok(authenticationToken).build();
		} else
		{
			return Response.status(Status.UNAUTHORIZED).build();
		}

	}
}
