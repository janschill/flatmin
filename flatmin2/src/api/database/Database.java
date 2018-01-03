package api.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import api.model.ShoppingItem;
import api.model.Users;

public class Database
{
	private final static String TAG = "Database: ";
	// connect to database and serve methods for data handling

	public static ShoppingItem getShoppingItem(long id) throws SQLException
	{
		ShoppingItem shoppingItem = new ShoppingItem();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try
		{
			conn = DataSource.getConnection();
			ps = conn.prepareStatement("SELECT * FROM todolist WHERE idtodolist = ?");
			ps.setLong(1, id);
			rs = ps.executeQuery();

			while (rs.next())
			{
				shoppingItem.setIdtodolist(rs.getLong("idtodolist"));
				shoppingItem.setItem(rs.getString("item"));
			}

		} catch (Exception e)
		{

		} finally
		{
			if (conn != null)
				conn.close();
		}

		return shoppingItem;
	}

	public static List<ShoppingItem> getShoppingList() throws SQLException
	{
		List<ShoppingItem> list = new ArrayList<ShoppingItem>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try
		{
			conn = DataSource.getConnection();
			ps = conn.prepareStatement("SELECT * FROM todolist");
			rs = ps.executeQuery();

			while (rs.next())
			{
				long id = rs.getLong("idtodolist");
				String item = rs.getString("item");
				list.add(new ShoppingItem(id, item));
			}

		} catch (Exception e)
		{

		} finally
		{
			if (conn != null)
				conn.close();
		}

		return list;
	}

	public static ShoppingItem insertShoppingItem(ShoppingItem shoppingItem) throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;

		try
		{
			conn = DataSource.getConnection();
			String query = "INSERT INTO todolist (item) VALUES (?)";
			ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, shoppingItem.getItem());
			int row = ps.executeUpdate();

			if (row == 0)
				throw new SQLException("Unable to insert item");

			try (ResultSet generatedKeys = ps.getGeneratedKeys())
			{
				if (generatedKeys.next())
					shoppingItem.setIdtodolist(generatedKeys.getLong(1));
				else
					throw new SQLException("Unable to insert item, no ID obtained.");
			}

		} catch (Exception e)
		{
			System.out.println("Couldn't insert item");
		} finally
		{
			if (conn != null)
				conn.close();
		}

		return shoppingItem;
	}

	public static ShoppingItem updateShoppingItem(ShoppingItem shoppingItem) throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;

		try
		{
			conn = DataSource.getConnection();
			String query = "UPDATE todolist SET item = ? WHERE idtodolist = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, shoppingItem.getItem());
			ps.setLong(2, shoppingItem.getIdtodolist());
			ps.executeUpdate();

		} catch (Exception e)
		{
			System.out.println("Couldn't update item");
		} finally
		{
			if (conn != null)
				conn.close();
		}

		return shoppingItem;
	}

	public static void deleteShoppingItem(long id) throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;

		try
		{
			conn = DataSource.getConnection();
			String query = "DELETE FROM todolist WHERE idtodolist = ?";
			ps = conn.prepareStatement(query);
			ps.setLong(1, id);
			ps.executeUpdate();

		} catch (Exception e)
		{
			System.out.println("Couldn't delete item");
		} finally
		{
			if (conn != null)
				conn.close();
		}
	}

	public static Users getUser(long id) throws SQLException
	{
		Users user = new Users();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try
		{
			conn = DataSource.getConnection();
			ps = conn.prepareStatement("SELECT * FROM users WHERE idusers = ?");
			ps.setLong(1, id);
			rs = ps.executeQuery();

			while (rs.next())
			{
				user.setIdusers(rs.getLong("idusers"));
				user.setFirst(rs.getString("first"));
				user.setLast(rs.getString("last"));
				user.setEmail(rs.getString("email"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
			}

		} catch (Exception e)
		{
			System.out.println("Couldn't get user.");
		} finally
		{
			if (conn != null)
				conn.close();
		}

		return user;
	}

	public static List<Users> getUsers() throws SQLException
	{
		List<Users> list = new ArrayList<Users>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try
		{
			conn = DataSource.getConnection();
			ps = conn.prepareStatement("SELECT * FROM users");
			rs = ps.executeQuery();

			while (rs.next())
			{
				long id = rs.getLong("idusers");
				String first = rs.getString("first");

				String last = rs.getString("last");
				String email = rs.getString("email");
				String username = rs.getString("username");
				String password = rs.getString("password");

				list.add(new Users(id, first, last, email, username, password));
			}

		} catch (Exception e)
		{

		} finally
		{
			if (conn != null)
				conn.close();
		}

		return list;
	}

	public static Users insertUser(Users user) throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;

		try
		{
			conn = DataSource.getConnection();
			String query = "INSERT INTO users (first, last, email, username, password) VALUES (?, ?, ?, ?, ?)";
			ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, user.getFirst());
			ps.setString(2, user.getLast());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getUsername());
			ps.setString(5, user.getPassword());

			int row = ps.executeUpdate();

			if (row == 0)
				throw new SQLException("Unable to insert user");

			try (ResultSet generatedKeys = ps.getGeneratedKeys())
			{
				if (generatedKeys.next())
					user.setIdusers(generatedKeys.getLong(1));
				else
					throw new SQLException("Unable to insert user, no ID obtained.");
			}

		} catch (Exception e)
		{
			System.out.println("Couldn't insert user");
		} finally
		{
			if (conn != null)
				conn.close();
		}

		return user;
	}

	public static Users updateUser(Users user) throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;

		try
		{
			conn = DataSource.getConnection();
			String query = "UPDATE users SET first = ?, last = ?, email = ?, username = ?, password = ? WHERE idusers = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, user.getFirst());
			ps.setString(2, user.getLast());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getUsername());
			ps.setString(5, user.getPassword());
			ps.setLong(6, user.getIdusers());
			ps.executeUpdate();

		} catch (Exception e)
		{
			System.out.println("Couldn't update user");
		} finally
		{
			if (conn != null)
				conn.close();
		}

		return user;
	}

	public static void deleteUser(long id) throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;

		try
		{
			conn = DataSource.getConnection();
			String query = "DELETE FROM users WHERE idusers = ?";
			ps = conn.prepareStatement(query);
			ps.setLong(1, id);
			ps.executeUpdate();

		} catch (Exception e)
		{
			System.out.println("Couldn't delete user");
		} finally
		{
			if (conn != null)
				conn.close();
		}
	}
}
