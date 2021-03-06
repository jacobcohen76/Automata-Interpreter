package parser;

public enum TokenType
{
	//special
	IDENTIFIER,
	NUMBER,
	END_OF_INPUT,
	
	//symbols
	TILDE,
	ACUTE,
	EXCLAMATION_POINT,
	AMPERSAT,
	OCTOTHORPE,
	GENERIC_CURRENCY,
	PERCENT,
	CARET,
	AMPERSAND,
	ASTERISK,
	HYPHEN,
	UNDERSCORE,
	PLUS,
	EQUAL,
	VERTICAL_BAR,
	BACK_SLASH,
	FORWARD_SLASH,
	COLON,
	SEMICOLON,
	QUOTE,
	APOSTROPHE,
	COMMA,
	PERIOD,
	QUESTION_MARK,
	
	//brackets
	LEFT_ROUND_BRACKET,
	RIGHT_ROUND_BRACKET,
	LEFT_CURLY_BRACKET,
	RIGHT_CURLY_BRACKET,
	LEFT_SQUARE_BRACKET,
	RIGHT_SQUARE_BRACKET,
	LEFT_ANGLE_BRACKET,
	RIGHT_ANGLE_BRACKET,
}
