package api.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Users
{
	private long idusers;
	private String first;
	private String last;
	private String email;
	private String username;
	private String password;

	public Users()
	{
	}

	public Users(long idusers, String first, String last, String email, String username, String password)
	{
		this.idusers = idusers;
		this.first = first;
		this.last = last;
		this.email = email;
		this.username = username;
		this.password = password;
	}

	public long getIdusers()
	{
		return idusers;
	}

	public void setIdusers(long idusers)
	{
		this.idusers = idusers;
	}

	public String getFirst()
	{
		return first;
	}

	public void setFirst(String first)
	{
		this.first = first;
	}

	public String getLast()
	{
		return last;
	}

	public void setLast(String last)
	{
		this.last = last;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}
}
