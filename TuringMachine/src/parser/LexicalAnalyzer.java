package parser;

import java.util.HashMap;
import java.util.LinkedList;

public class LexicalAnalyzer
{
	private static final HashMap<String, TokenType> typeMap;
	
	static
	{
		typeMap = new HashMap<String, TokenType>();
		
		typeMap.put("~", TokenType.TILDE);
		typeMap.put("`", TokenType.ACUTE);
		typeMap.put("!", TokenType.EXCLAMATION_POINT);
		typeMap.put("@", TokenType.AMPERSAT);
		typeMap.put("#", TokenType.OCTOTHORPE);
		typeMap.put("$", TokenType.GENERIC_CURRENCY);
		typeMap.put("%", TokenType.PERCENT);
		typeMap.put("^", TokenType.CARET);
		typeMap.put("&", TokenType.AMPERSAND);
		typeMap.put("*", TokenType.ASTERISK);
		typeMap.put("-", TokenType.HYPHEN);
		typeMap.put("_", TokenType.UNDERSCORE);
		typeMap.put("+", TokenType.PLUS);
		typeMap.put("=", TokenType.EQUAL);
		typeMap.put("|", TokenType.VERTICAL_BAR);
		typeMap.put("\\", TokenType.BACK_SLASH);
		typeMap.put("/", TokenType.FORWARD_SLASH);
		typeMap.put(":", TokenType.COLON);
		typeMap.put(";", TokenType.SEMICOLON);
		typeMap.put("\"", TokenType.QUOTE);
		typeMap.put("'", TokenType.APOSTROPHE);
		typeMap.put(",", TokenType.COMMA);
		typeMap.put(".", TokenType.PERIOD);
		typeMap.put("?", TokenType.QUESTION_MARK);
		
		typeMap.put("(", TokenType.LEFT_ROUND_BRACKET);
		typeMap.put(")", TokenType.RIGHT_ROUND_BRACKET);
		typeMap.put("{", TokenType.LEFT_CURLY_BRACKET);
		typeMap.put("}", TokenType.RIGHT_CURLY_BRACKET);
		typeMap.put("[", TokenType.LEFT_SQUARE_BRACKET);
		typeMap.put("]", TokenType.RIGHT_SQUARE_BRACKET);
		typeMap.put("<", TokenType.LEFT_ANGLE_BRACKET);
		typeMap.put(">", TokenType.RIGHT_ANGLE_BRACKET);
	}
	
	private LinkedList<Token> tokenStream;
	
	public LexicalAnalyzer(String input)
	{
		tokenStream = Tokenizer.tokenize(input, typeMap);
	}
	
	public Token getToken()
	{
		return tokenStream.pollFirst();
	}
	
	public void ungetToken(Token t)
	{
		tokenStream.addFirst(t);
	}
	
	public Token peek()
	{
		return tokenStream.peekFirst();
	}
	
	public Token expect(TokenType type)
	{
		Token t = getToken();
		if(t.type != type)
			throw new Error("Syntax Error, expected token of type '" + type + "', instead got token " + t);
		return t;
	}
	
	public String toString()
	{
		return tokenStream.toString();
	}
}
