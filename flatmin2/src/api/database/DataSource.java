package api.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataSource
{
	private static final String TAG = "DataSource:";
	private static final String ADDRESS = "jdbc:postgresql://localhost:5432/Flatmin";
	private static final String USER = "postgres";
	private static final String PASSWORD = "quarcks";
	private static final String DBDRIVER = "org.postgresql.Driver";
	private static Connection conn = null;

	public static Connection getConnection() throws Exception
	{
		System.out.println(TAG + "1");

		if (conn != null)
			conn = null;
		System.out.println(TAG + "2");
		try
		{
			System.out.println(TAG + "3");
			Class.forName(DBDRIVER);
			System.out.println(TAG + "4");
			conn = DriverManager.getConnection(ADDRESS, USER, PASSWORD);
			System.out.println(TAG + "5");
		} catch (Exception e)
		{
			System.out.println(TAG + "Connection failed");
		}
		System.out.println(TAG + "Connection established");
		return conn;
	}
}
