package automatons;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class NFA extends Automaton
{	
	private HashMap<State, HashMap<Character, Set<State>>> transitionMap;
	private HashMap<State, Set<State>> mapE;
	
	public NFA(Set<State> Q, Set<Character> Σ, LinkedList<Pair<Pair<State, Character>, State>> transitions, State q, Set<State> F)
	{
		super(Q, Σ, q, F);
		
		//initializes transitionMap
		transitionMap = new HashMap<State, HashMap<Character, Set<State>>>();
		for(State s : Q)
		{
			transitionMap.put(s, new HashMap<Character, Set<State>>());
			transitionMap.get(s).put(EPSILON, new HashSet<State>());
			for(Character c : Σ)
				transitionMap.get(s).put(c, new HashSet<State>());
		}
		
		//fills out transitionMap with information specified by transitions list
		for(Pair<Pair<State, Character>, State> transition : transitions)
			map(transition);
		
		//initializes mapE to map the EPSILON transition at that state unionized with itself
		mapE = new HashMap<State, Set<State>>();
		for(State s : Q)
		{
			mapE.put(s, new HashSet<State>());
			mapE.get(s).add(s);
			mapE.get(s).addAll(δ(s, EPSILON));
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
		
		if(c != EPSILON)
			checkCharacter(c);
		
		transitionMap.get(from).get(c).add(to);
	}
	
	public Set<State> E(State s)
	{
		return mapE.get(s);
	}
	
	public Set<State> E(Set<State> states)
	{
		Set<State> unionE = new HashSet<State>();
		unionE.addAll(states);
		boolean changed;
		do
		{
			changed = false;
			for(State state : unionE)
				changed |= unionE.addAll(E(state));
		} while (changed == false);
		return unionE;
	}
	
	public Set<State> δ(State s, Character c)
	{
		return transitionMap.get(s).get(c);
	}
	
	public Set<State> δ(Set<State> states, Character c)
	{
		Set<State> unionδ = new HashSet<State>();
		for(State state : states)
			unionδ.addAll(δ(state, c));
		return unionδ;
	}
	
	private boolean accept(Set<State> current)
	{
		boolean accept = false;
		for(State state : current)
			accept |= F.contains(state);
		return accept;
	}
	
	@Override
	public boolean test(String input)
	{
		Set<State> current = E(q);
		for(int i = 0; i < input.length(); i++)
		{
			System.out.println(current);
			current = E(δ(current, input.charAt(i)));
		}
		System.out.println(current);
		return accept(current);
	}
}
