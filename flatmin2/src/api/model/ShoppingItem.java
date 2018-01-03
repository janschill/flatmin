package api.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ShoppingItem
{
	private long idtodolist;
	private String item;
	private List<Link> links = new ArrayList<Link>();

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

	public List<Link> getLinks()
	{
		return links;
	}

	public void setLinks(List<Link> links)
	{
		this.links = links;
	}

	public void addLink(String url, String rel)
	{
		Link link = new Link();
		link.setLink(url);
		link.setRel(rel);
		links.add(link);
	}
}
