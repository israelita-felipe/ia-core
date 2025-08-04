grammar lexer nlp;

SEPARATOR:
	[,]
DOT:
	[\.]	
FOUR_NUMBERS:
	DECIMAL SEPARATOR DECIMAL SEPARATOR DECIMAL SEPARATOR DECIMAL
DECIMAL:
	[0-9]+DOT[0-9]+	;
OPEN_BRACKETS:
	[[]
CLOSE_BRACKETS:
	[]]	

grammar parser nlp;

program:
	statements EOF;

statements:
	node IMPLIES node;
	
node:
	OPEN_BRACKETS FOUR_NUMBERS CLOSE_BRACKETS;