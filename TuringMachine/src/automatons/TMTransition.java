package automatons;

public class TMTransition
{
	public State to;
	public Character write;
	public ShiftType shiftType;
	
	public TMTransition(State to, Character write, ShiftType shiftType)
	{
		this.to = to;
		this.write = write;
		this.shiftType = shiftType;
	}
	
	public String toString()
	{
		return "(" + to + ", " + write + ", " + shiftType + ")";
	}
}
