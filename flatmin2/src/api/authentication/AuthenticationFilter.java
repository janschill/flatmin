package api.authentication;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

import com.google.common.net.HttpHeaders;

import api.model.AuthenticationToken;

@Provider
public class AuthenticationFilter implements ContainerRequestFilter
{
	private final String TAG = "AuthenticationFilter: ";

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException
	{
		String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer "))
		{
			AuthenticationToken authenticationToken = new AuthenticationToken();
			authenticationToken.setToken(authorizationHeader.substring(7));
			System.out.println(TAG + authenticationToken.getToken());
			handleToken(authenticationToken, requestContext);
			return;
		}
	}

	private void handleToken(AuthenticationToken authenticationToken, ContainerRequestContext requestContext)
	{
	}

}
