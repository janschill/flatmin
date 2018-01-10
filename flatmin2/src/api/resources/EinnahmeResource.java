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

import api.model.Einnahme;
import api.service.EinnahmeService;

@Path("/income")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EinnahmeResource
{

	EinnahmeService einnahmeService = new EinnahmeService();

	@GET
	public List<Einnahme> getEinnahmen(@Context UriInfo uriInfo) throws SQLException
	{
		List<Einnahme> list = einnahmeService.getEinnahmen();

		for (Einnahme einnahme : list)
		{
			einnahme.addLink(getUriForSelf(uriInfo, einnahme), "self");
			einnahme.addLink(getUriForUser(uriInfo, einnahme), "user");
		}

		return list;
	}

	@GET
	@Path("/{id}")
	public Einnahme getEinnahme(@PathParam("id") long id, @Context UriInfo uriInfo) throws SQLException
	{
		Einnahme einnahme = einnahmeService.getEinnahme(id);
		einnahme.addLink(getUriForSelf(uriInfo, einnahme), "self");
		einnahme.addLink(getUriForUser(uriInfo, einnahme), "user");
		return einnahme;
	}

	@POST
	public Response insertEinnahme(Einnahme einnahme, @Context UriInfo uriInfo) throws SQLException
	{
		Einnahme newEinnahme = einnahmeService.insertEinnahme(einnahme);
		String id = String.valueOf(newEinnahme.getId());
		URI uri = uriInfo.getAbsolutePathBuilder().path(id).build();

		return Response.created(uri).entity(newEinnahme).build();
	}

	@PUT
	@Path("/{id}")
	public Einnahme updateEinnahme(@PathParam("id") long id, Einnahme einnahme) throws SQLException
	{
		einnahme.setId(id);
		return einnahmeService.updateEinnahme(einnahme);
	}

	@DELETE
	@Path("/{id}")
	public void deleteEinnahme(@PathParam("id") long id) throws SQLException
	{
		einnahmeService.deleteEinnahme(id);
	}

	private String getUriForSelf(UriInfo uriInfo, Einnahme einnahme)
	{
		String uri = uriInfo.getBaseUriBuilder().path(EinnahmeResource.class).path(Long.toString(einnahme.getId()))
				.build().toString();
		return uri;
	}

	private String getUriForUser(UriInfo uriInfo, Einnahme einnahme)
	{
		String uri = uriInfo.getBaseUriBuilder().path(UsersResource.class).path(Long.toString(einnahme.getIdusers()))
				.build().toString();
		return uri;
	}
}