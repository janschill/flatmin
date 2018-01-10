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

import api.model.ShoppingItem;
import api.service.ShoppingListService;

@Path("/shoppinglist")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ShoppingListResource
{
	ShoppingListService shoppingListService = new ShoppingListService();

	@GET
	public List<ShoppingItem> getShoppingList(@Context UriInfo uriInfo) throws SQLException
	{
		List<ShoppingItem> list = shoppingListService.getShoppingList();

		for (ShoppingItem shoppingItem : list)
		{
			shoppingItem.addLink(getUriForSelf(uriInfo, shoppingItem), "self");
		}

		return list;
	}

	@GET
	@Path("/{id}")
	public ShoppingItem getShoppingItem(@PathParam("id") long id, @Context UriInfo uriInfo) throws SQLException
	{
		ShoppingItem shoppingItem = shoppingListService.getShoppingItem(id);
		shoppingItem.addLink(getUriForSelf(uriInfo, shoppingItem), "self");
		return shoppingItem;
	}

	@POST
	public Response insertShoppingItem(ShoppingItem shoppingItem, @Context UriInfo uriInfo) throws SQLException
	{
		ShoppingItem newShoppingItem = shoppingListService.insertShoppingItem(shoppingItem);
		String id = String.valueOf(newShoppingItem.getIdtodolist());
		URI uri = uriInfo.getAbsolutePathBuilder().path(id).build();
		return Response.created(uri).entity(newShoppingItem).build();
	}

	@PUT
	@Path("/{id}")
	public ShoppingItem updateShoppingItem(@PathParam("id") long id, ShoppingItem shoppingItem) throws SQLException
	{
		shoppingItem.setIdtodolist(id);
		return shoppingListService.updateShoppingItem(shoppingItem);
	}

	@DELETE
	@Path("/{id}")
	public void deleteShoppingItem(@PathParam("id") long id) throws SQLException
	{
		shoppingListService.deleteShoppingItem(id);
	}

	private String getUriForSelf(UriInfo uriInfo, ShoppingItem shoppingItem)
	{
		String uri = uriInfo.getBaseUriBuilder().path(ShoppingListResource.class)
				.path(Long.toString(shoppingItem.getIdtodolist())).build().toString();
		return uri;
	}

}
