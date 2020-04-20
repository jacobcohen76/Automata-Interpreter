package parser;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import automatons.DFA;
import automatons.NFA;
import automatons.PDA;
import automatons.Pair;
import automatons.ShiftType;
import automatons.State;
import automatons.TM;
import automatons.TMTransition;
import automatons.Triple;

public class Parser
{
	private LexicalAnalyzer lexer;
	
	public Parser(String input)
	{
		lexer = new LexicalAnalyzer(input);
	}
	
	public TM parseTM()
	{
		Set<State> Q = parseSet("Q");
		Set<Character> Σ = parseAlphabet("Σ");
		Set<Character> Γ = parseAlphabet("Γ");
		LinkedList<Pair<Pair<State, Character>, TMTransition>> transitions = parseSyntaxedTMTransitionsList();
		State q = parseState("q");
		Character B = parseCharacter("B");
		Set<State> F = parseSet("F");
		return new TM(Q, Σ, Γ, transitions, q, B, F);
	}
	
	public PDA parsePDA()
	{
		Set<State> Q = parseSet("Q");
		Set<Character> Σ = parseAlphabet("Σ");
		Set<Character> Γ = parseAlphabet("Γ");
		LinkedList<Pair<Triple<State, Character, Character>, Pair<State, Character>>> transitions = parseSyntaxedPDATransitionList();
		State q = parseState("q");
		Set<State> F = parseSet("F");
		return new PDA(Q, Σ, Γ, transitions, q, F);
	}

	public DFA parseDFA()
	{
		Set<State> Q = parseSet("Q");
		Set<Character> Σ = parseAlphabet("Σ");
		LinkedList<Pair<Pair<State, Character>, State>> transitions = parseSyntaxedTransitionsList();
		State q = parseState("q");
		Set<State> F = parseSet("F");
		return new DFA(Q, Σ, transitions, q, F);
	}
	
	public NFA parseNFA()
	{
		Set<State> Q = parseSet("Q");
		Set<Character> Σ = parseAlphabet("Σ");
		LinkedList<Pair<Pair<State, Character>, State>> transitions = parseSyntaxedTransitionsList();
		Set<State> F = parseSet("F");
		State q = parseState("q");
		return new NFA(Q, Σ, transitions, q, F);
	}
	
	private Set<State> parseSet(String variableName)
	{
		Token t;
		t = lexer.getToken();
		if(t.lexeme.compareTo(variableName) != 0)
			throw new Error("Expected '" + variableName + "', got '" + t.lexeme + "'.");
		lexer.expect(TokenType.COLON);
		lexer.expect(TokenType.EQUAL);
		Set<State> stateSet = parseStateSet();
		lexer.expect(TokenType.SEMICOLON);
		return stateSet;
	}
	
	private Set<Character> parseAlphabet(String variableName)
	{
		Token t;
		t = lexer.getToken();
		if(t.lexeme.compareTo(variableName) != 0)
			throw new Error("Expected '" + variableName + "', got '" + t.lexeme + "'.");
		lexer.expect(TokenType.COLON);
		lexer.expect(TokenType.EQUAL);
		Set<Character> alphabet = parseCharacterSet();
		lexer.expect(TokenType.SEMICOLON);
		return alphabet;
	}
	
	private LinkedList<Pair<Pair<State, Character>, State>> parseSyntaxedTransitionsList()
	{
		Token t;
		t = lexer.getToken();
		if(t.lexeme.compareTo("δ") != 0)
			throw new Error("Expected 'δ', got '" + t.lexeme + "'.");
		lexer.expect(TokenType.COLON);
		lexer.expect(TokenType.EQUAL);
		LinkedList<Pair<Pair<State, Character>, State>> transitionList = parseTransitionList();
		lexer.expect(TokenType.SEMICOLON);
		return transitionList;
	}
	
	private LinkedList<Pair<Pair<State, Character>, TMTransition>> parseSyntaxedTMTransitionsList()
	{
		Token t;
		t = lexer.getToken();
		if(t.lexeme.compareTo("δ") != 0)
			throw new Error("Expected 'δ', got '" + t.lexeme + "'.");
		lexer.expect(TokenType.COLON);
		lexer.expect(TokenType.EQUAL);
		LinkedList<Pair<Pair<State, Character>, TMTransition>> transitionList = parseTMTransitionList();
		lexer.expect(TokenType.SEMICOLON);
		return transitionList;
	}
	
	private State parseState(String variableName)
	{

		Token t;
		t = lexer.getToken();
		if(t.lexeme.compareTo(variableName) != 0)
			throw new Error("Expected '" + variableName + "', got '" + t.lexeme + "'.");
		lexer.expect(TokenType.COLON);
		lexer.expect(TokenType.EQUAL);
		State startState = parseState();
		lexer.expect(TokenType.SEMICOLON);
		return startState;
	}
	
	private LinkedList<Pair<Pair<State, Character>, State>> parseTransitionList()
	{
		LinkedList<Pair<Pair<State, Character>, State>> transitionList = new LinkedList<Pair<Pair<State, Character>, State>>();
		transitionList.add(parseTransition());
		if(lexer.peek().type == TokenType.COMMA)
		{
			lexer.expect(TokenType.COMMA);
			transitionList.addAll(parseTransitionList());
		}
		return transitionList;
	}
	
	private LinkedList<Pair<Pair<State, Character>, TMTransition>> parseTMTransitionList()
	{
		LinkedList<Pair<Pair<State, Character>, TMTransition>> transitionList = new LinkedList<Pair<Pair<State, Character>, TMTransition>>();
		transitionList.add(parseCompleteTMTransition());
		if(lexer.peek().type == TokenType.COMMA)
		{
			lexer.expect(TokenType.COMMA);
			transitionList.addAll(parseTMTransitionList());
		}
		return transitionList;
	}
	
	private Pair<Pair<State, Character>, TMTransition> parseCompleteTMTransition()
	{
		Pair<State, Character> edge = parseEdge();
		lexer.expect(TokenType.EQUAL);
		TMTransition tmtransition = parseTMTransition();
		return new Pair<Pair<State, Character>, TMTransition>(edge, tmtransition);
	}
	
	private TMTransition parseTMTransition()
	{
		lexer.expect(TokenType.LEFT_ROUND_BRACKET);
		State state = parseState();
		lexer.expect(TokenType.COMMA);
		Character write = parseCharacter();
		lexer.expect(TokenType.COMMA);
		ShiftType shiftType = parseShiftType();
		lexer.expect(TokenType.RIGHT_ROUND_BRACKET);
		return new TMTransition(state, write, shiftType);
	}
	
	private ShiftType parseShiftType()
	{
		Character c = parseCharacter();
		if(c == 'L')
			return ShiftType.LEFT;
		else if(c == 'R')
			return ShiftType.RIGHT;
		else if (c == 'H')
			return ShiftType.HALT;
		else
			throw new Error("Error, shift type must be either 'L', 'R', or 'H', instead got '" + c + "'");
	}
	
	private Pair<Pair<State, Character>, State> parseTransition()
	{
		Pair<State, Character> first = parseEdge();
		lexer.expect(TokenType.EQUAL);
		State second = parseState();
		return new Pair<Pair<State, Character>, State>(first, second);
	}
	
	private LinkedList<Pair<Triple<State, Character, Character>, Pair<State, Character>>> parseSyntaxedPDATransitionList()
	{
		Token t;
		t = lexer.getToken();
		if(t.lexeme.compareTo("δ") != 0)
			throw new Error("Expected 'δ', got '" + t.lexeme + "'.");
		lexer.expect(TokenType.COLON);
		lexer.expect(TokenType.EQUAL);
		LinkedList<Pair<Triple<State, Character, Character>, Pair<State, Character>>> transitionList = parsePDATransitionList();
		lexer.expect(TokenType.SEMICOLON);
		return transitionList;
	}
	
	private LinkedList<Pair<Triple<State, Character, Character>, Pair<State, Character>>> parsePDATransitionList()
	{
		LinkedList<Pair<Triple<State, Character, Character>, Pair<State, Character>>> transitionList = new LinkedList<Pair<Triple<State, Character, Character>, Pair<State, Character>>>();
		transitionList.add(parsePDATransition());
		if(lexer.peek().type == TokenType.COMMA)
		{
			lexer.expect(TokenType.COMMA);
			transitionList.addAll(parsePDATransitionList());
		}
		return transitionList;
	}
	
	private Pair<Triple<State, Character, Character>, Pair<State, Character>> parsePDATransition()
	{
//		System.out.println(lexer);
		Triple<State, Character, Character> edge = parsePDAEdge();
		lexer.expect(TokenType.EQUAL);
		Pair<State, Character> destination = parseEdge();
		return new Pair<Triple<State, Character, Character>, Pair<State, Character>>(edge, destination);
	}
	
	private Triple<State, Character, Character> parsePDAEdge()
	{
		lexer.expect(TokenType.LEFT_ROUND_BRACKET);
		State first = parseState();
		lexer.expect(TokenType.COMMA);
		Character second = parseCharacter();
		lexer.expect(TokenType.COMMA);
		Character third = parseCharacter();
		lexer.expect(TokenType.RIGHT_ROUND_BRACKET);
		return new Triple<State, Character, Character>(first, second, third);
	}

	private Pair<State, Character> parseEdge()
	{
		lexer.expect(TokenType.LEFT_ROUND_BRACKET);
		State first = parseState();
		lexer.expect(TokenType.COMMA);
		Character second = parseCharacter();
		lexer.expect(TokenType.RIGHT_ROUND_BRACKET);
		return new Pair<State, Character>(first, second);
	}
	
	private Set<State> parseStateSet()
	{
		lexer.expect(TokenType.LEFT_CURLY_BRACKET);
		LinkedList<State> stateList = parseStateList();
		lexer.expect(TokenType.RIGHT_CURLY_BRACKET);
		return new HashSet<State>(stateList);
	}
	
	private LinkedList<State> parseStateList()
	{
		LinkedList<State> stringList = new LinkedList<State>();
		stringList.add(parseState());
		if(lexer.peek().type == TokenType.COMMA)
		{
			lexer.expect(TokenType.COMMA);
			stringList.addAll(parseStateList());
		}
		return stringList;
	}
	
	private State parseState()
	{
		Token t = lexer.expect(TokenType.IDENTIFIER);
		return new State(t.lexeme);
	}
	
	private Set<Character> parseCharacterSet()
	{
		lexer.expect(TokenType.LEFT_CURLY_BRACKET);
		LinkedList<Character> characterList = parseCharacterList();
		lexer.expect(TokenType.RIGHT_CURLY_BRACKET);
		return new HashSet<Character>(characterList);
	}
	
	private LinkedList<Character> parseCharacterList()
	{
		LinkedList<Character> characterList = new LinkedList<Character>();
		characterList.add(parseCharacter());
		if(lexer.peek().type == TokenType.COMMA)
		{
			lexer.expect(TokenType.COMMA);
			characterList.addAll(parseCharacterList());
		}
		return characterList;
	}
	
	private Character parseCharacter(String variableName)
	{
		Token t;
		t = lexer.getToken();
		if(t.lexeme.compareTo(variableName) != 0)
			throw new Error("Expected '" + variableName + "', got '" + t.lexeme + "'.");
		lexer.expect(TokenType.COLON);
		lexer.expect(TokenType.EQUAL);
		Character character = parseCharacter();
		lexer.expect(TokenType.SEMICOLON);
		return character;
	}
	
	private Character parseCharacter()
	{
		Token t = lexer.getToken();
		if(t.lexeme.compareTo("EPSILON") == 0)
			return null;
		else if(t.lexeme.length() != 1)
			throw new Error("Error, expected token with lexeme comprised of a single character,"
					+ " got a token with lexeme of '" + t.lexeme + "'.");
		return t.lexeme.charAt(0);
	}
}
