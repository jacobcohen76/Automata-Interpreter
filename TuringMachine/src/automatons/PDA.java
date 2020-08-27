package automatons;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.Stack;

public class PDA extends Automaton
{
	//stack alphabet
	private Set<Character> Γ;
	
	//Q x (Σ U Ɛ) x (Γ U Ɛ) -> P(Q x (Γ U Ɛ))
	private HashMap<State, HashMap<Character, HashMap<Character, HashSet<Pair<State, Character>>>>> transitionMap;
	
	public PDA(Set<State> Q, Set<Character> Σ, Set<Character> Γ, LinkedList<Pair<Triple<State, Character, Character>, Pair<State, Character>>> transitions, State q, Set<State> F)
	{
		super(Q, Σ, q, F);
		this.Γ = Γ;
		this.Γ.add(EPSILON);
		
		//initializes transition function map
		transitionMap = new HashMap<State, HashMap<Character, HashMap<Character, HashSet<Pair<State, Character>>>>>();
		
		//initializes each states transition map
		for(State s : Q)
		{
			transitionMap.put(s, new HashMap<Character, HashMap<Character, HashSet<Pair<State, Character>>>>());
			
			//initializes the transition from state s to each
			//character in the input alphabet
			for(Character c1 : Σ)
			{
				transitionMap.get(s).put(c1, new HashMap<Character, HashSet<Pair<State, Character>>>());
				
				//initializes the transition for each read character
				//in the input alphabet
				for(Character c2 : Γ)
					transitionMap.get(s).get(c1).put(c2, new HashSet<Pair<State, Character>>());
			}
			
			//same as above but for special character epsilon
			transitionMap.get(s).put(EPSILON, new HashMap<Character, HashSet<Pair<State, Character>>>());
			for(Character c : Γ)
				transitionMap.get(s).get(EPSILON).put(c, new HashSet<Pair<State, Character>>());
		}
		
		//fills transition map with appropriate information from the list of edges and destination
		for(Pair<Triple<State, Character, Character>, Pair<State, Character>> transition : transitions)
		{
			Triple<State, Character, Character> edge = transition.first;
			transitionMap.get(edge.first).get(edge.second).get(edge.third).add(transition.second);
		}
	}
	
	@SuppressWarnings("unchecked")
	public Set<Pair<State, Stack<Character>>> δ(Pair<State, Stack<Character>> state, Character c)
	{
		HashSet<Pair<State, Stack<Character>>> result = new HashSet<Pair<State, Stack<Character>>>();
		
		//gets the set of transitions this machine can take depending on the symbol read from the stack
		HashMap<Character, HashSet<Pair<State, Character>>> readMap = transitionMap.get(state.first).get(c);
		
		//current symbol on top of the stack
		Character onTop = state.second.isEmpty() ? EPSILON : state.second.peek();
		
		//iterable set of keys to iterate through the readMap
		Set<Character> keySet = readMap.keySet();
		
		//each key is a possible read character from the stack
		for(Character key : keySet)
		{
			//set of transitions of state to go to and character to write to stack from
			//the symbol read from the stack
			HashSet<Pair<State, Character>> transitions = readMap.get(key);
			for(Pair<State, Character> transition : transitions)
			{
				//if the key is matching the current symbol on top of the stack or the key is EPSILON,
				//the transition is valid so we add it to the state of transitions, otherwise this
				//transition is not valid, so the computation dies and we simulate this by not adding
				//it to our set of transitions
				if(onTop == key || key == EPSILON)
				{
					//constructs a new pair with modifications to stack, removing the read
					//symbol if it is not EPSILON, and also adding the written symbol if it
					//is not EPSILON
					State nextState = transition.first;
					Stack<Character> nextStack = (Stack<Character>)state.second.clone();
					if(key != EPSILON)
						nextStack.pop();
					if(transition.second != EPSILON)
						nextStack.push(transition.second);
					result.add(new Pair<State, Stack<Character>>(nextState, nextStack));
				}
			}
		}
		return result;
	}
	
	public Set<Pair<State, Stack<Character>>> δ(Set<Pair<State, Stack<Character>>> states, Character c)
	{
		Set<Pair<State, Stack<Character>>> result = new HashSet<Pair<State, Stack<Character>>>();
		for(Pair<State, Stack<Character>> state : states)
			result.addAll(δ(state, c));
		return result;
	}
	
	public Set<Pair<State, Stack<Character>>> E(Pair<State, Stack<Character>> state)
	{
		Set<Pair<State, Stack<Character>>> result = new HashSet<Pair<State, Stack<Character>>>();
		result.add(state);
		
		Set<Pair<State, Stack<Character>>> iteration = new HashSet<Pair<State, Stack<Character>>>(result);
		
		//adds all of the EPSILON transitions from the result until there is no change
		do
			for(Pair<State, Stack<Character>> part : result)
				iteration.addAll(δ(part, EPSILON));
		while(result.addAll(iteration));
		return result;
	}
	
	public Set<Pair<State, Stack<Character>>> E(Set<Pair<State, Stack<Character>>> states)
	{
		Set<Pair<State, Stack<Character>>> result = new HashSet<Pair<State, Stack<Character>>>(states);
		Set<Pair<State, Stack<Character>>> iteration = new HashSet<Pair<State, Stack<Character>>>(states);
		
		//adds all of the EPSILON transitions from the result until there is no change
		do
			for(Pair<State, Stack<Character>> state : result)
				iteration.addAll(E(state));
		while(result.addAll(iteration));
		
		return result;
	}
	
	public boolean accept(Set<Pair<State, Stack<Character>>> current)
	{
		boolean accept = false;
		for(Pair<State, Stack<Character>> state : current)
			accept |= F.contains(state.first);
		return accept;
	}

	@Override
	public boolean test(String input)
	{
		Set<Pair<State, Stack<Character>>> current = new HashSet<Pair<State, Stack<Character>>>();
		current.add(new Pair<State, Stack<Character>>(q, new Stack<Character>()));
		
//		System.out.println("δ(current, '" + ' ' + "')");
//		for(Pair<State, Stack<Character>> state : current)
//			System.out.println("\t" + state.first + " - " + state.second);
		current.addAll(E(current));
//		System.out.println("E(current)");
//		for(Pair<State, Stack<Character>> state : current)
//			System.out.println("\t" + state.first + " - " + state.second);
//		System.out.println();
		
		for(int i = 0; i < input.length(); i++)
		{
			current = δ(current, input.charAt(i));
//			System.out.println("δ(current, '" + input.charAt(i) + "')");
//			for(Pair<State, Stack<Character>> state : current)
//				System.out.println("\t" + state.first + " - " + state.second);
			current = E(current);
//			System.out.println("E(current)");
//			for(Pair<State, Stack<Character>> state : current)
//				System.out.println("\t" + state.first + " - " + state.second);
//			System.out.println();
		}
		
		return accept(current);
	}

}
