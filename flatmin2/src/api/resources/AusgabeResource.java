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
import api.model.EinnahmeAusgabe;
import api.service.AusgabeService;

@Path("/expenses")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AusgabeResource
{
	private final static String TAG = "AusgabeResource: ";

	AusgabeService ausgabeService = new AusgabeService();

	@GET
	public List<Ausgabe> getAusgaben(@Context UriInfo uriInfo) throws SQLException
	{
		List<Ausgabe> list = ausgabeService.getAusgaben();

		for (Ausgabe ausgabe : list)
		{
			ausgabe.addLink(getUriForSelf(uriInfo, ausgabe), "self");
			ausgabe.addLink(getUriForUser(uriInfo, ausgabe), "user");
		}

		return list;
	}

	@GET
	@Path("/{id}")
	public Ausgabe getAusgabe(@PathParam("id") long id) throws SQLException
	{
		System.out.println(TAG + id);
		return ausgabeService.getAusgabe(id);
	}

	@POST
	public Response insertAusgabe(EinnahmeAusgabe ausgabe, @Context UriInfo uriInfo) throws SQLException
	{
	
		System.out.println(TAG + ausgabe.getBetrag());
		System.out.println(TAG + ausgabe.getDebtor().size());
		EinnahmeAusgabe newAusgabe = ausgabeService.insertEinnahmeAusgabe(ausgabe);
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

	private String getUriForSelf(UriInfo uriInfo, Ausgabe ausgabe)
	{
		String uri = uriInfo.getBaseUriBuilder().path(AusgabeResource.class).path(Long.toString(ausgabe.getId()))
				.build().toString();
		return uri;
	}

	private String getUriForUser(UriInfo uriInfo, Ausgabe ausgabe)
	{
		String uri = uriInfo.getBaseUriBuilder().path(UsersResource.class).path(Long.toString(ausgabe.getIdusers()))
				.build().toString();
		return uri;
	}
}
