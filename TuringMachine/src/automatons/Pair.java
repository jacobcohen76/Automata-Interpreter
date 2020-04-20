package automatons;

public class Pair<A, B>
{
	public A first;
	public B second;
	
	public Pair(A first, B second)
	{
		this.first = first;
		this.second = second;
	}
	
	private int cantor(int x, int y)
	{
		return (int) (0.5 * (x + y) * (x + y + 1) + y);
	}
	
	public int hashCode()
	{
		return cantor(first == null ? 0 : first.hashCode(), second == null ? 0 : second.hashCode());
	}
	
	public boolean equals(Object obj)
	{
		return	this == obj ||
				obj instanceof Pair<?, ?> &&
				first.equals(((Pair<?, ?>)obj).first) && 
				second.equals(((Pair<?, ?>)obj).second);
	}
	
	public String toString()
	{
		return "(" + first + ", " + second + ")";
	}
}
