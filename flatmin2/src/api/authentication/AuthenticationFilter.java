package api.authentication;

import java.io.IOException;
import java.sql.SQLException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.google.common.net.HttpHeaders;

import api.model.AuthenticationToken;

@Provider
public class AuthenticationFilter implements ContainerRequestFilter
{
	private final String TAG = "AuthenticationFilter: ";
	private static final String SECURED_URL_PREFIX = "users";
	private AuthenticationService authenticationService = new AuthenticationService();

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException
	{
		if (requestContext.getUriInfo().getPath().contains(SECURED_URL_PREFIX))
		{
			String authorizationHeader = requestContext.getHeaderString("token");
			if (authorizationHeader != null)
			{
				AuthenticationToken authenticationToken = new AuthenticationToken();
				System.out.println(authorizationHeader);
				authenticationToken.setToken(authorizationHeader);
				System.out.println(TAG + authenticationToken.getToken());
				try
				{
					if (authenticationService.validateToken(authenticationToken))
					{
						return;
					}
				} catch (SQLException e)
				{
					e.printStackTrace();
				}
			}

			Response unauthorizedStatus = Response.status(Response.Status.UNAUTHORIZED).entity("Not authorized!")
					.build();

			requestContext.abortWith(unauthorizedStatus);
		}

	}
}