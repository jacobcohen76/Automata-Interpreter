package automatons;

public class State implements Comparable<State>
{
	private String id;
	
	public State(String id)
	{
		this.id = id;
	}
	
	public int hashCode()
	{
		return id.hashCode();
	}
	
	public boolean equals(Object obj)
	{
		return	this == obj ||
				obj instanceof State &&
				id.compareTo(((State) obj).id) == 0;
	}
	
	public int compareTo(State o)
	{
		return id.compareTo(o.id);
	}
	
	public String toString()
	{
		return id;
	}
}
