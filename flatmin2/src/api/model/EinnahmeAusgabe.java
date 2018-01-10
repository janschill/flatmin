package api.model;

import java.util.ArrayList;
import java.util.List;

public class EinnahmeAusgabe
{
	private long id;
	private double betrag;
	private String token;
	private String notiz;
	private List<Username> debtor = new ArrayList<Username>();

	public EinnahmeAusgabe()
	{
	}

	public EinnahmeAusgabe(long id, double betrag, String token, String notiz, List<Username> debtor)
	{
		this.id = id;
		this.betrag = betrag;
		this.token = token;
		this.notiz = notiz;
		this.debtor = debtor;
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

	public String getToken()
	{
		return token;
	}

	public void setToken(String token)
	{
		this.token = token;
	}

	public List<Username> getDebtor()
	{
		return debtor;
	}

	public void setDebtor(List<Username> debtor)
	{
		this.debtor = debtor;
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
