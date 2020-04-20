package parser;

import java.util.Stack;

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
	
	public boolean equals(Object obj)
	{
		if(obj instanceof Pair<?, ?>)
		{
			if(first instanceof String && second instanceof Stack<?>)
				return first.equals(((Pair<?, ?>)obj).first) && second.equals(((Pair<?, ?>)obj).second);
		}
		return	this == obj ||
				obj instanceof Pair<?, ?> &&
				first.equals(((Pair<?, ?>)obj).first) && 
				second.equals(((Pair<?, ?>)obj).second);
	}
	
	public int hashCode()
	{
		return cantor(first.hashCode(), second.hashCode());
	}
	
	public String toString()
	{
		return "(" + first + ", " + second + ")";
	}
}
