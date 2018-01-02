# flatmin

## Setup

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

## Rest Client
If you encounter this error message with your Java Rest Client:

> java.lang.NoClassDefFoundError: org/glassfish/json/jaxrs/JsonStructureBodyReader

you have to add these two libraries `javax.json.jar` and `jsonp-jaxrs.jar`, both located in your `glassfish4/glassfish/modules`-path,  to you `Build Path`.
