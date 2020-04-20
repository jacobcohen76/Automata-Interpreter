package automatons;

import java.util.Set;

public abstract class Automaton
{
	protected static final Character EPSILON = null;
	
	public Set<State> Q;
	public Set<Character> Σ;
	public State q;
	public Set<State> F;
	
	public Automaton(Set<State> Q, Set<Character> Σ, State q, Set<State> F)
	{
		this.Q = Q;
		this.Σ = Σ;
		this.q = q;
		this.F = F;
		errorCheck();
	}
	
	private void errorCheck()
	{
		for(State s : F)
			if(Q.contains(s) == false)
				throw new Error("Error, F must be a subset of Q, state '" + s + "' is not an element of Q.");
		if(Q.contains(q) == false)
			throw new Error("Error, q must be a subset of Q, state '" + q + "' is not an element of Q.");
	}
	
	public void checkState(State state)
	{
		if(Q.contains(state) == false)
			throw new Error("Unrecognized State at '" + state + "'.");
	}
	
	public void checkCharacter(Character c)
	{
		if(Σ.contains(c) == false)
			throw new Error("Unrecognized transition character at '" + c + "'.");
	}
	
	public abstract boolean test(String input);
}
