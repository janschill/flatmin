# flatmin

## Setup

### Application server
We will be using GlassFish.

### Server
We will deploy flatmin on a GlassFish instance running on a Raspberry Pi. This way we can develop a web view and display it on a monitor in our flat, 
where all the flat mates can see the different information flatmin provides.

To install GlassFish and PostgreSQL on a Raspberry Pi check out these two tutorials:
https://opensource.com/article/17/10/set-postgres-database-your-raspberry-pi
https://thehecklers.com/glassfish4-raspberrypi/


### Database
Our database of choice is PostgreSQL


#### Restoring Backups
To succesfully use the client with the server, the correct database needs to be online.
In pgAdmin, use the 'Restore'-Function to import the Flatmin-Database.

### Postman
Postman allows easy communication to the server. 
You can select the HTTP method you want to send to the server, you provide the URL, header/body params and Postman shows you the response the server sends. This way you can easily test different request methods and make sure you get the correct response.

## Server
Without opening any ports


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

### Android Client
We decived to test our REST-Server with an Android client, which turned out to work perfectly.

Because the server is used locally and has therefore a hard coded local IP, you need to change the IP accordingly in the client.
You can find the configuration file in `Globals.java`


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

We will be using **token-based authentication**, which generates a token with an expiration date on a valid login. The server stores this token in a database and sends the token back to the user.

The user then, has to send the token in every request, where the server compares it to the stored token in the database.
We decided to implement an easy and not safe to use token issuing, where the server just generates a random string and links it in the datase to the server.

**Token expiration is not yet implemented.**



