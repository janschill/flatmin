package api.service;

import java.sql.SQLException;
import java.util.List;

import api.database.Database;
import api.model.Einnahme;

public class EinnahmeService
{
	private final static String TAG = "EinnahmeService: ";

	public List<Einnahme> getEinnahmen() throws SQLException
	{
		return Database.getEinnahmen();
	}

	public Einnahme getEinnahme(long id) throws SQLException
	{
		System.out.println(TAG + id);
		return Database.getEinnahme(id);
	}

	public Einnahme insertEinnahme(Einnahme einnahme) throws SQLException
	{
		return Database.insertEinnahme(einnahme);
	}

	public Einnahme updateEinnahme(Einnahme einnahme) throws SQLException
	{
		return Database.updateEinnahme(einnahme);
	}

	public void deleteEinnahme(long id) throws SQLException
	{
		Database.deleteEinnahme(id);
	}
}
