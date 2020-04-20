package automatons;

public class Quad<A, B, C, D> extends Triple<A, B, C>
{
	public D fourth;
	
	public Quad(A first, B second, C third, D fourth)
	{
		super(first, second, third);
		this.fourth = fourth;
	}
	
	public String toString()
	{
		return "(" + first + ", " + second + ", " + third + ", " + fourth + ")";
	}
}
