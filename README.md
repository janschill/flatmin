# flatmin

## Setup

### Application server
We will be using GlassFish.

### Database
Our database of choice is PostgreSQL

### web.xml
Here we configure our Web API URL-Pattern `<servlet-mapping>` and tell the application where to look for the Java classes to handle requests `<init-param>`.
```
<servlet>
  <servlet-name>Flatmin</servlet-name>
  <servlet-class>org.glassfish.jersey.servlet.ServletContainer</servlet-class>
  <init-param>
  	<param-name>jersey.config.server.provider.packages</param-name>
  	<param-value>api</param-value>
  </init-param>
  <load-on-startup>1</load-on-startup>
</servlet>
<servlet-mapping>
  <servlet-name>Flatmin</servlet-name>
  	<url-pattern>/app/*</url-pattern>
</servlet-mapping>
```
We can then structure our Java Resources in the `api` package and use the `@Path` - Annotation for classes and/or methods to handle the requests.

## REST Client


### Errors
If you encounter this error message with your Java Rest Client:

> java.lang.NoClassDefFoundError: org/glassfish/json/jaxrs/JsonStructureBodyReader

you have to add these two libraries `javax.json.jar` and `jsonp-jaxrs.jar`, both located in your `glassfish4/glassfish/modules`-path,  to you `Build Path`.

Another error that might occur when running the Java Rest Client:

> Exception in thread "main" org.glassfish.jersey.message.internal.MessageBodyProviderNotFoundException: MessageBodyReader not found for media type=text/html, type=class api.model.AuthenticationToken, genericType=class api.model.AuthenticationToken.

The reason for this might be an old database without Tokens. Try restoring a new backup.


## Resources
RESTful services use resources as


## Header

## Authentication

Traditional session-based authentication works as the client sends his login information to the server.
The server accepts or rejects the data and creates a session and send the session token to the client.
The client stores this token in a cookie and sends with every request this session token, which then gets validated by the server.
This mechanism causes a problem, because RESTful services are stateless, which means the server does not hold any information about the client that are maintained over multiple responses/requests.
This is the reason, we cannot use session based authentication in our RESTful API.

Basic auth
Sends encoded login in every request header. Only secure when used with HTTPS.

We will be using token-based authentication, which generates a token with an expiration date on a valid login. The server stores this token in a database and sends the token back to the user.
The user then, has to send the token in every request, where the server compares it to the stored token in the database.
