package api.resources;

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
import javax.ws.rs.core.MediaType;

import api.model.Einnahme;
import api.service.EinnahmeService;

@Path("/income")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EinnahmeResource
{
	private final static String TAG = "EinnahmeResource: ";

	EinnahmeService einnahmeService = new EinnahmeService();

	@GET
	public List<Einnahme> getEinnahmen() throws SQLException
	{
		return einnahmeService.getEinnahmen();
	}

	@GET
	@Path("/{id}")
	public Einnahme getEinnahme(@PathParam("id") long id) throws SQLException
	{
		System.out.println(TAG + id);
		return einnahmeService.getEinnahme(id);
	}

	@POST
	public Einnahme insertEinnahme(Einnahme einnahme) throws SQLException
	{
		return einnahmeService.insertEinnahme(einnahme);
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
}