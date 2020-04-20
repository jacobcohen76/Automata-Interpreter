package automatons;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

public class TM extends Automaton
{
	private static final State REJECT = null;
	
	public Set<Character> Γ;
	public Character B;
	public Tape tape;
	
	private HashMap<State, HashMap<Character, TMTransition>> transitionMap;
	
	public TM(Set<State> Q, Set<Character> Σ, Set<Character> Γ, LinkedList<Pair<Pair<State, Character>, TMTransition>> transitions, State q, Character B, Set<State> F)
	{
		super(Q, Σ, q, F);
		this.Γ = Γ;
		this.B = B;
		this.tape = new Tape(B);
		
		//initializes transitionMap
		transitionMap = new HashMap<State, HashMap<Character, TMTransition>>();
		for(State s : Q)
			transitionMap.put(s, new HashMap<Character, TMTransition>());
		
		//fills out transitionMap with information specified by transitions list
		for(Pair<Pair<State, Character>, TMTransition> transition : transitions)
			map(transition);
	}
	
	private void checkTapeCharacter(Character c)
	{
		if(Γ.contains(c) == false)
			throw new Error("Unrecognized tape character at '" + c + "'.");
	}
	
	private void map(Pair<Pair<State, Character>, TMTransition> transition)
	{
		Pair<State, Character> edge = transition.first;
		TMTransition transitionInfo = transition.second;
		
		checkState(edge.first);
		checkTapeCharacter(edge.second);
		checkState(transitionInfo.to);
		checkTapeCharacter(transitionInfo.write);
		
		transitionMap.get(edge.first).put(edge.second, transitionInfo);
	}
	
	public State δ(State s, Character c)
	{
		TMTransition transitionInfo = transitionMap.get(s).get(c);
		
		if(	transitionInfo == null ||
			transitionInfo.shiftType == ShiftType.HALT ||
			transitionInfo.shiftType == null)
			return REJECT;
		
		tape.write(transitionInfo.write);
		if(transitionInfo.shiftType == ShiftType.LEFT)
			tape.moveLeft();
		else if(transitionInfo.shiftType == ShiftType.RIGHT)
			tape.moveRight();
		return transitionInfo.to;
	}

	@Override
	public boolean test(String input)
	{
		tape.load(input);
		State current = q;
		while(F.contains(current) == false && current != REJECT)
		{
//			System.out.println(current + "\n" + tape);
			current = δ(current, tape.read());
		}
		return F.contains(current);
	}

}
