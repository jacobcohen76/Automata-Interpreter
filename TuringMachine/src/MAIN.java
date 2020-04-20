import java.util.LinkedList;

import automatons.Automaton;
import automatons.DFA;
import automatons.NFA;
import automatons.TM;
import parser.Parser;

public class MAIN
{
	public static void main(String args[])
	{		
//		testDFA1(21);
		
//		testTM1(20);
//		testTMaNbNcN(15);
//		testTM0i1i(2);
		testTM1(10);
		
//		Tape tape = new Tape('_');
//		tape.load("Hello World");
//		System.out.println(tape);		
//		tape.load("Goodbye!!!");
//		System.out.println(tape);	
	}
	
	public static void testAll(int k, Automaton automaton)
	{
		LinkedList<String> inputStrings = StringGenerator.generateAllUptoKLength(automaton.Σ, k);
		for(String str : inputStrings)
			if(automaton.test(str))
				System.out.println(str);
	}
	
	public static void testTMww(int k)
	{
		String input = 
				"Q := { q0, q1, q2, q3, q4, q5, q6, q7, q8, q9 };\r\n" + 
				"Σ := { 0, 1 };\r\n" + 
				"Γ := { 0, 1, X, Y, #, _ };\r\n" + 
				"δ :=	(q0, 0) = (q1, X, R),\r\n" + 
				"	(q0, 1) = (q1, Y, R),\r\n" + 
				"	(q0, X) = (q4, X, L),\r\n" + 
				"	(q0, Y) = (q4, Y, L),\r\n" + 
				"	(q1, 0) = (q1, 0, R),\r\n" + 
				"	(q1, 1) = (q1, 1, R),\r\n" + 
				"	(q1, X) = (q2, X, L),\r\n" + 
				"	(q1, Y) = (q2, Y, L),\r\n" + 
				"	(q1, _) = (q2, #, L),\r\n" + 
				"	(q2, 0) = (q3, X, L),\r\n" + 
				"	(q2, 1) = (q3, Y, L),\r\n" + 
				"	(q3, 0) = (q3, 0, L),\r\n" + 
				"	(q3, 1) = (q3, 1, L),\r\n" + 
				"	(q3, X) = (q0, X, R),\r\n" + 
				"	(q3, Y) = (q0, Y, R),\r\n" + 
				"	(q4, X) = (q4, 0, L),\r\n" + 
				"	(q4, Y) = (q4, 1, L),\r\n" + 
				"	(q4, _) = (q5, #, R),\r\n" + 
				"	(q5, 0) = (q6, X, R),\r\n" + 
				"	(q5, 1) = (q7, Y, R),\r\n" + 
				"	(q5, _) = (q9, _, L),\r\n" + 
				"	(q6, 0) = (q6, 0, R),\r\n" + 
				"	(q6, 1) = (q6, 1, R),\r\n" + 
				"	(q6, _) = (q6, _, R),\r\n" + 
				"	(q6, X) = (q8, _, L),\r\n" + 
				"	(q7, 0) = (q7, 0, R),\r\n" + 
				"	(q7, 1) = (q7, 1, R),\r\n" + 
				"	(q7, _) = (q7, _, R),\r\n" + 
				"	(q7, Y) = (q8, _, L),\r\n" + 
				"	(q8, 0) = (q8, 0, L),\r\n" + 
				"	(q8, 1) = (q8, 1, L),\r\n" + 
				"	(q8, _) = (q8, _, L),\r\n" + 
				"	(q8, X) = (q5, X, R),\r\n" + 
				"	(q8, Y) = (q5, Y, R);	\r\n" + 
				"q := q0;\r\n" + 
				"B := _;\r\n" + 
				"F := { q9 };\r\n";
		Parser parser = new Parser(input);
		TM tm = parser.parseTM();
		testAll(k, tm);
	}
	
	public static void testTM0i1i(int k)
	{
		String input = 
				"Q := { q0, q1, q2, q3, q4, q5, q6, q7 };\r\n" + 
				"Σ := { 0, 1 };\r\n" + 
				"Γ := { 0, 1, _ };\r\n" + 
				"δ :=	(q0, 0) = (q1, _, R),\r\n" + 
				"	(q0, 1) = (q6, _, R),\r\n" + 
				"	(q1, 0) = (q1, 0, R),\r\n" + 
				"	(q1, 1) = (q2, 1, R),\r\n" + 
				"	(q2, 1) = (q2, 1, R),\r\n" + 
				"	(q2, _) = (q3, _, L),\r\n" + 
				"	(q3, 1) = (q4, _, L),\r\n" + 
				"	(q4, 0) = (q4, 0, L),\r\n" + 
				"	(q4, 1) = (q4, 1, L),\r\n" + 
				"	(q4, _) = (q5, _, R),\r\n" + 
				"	(q5, 0) = (q1, _, R),\r\n" + 
				"	(q5, 1) = (q6, _, R),\r\n" + 
				"	(q5, _) = (q6, _, R),\r\n" + 
				"	(q6, 1) = (q6, _, R),\r\n" + 
				"	(q6, _) = (q7, _, R);\r\n" +
				"	\r\n" + 
				"q := q0;\r\n" + 
				"B := _;\r\n" + 
				"F := { q7 };\r\n";
		Parser parser = new Parser(input);
		TM tm = parser.parseTM();
		testAll(k, tm);
	}
	
	public static void testTM1(int k)
	{
		String input = 
				"Q := { q0, q1, q2, q3, q4, q5, q6 };\r\n" + 
				"Σ := { 0, 1 };\r\n" + 
				"Γ := { 0, 1, _ };\r\n" + 
				"δ :=	(q0, 0) = (q1, _, R),\r\n" + 
				"	(q0, 1) = (q5, _, R),\r\n" + 
				"	(q0, _) = (q3, _, R),\r\n" + 
				"	(q1, 0) = (q1, 0, R),\r\n" + 
				"	(q1, 1) = (q1, 1, R),\r\n" + 
				"	(q1, _) = (q2, _, L),\r\n" + 
				"	(q2, 0) = (q4, _, L),\r\n" + 
				"	(q2, _) = (q3, _, R),\r\n" + 
				"	(q4, 0) = (q4, 0, L),\r\n" + 
				"	(q4, 1) = (q4, 1, L),\r\n" + 
				"	(q4, _) = (q0, _, R),\r\n" + 
				"	(q5, 0) = (q5, 0, R),\r\n" + 
				"	(q5, 1) = (q5, 1, R),\r\n" + 
				"	(q5, _) = (q6, _, L),\r\n" + 
				"	(q6, _) = (q3, _, R),\r\n" + 
				"	(q6, 1) = (q4, _, L);\r\n" + 
				"q := q0;\r\n" + 
				"B := _;\r\n" + 
				"F := { q3 };";
		Parser parser = new Parser(input);
		TM tm = parser.parseTM();
		testAll(k, tm);
	}
	
	public static void testTMaNbN(int k)
	{
		String input =
				"Q := { q0, q1, q2, q3, q4 };\r\n" + 
				"Σ := { a, b };\r\n" + 
				"Γ := { a, b, A, B, _ };\r\n" + 
				"δ :=	(q0, a) = (q1, A, R),\r\n" + 
				"	(q0, B) = (q3, B, R),\r\n" + 
				"	(q0, _) = (q4, _, R),\r\n" +
				"	(q1, a) = (q1, a, R),\r\n" + 
				"	(q1, A) = (q1, A, R),\r\n" + 
				"	(q1, b) = (q2, B, L),\r\n" + 
				"	(q1, B) = (q1, B, R),\r\n" +
				"	(q2, a) = (q2, a, L),\r\n" + 
				"	(q2, b) = (q2, b, L),\r\n" + 
				"	(q2, B) = (q2, B, L),\r\n" + 
				"	(q2, A) = (q0, A, R),\r\n" + 
				"	(q3, B) = (q3, B, R),\r\n" + 
				"	(q3, _) = (q4, _, R);\r\n" + 
				"q := q0;\r\n" + 
				"B := _;\r\n" + 
				"F := { q4 };";
		Parser parser = new Parser(input);
		TM tm = parser.parseTM();
		testAll(k, tm);
	}
	
	public static void testTMaNbNcN(int k)
	{
		String input = 
				"Q := { q0, q1, q2, q3, q4, q5 };\r\n" + 
				"Σ := { a, b, c };\r\n" + 
				"Γ := { a, b, c, A, B, C, _ };\r\n" + 
				"δ :=	(q0, a) = (q1, A, R),\r\n" + 
				"	(q0, B) = (q4, B, R),\r\n" + 
				"	(q0, _) = (q5, _, R),\r\n" + 
				"	(q1, a) = (q1, a, R),\r\n" + 
				"	(q1, B) = (q1, B, R),\r\n" +
				"	(q1, b) = (q2, B, R),\r\n" + 
				"	(q2, b) = (q2, b, R),\r\n" + 
				"	(q2, C) = (q2, C, R),\r\n" +
				"	(q2, c) = (q3, C, L),\r\n" + 
				"	(q3, B) = (q3, B, L),\r\n" + 
				"	(q3, b) = (q3, b, L),\r\n" + 
				"	(q3, a) = (q3, a, L),\r\n" + 
				"	(q3, C) = (q3, C, L),\r\n" + 
				"	(q3, A) = (q0, A, R),\r\n" + 
				"	(q4, B) = (q4, B, R),\r\n" + 
				"	(q4, C) = (q4, C, R),\r\n" + 
				"	(q4, _) = (q5, _, R);\r\n" + 
				"q := q0;\r\n" + 
				"B := _;\r\n" + 
				"F := { q5 };";
		Parser parser = new Parser(input);
		TM tm = parser.parseTM();
		testAll(k, tm);		
	}
	
	public static void testDFA1(int k)
	{
		String input = ""
				+ "Q := { q0, q1 };\r\n"
				+ "Σ := { 0, 1 };\r\n"
				+ "δ := (q0, 0) = (q), (q0, 1) = q1, (q1, 0) = q1, (q1, 1) = q0;\r\n"
				+ "q := q0;\r\n"
				+ "F := { q1 };";
		Parser parser = new Parser(input);
		DFA dfa = parser.parseDFA();
		testAll(k, dfa);
	}
	
	public static void testNFA1()
	{
		String input = ""
				+ "Q := { q0, q1 };\r\n"
				+ "Σ := { 0, 1 };\r\n"
				+ "δ := (q0, 0) = q0, (q0, 1) = q1, (q0, EPSILON) = q1, (q1, 0) = q1, (q1, 1) = q0;\r\n"
				+ "q := q0;\r\n"
				+ "F := { q1 };";
		Parser parser = new Parser(input);
		NFA nfa = parser.parseNFA();
		String inputString = "01101";
		System.out.println(nfa.test(inputString));
	}
}