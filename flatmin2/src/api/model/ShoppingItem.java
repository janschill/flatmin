package api.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ShoppingItem
{
	private long idtodolist;
	private String item;

	public ShoppingItem()
	{
	}

	public ShoppingItem(Long idtodolist, String item)
	{
		this.idtodolist = idtodolist;
		this.item = item;
	}

	public long getIdtodolist()
	{
		return idtodolist;
	}

	public void setIdtodolist(long idtodolist)
	{
		this.idtodolist = idtodolist;
	}

	public String getItem()
	{
		return item;
	}

	public void setItem(String item)
	{
		this.item = item;
	}
}
