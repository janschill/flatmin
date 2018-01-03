package api.authentication;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.internal.util.Base64;

@Provider
public class SecurityFilter implements ContainerRequestFilter
{
	private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
	private static final String AUTHORIZATION_HEADER_PREFIX = "Basic: ";
	private static final String SECURED_URL_PREFIX = "users";

	@Override
	public void filter(ContainerRequestContext request) throws IOException
	{
		if (request.getUriInfo().getPath().contains(SECURED_URL_PREFIX))
		{
			List<String> list = request.getHeaders().get(AUTHORIZATION_HEADER_KEY);
			if (list != null && list.size() > 0)
			{
				String token = list.get(0);
				token = token.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
				String decoded = Base64.decodeAsString(token);
				StringTokenizer tokenized = new StringTokenizer(decoded, ":");
				String username = tokenized.nextToken();
				String password = tokenized.nextToken();

				if (username.equals("janschill") && username.equals("50cent"))
				{
					return;
				}

			}
			Response unauthorizedStatus = Response.status(Response.Status.UNAUTHORIZED).entity("Not authorized!")
					.build();

			request.abortWith(unauthorizedStatus);

		}
	}

}
