package parser;

import java.util.HashMap;
import java.util.LinkedList;

public class Tokenizer
{	
	private HashMap<String, TokenType> typeMap;
	private LinkedList<Token> tokenStream;
	private String input;
	private int lineNumber;
	
	public Tokenizer(String input, HashMap<String, TokenType> typeMap)
	{
		this.input = input;
		this.typeMap = typeMap;
		tokenStream = new LinkedList<Token>();
		lineNumber = 0;
	}
	
	public static LinkedList<Token> tokenize(String input, HashMap<String, TokenType> typeMap)
	{
		Tokenizer tokenizer = new Tokenizer(input, typeMap);
		tokenizer.tokenize();
		return tokenizer.tokenStream;
	}
	
	public void tokenize()
	{
		do
		{
			tokenStream.addLast(getToken());
		} while (tokenStream.peekLast().type != TokenType.END_OF_INPUT);
	}
	
	private Token getToken()
	{
		skipWhiteSpace();
		String lexeme = nextLexeme();
		TokenType type = typeOf(lexeme);
		return new Token(type, lexeme, lineNumber);
	}
	
	private void skipWhiteSpace()
	{
		int i = 0;
		while(input.length() > i && Character.isWhitespace(input.charAt(i)))
		{
			if(input.charAt(i) == '\n')
				lineNumber++;
			i++;
		}
		input = input.substring(i);
	}
	
	private String nextLexeme()
	{
		int i = 0;
		while(input.length() > i && isDelimiter(input.charAt(i)) == false)
			i++;
		if(input.length() > i)
		{
			if(i <= 0)
				i = 1;
			String lexeme = input.substring(0, i);
			input = input.substring(i);
			return lexeme;
		}
		else
			return "";
	}
	
	private boolean isDelimiter(char c)
	{
		return Character.isWhitespace(c) || typeMap.containsKey(String.valueOf(c));
	}
	
	private TokenType typeOf(String lexeme)
	{
		TokenType type = typeMap.get(lexeme);
		if(type == null)
		{
			if(isNumber(lexeme))
				type = TokenType.NUMBER;
			else if(isIdentifier(lexeme))
				type = TokenType.IDENTIFIER;
			else
				type = TokenType.END_OF_INPUT;
		}
		return type;
	}
	
	private boolean isNumber(String s)
	{
		for(int i = 0; i < s.length(); i++)
			if(s.charAt(i) < '0' || '9' < s.charAt(i))
				return false;
		return s.length() > 0;
	}
	
	private boolean isIdentifier(String s)
	{
		for(int i = 0; i < s.length(); i++)
			if(isIDchar(s.charAt(i)) == false)
				return false;
		return s.length() > 0;
	}
	
	private boolean isIDchar(char id)
	{
		return Character.isDigit(id) || isDelimiter(id) == false;
	}
}
