package api.service;

import java.sql.SQLException;
import java.util.List;

import api.database.Database;
import api.model.Ausgabe;

public class AusgabeService
{
	private final static String TAG = "AusgabeService: ";

	public List<Ausgabe> getAusgaben() throws SQLException
	{
		return Database.getAusgaben();
	}

	public Ausgabe getAusgabe(long id) throws SQLException
	{
		System.out.println(TAG + id);
		return Database.getAusgabe(id);
	}

	public Ausgabe insertAusgabe(Ausgabe ausgabe) throws SQLException
	{
		return Database.insertAusgabe(ausgabe);
	}

	public Ausgabe updateAusgabe(Ausgabe ausgabe) throws SQLException
	{
		return Database.updateAusgabe(ausgabe);
	}

	public void deleteAusgabe(long id) throws SQLException
	{
		Database.deleteAusgabe(id);
	}
}
