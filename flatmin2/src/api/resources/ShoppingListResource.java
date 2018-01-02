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

import api.model.ShoppingItem;
import api.service.ShoppingListService;

@Path("/shoppinglist")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ShoppingListResource
{
	ShoppingListService shoppingListService = new ShoppingListService();

	@GET
	public List<ShoppingItem> getShoppingList() throws SQLException
	{
		return shoppingListService.getShoppingList();
	}

	@GET
	@Path("/{id}")
	public ShoppingItem getShoppingItem(@PathParam("id") long id) throws SQLException
	{
		return shoppingListService.getShoppingItem(id);
	}

	@POST
	public ShoppingItem insertShoppingItem(ShoppingItem shoppingItem) throws SQLException
	{
		return shoppingListService.insertShoppingItem(shoppingItem);
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

}
