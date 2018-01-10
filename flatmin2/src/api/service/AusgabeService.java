package api.service;

import java.sql.SQLException;
import java.util.List;

import api.database.Database;
import api.model.Ausgabe;
import api.model.EinnahmeAusgabe;

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

	public EinnahmeAusgabe insertEinnahmeAusgabe(EinnahmeAusgabe ausgabe) throws SQLException
	{
		return Database.insertEinnahmeAusgabe(ausgabe);
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
