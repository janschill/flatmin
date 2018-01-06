package api.authentication;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import api.model.AuthenticationToken;

@Provider
public class AuthenticationFilter implements ContainerRequestFilter
{
	private final String TAG = "AuthenticationFilter: ";

	private AuthenticationService authenticationService = new AuthenticationService();
	private AuthenticationList authenticationList = new AuthenticationList();

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException
	{
		System.out.println(requestContext.getMethod());
		List<SecuredContext> securedList = authenticationList.getAuthenticationList();

		for (SecuredContext context : securedList)
		{
			if (requestContext.getUriInfo().getPath().contains(context.getUrl())
					&& requestContext.getMethod().equals(context.getMethod()))
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
}