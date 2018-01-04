package api.authentication;

import java.net.URISyntaxException;
import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import api.model.AuthenticationToken;
import api.model.UserCredentials;
import api.model.Users;
import api.service.AuthenticationTokenService;
import api.service.UsernamePasswordValidator;

@Path("/authenticate")
public class Authenticate
{
	private UsernamePasswordValidator usernamePasswordValidator = new UsernamePasswordValidator();
	private AuthenticationTokenService authenticationTokenService = new AuthenticationTokenService();

	/** the token generation is not taking in any user params, yet
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws URISyntaxException
	 * @throws SQLException
	 */
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response authenticateLogin(@FormParam("username") String username, @FormParam("password") String password)
			throws URISyntaxException, SQLException
	{
		UserCredentials userCredentials = new UserCredentials();
		userCredentials.setUsername(username);
		userCredentials.setPassword(password);

		Users user = usernamePasswordValidator.validateCredentials(userCredentials.getUsername(),
				userCredentials.getPassword());
		String token = authenticationTokenService.issueToken();
		AuthenticationToken authenticationToken = new AuthenticationToken();
		authenticationToken.setToken(token);
		return Response.ok(authenticationToken).build();
	}
}
