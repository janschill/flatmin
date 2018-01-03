package api.model;

public class Ausgabe
{
	private long id;
	private double betrag;
	private long idusers;
	private String datum;
	private String notiz;

	public Ausgabe()
	{
	}

	public Ausgabe(long id, double betrag, long idusers, String datum, String notiz)
	{
		this.id = id;
		this.betrag = betrag;
		this.idusers = idusers;
		this.datum = datum;
		this.notiz = notiz;
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public double getBetrag()
	{
		return betrag;
	}

	public void setBetrag(double betrag)
	{
		this.betrag = betrag;
	}

	public long getIdusers()
	{
		return idusers;
	}

	public void setIdusers(long idusers)
	{
		this.idusers = idusers;
	}

	public String getDatum()
	{
		return datum;
	}

	public void setDatum(String datum)
	{
		this.datum = datum;
	}

	public String getNotiz()
	{
		return notiz;
	}

	public void setNotiz(String notiz)
	{
		this.notiz = notiz;
	}

}
