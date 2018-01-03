package api.resources;

import java.net.URI;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import api.model.Ausgabe;
import api.model.Einnahme;
import api.service.AusgabeService;

@Path("/expenses")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AusgabeResource
{
	private final static String TAG = "AusgabeResource: ";

	AusgabeService ausgabeService = new AusgabeService();

	@GET
	public List<Ausgabe> getAusgaben() throws SQLException
	{
		return ausgabeService.getAusgaben();
	}

	@GET
	@Path("/{id}")
	public Ausgabe getAusgabe(@PathParam("id") long id) throws SQLException
	{
		System.out.println(TAG + id);
		return ausgabeService.getAusgabe(id);
	}

	@POST
	public Response insertAusgabe(Ausgabe ausgabe, @Context UriInfo uriInfo) throws SQLException
	{
		Ausgabe newAusgabe = ausgabeService.insertAusgabe(ausgabe);
		String id = String.valueOf(newAusgabe.getId());
		URI uri = uriInfo.getAbsolutePathBuilder().path(id).build();
		return Response.created(uri).entity(newAusgabe).build();
	}

	@PUT
	@Path("/{id}")
	public Ausgabe updateAusgabe(@PathParam("id") long id, Ausgabe ausgabe) throws SQLException
	{
		ausgabe.setId(id);
		return ausgabeService.updateAusgabe(ausgabe);
	}

	@DELETE
	@Path("/{id}")
	public void deleteAusgabe(@PathParam("id") long id) throws SQLException
	{
		ausgabeService.deleteAusgabe(id);
	}
}
