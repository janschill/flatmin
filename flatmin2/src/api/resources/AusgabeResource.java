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

import api.model.Ausgabe;
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
	public Ausgabe insertAusgabe(Ausgabe ausgabe) throws SQLException
	{
		return ausgabeService.insertAusgabe(ausgabe);
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
