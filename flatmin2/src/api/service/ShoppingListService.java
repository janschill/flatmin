package api.service;

import java.sql.SQLException;
import java.util.List;

import api.database.Database;
import api.model.ShoppingItem;

public class ShoppingListService
{
	
	public ShoppingItem getShoppingItem(long id) throws SQLException
	{
		return Database.getShoppingItem(id);
	}

	public List<ShoppingItem> getShoppingList() throws SQLException
	{
		return Database.getShoppingList();
	}

	public ShoppingItem insertShoppingItem(ShoppingItem shoppingItem) throws SQLException
	{
		return Database.insertShoppingItem(shoppingItem);
	}

	public void deleteShoppingItem(long id) throws SQLException
	{
		Database.deleteShoppingItem(id);
	}

	public ShoppingItem updateShoppingItem(ShoppingItem shoppingItem) throws SQLException
	{
		return Database.updateShoppingItem(shoppingItem);
	}

}
