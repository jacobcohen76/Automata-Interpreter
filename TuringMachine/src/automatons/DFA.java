package automatons;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

public class DFA extends Automaton
{
	public HashMap<State, HashMap<Character, State>> transitionMap;
	
	public DFA(Set<State> Q, Set<Character> Σ, LinkedList<Pair<Pair<State, Character>, State>> transitions, State q, Set<State> F)
	{
		super(Q, Σ, q, F);
		
		//initializes transitionMap
		transitionMap = new HashMap<State, HashMap<Character, State>>();
		for(State state : Q)
			transitionMap.put(state, new HashMap<Character, State>());
		
		//fills out transitionMap with information specified by transitions list
		for(Pair<Pair<State, Character>, State> transition : transitions)
			map(transition);
		
		//error check, checks to make sure all edges are defined
		for(State state : Q)
		{
			Set<Character> edges = transitionMap.get(state).keySet();
			for(Character c : Σ)
				if(edges.contains(c) == false)
					throw new Error("Error, edge ('" + state + "', '" + c + "') is not defined.");
		}
	}
	
	private void map(Pair<Pair<State, Character>, State> transition)
	{
		Pair<State, Character> edge = transition.first;
		map(edge.first, edge.second, transition.second);
	}
	
	private void map(State from, Character c, State to)
	{
		checkState(from);
		checkState(to);
		checkCharacter(c);
		transitionMap.get(from).put(c, to);
	}
	
	public State δ(State s, Character c)
	{
		return transitionMap.get(s).get(c);
	}

	@Override
	public boolean test(String input)
	{
		State current = q;
		for(int i = 0; i < input.length(); i++)
			current = δ(current, input.charAt(i));
		return F.contains(current);
	}
}
