/**

This class is a top-down, recursive-descent parser for the following syntactic categories:

<fun def list> --> <fun def> | <fun def> <fun def list>
<fun def> --> <header> = <exp>
<header> --> <type> <fun name> <parameter list>
<type> --> "int" | "float" | "boolean"
<fun name> --> <id>
<parameter list> --> empty string | "(" <parameters> ")"
<parameters> --> <parameter> | <parameter> "," <parameters>
<parameter> --> <type> <id>
<exp> --> <id> | <int> | <float> | <floatE> | <boolLiteral> | "(" <fun exp> ")" | "if" <exp> "then" <exp> "else" <exp>
<boolLiteral> --> "false" | "true"
<fun exp> --> <fun op> <exp list>
<exp list> --> empty string | <exp> <exp list>
<fun op> --> <fun name> | <arith op> | <bool op> | <comp op>
<arith op> --> + | - | * | /
<bool op> --> "and" | "or" | "not"
<comp op> --> "<" | "<=" | ">" | ">=" | "=" 

The definitions of the tokens are given in the lexical analyzer class file "LexAnalyzer.java". 

The following variables/functions of "IO"/"LexAnalyzer" classes are used:

static void display(String s)
static void displayln(String s)
static void setIO(String inFile, String outFile)
static void closeIO()

static void setLex()
static String t // holds an extracted token
static State state // the current state of the finite automaton
static int getToken() // extracts the next token

An explicit parse tree is constructed in the form of nested class objects.
 
The main function will display the parse tree in linearly indented form.
Each syntactic category name labeling a node is displayed on a separate line, 
prefixed with the integer i representing the node's depth and indented by i blanks. 

**/


public abstract class Parser extends LexAnalyzer
{
	static boolean errorFound = false;

		
	public static FunDefList fDefList()
	
	// <fun def list> --> <fun def> | <fun def> <fun def list>
	
	{
		SingleFunDef def = funDef();
		
		if(state == State.Keyword_boolean | state==State.Keyword_float | state==State.Keyword_int)
		{
			FunDefList fDefList = fDefList();
			return new MultipleFunDef(def, fDefList);
		}
		else {
			return def;
		}
		
	}

	public static SingleFunDef funDef()
	{
		//<fun def> --> <header> = <exp>
		Header h = header();
		if(state == State.Eq)
		{
			getToken();
			Exp e= exp();
			return new SingleFunDef(h, e);
		}
		else
		{
			errorMsg(3);
			return new SingleFunDef(null,null);
		}
		
		
	}
	
	public static Header header()
	{
		//<header> --> <type> <fun name> <parameter list>
		Type t = type();
		getToken();
		FunName fn = fn();
		getToken();
		ParameterList pl = pl();
		return new Header(t, fn, pl);
		
		
	}
	
	public static Type type()
	{
		//<type> --> "int" | "float" | "boolean"
		if(state == State.Keyword_boolean | state==State.Keyword_float | state==State.Keyword_int)
		{
			String type = t; 
			return new Type(type);
		}
		
		else
		{
			errorMsg(6);
			return null;
		}
		
	}
	
	public static FunName fn()
	{
		//<fun name> --> <id>
		if(state==State.Id)
		{
			String id = t;
			return new FunName(id);
		}
		else
		{
			errorMsg(5);
			return null;
		}
	}
	
	public static ParameterList pl()
	{
		//<parameter list> --> empty string | "(" <parameters> ")"
		if(state==State.LParen)
		{
			getToken();
			Parameters params =  parameters();
			
			if(state==State.RParen)
			{
				getToken();
				return new ParameterList(params);
			}
			else
			{
				errorMsg(8);
				return null;
			}
			
		}
		
		else //The list is empty or there are no more elements to process
		{
			return null;
		}
		
	}
	
	public static Parameters parameters()
	{
		//<parameters> --> <parameter> | <parameter> "," <parameters>
		SingleParameter param = param();
		getToken();
		if(state==State.Comma)
		{
			getToken();
			Parameters p = parameters();
			return new MultipleParameter(param, p);
			
		}
		else
		{
			return param;
		}
	}
	
	public static SingleParameter param() {
		//<parameter> --> <type> <id>
		Type type = type();
		getToken();
		if(state==State.Id)
		{
			return new SingleParameter(type, t);
		}
		else {
			errorMsg(5);
			return null;
		}
		
	}
	public static Exp exp()
	{
		//<exp> --> <id> | <int> | <float> | <floatE> | <boolLiteral> | "(" <fun exp> ")" | "if" <exp> "then" <exp> "else" <exp>
		switch ( state )
		{
		case Id:
			ExpID id = new ExpID(t);
			getToken();
			return id;

		case Int:
			ExpInt exInt = new ExpInt(t);
			getToken();
			return exInt;

		case Float: 
			ExpFloat exFloat = new ExpFloat(t);
			getToken();
			return exFloat;
			
		case FloatE:
			ExpFloatE floatElem = new ExpFloatE(t);
			getToken();
			return floatElem;
		
		case Keyword_false:
		case Keyword_true:	
			ExpBoolLiteral boolElem = new ExpBoolLiteral(t);
			getToken();
			return boolElem;
			
		case Keyword_if:
			ExpConditional ec = cStatement();
			return ec;
		case LParen:
			getToken();
			FunExp fExp = fExp();

			if ( state == State.RParen )
				{
				getToken();
				return fExp;
				}
			else
			{
				errorMsg(8);
				return null;
			}

		default:
			errorMsg(2);
			return null;
		}
	}
	
	public static ExpConditional cStatement()
	{
		//"if" <exp> "then" <exp> "else" <exp>
		getToken();
		Exp e = exp();
		if(state==State.Keyword_then)
		{
			getToken();
			Exp e1 = exp();
			
			if(state==State.Keyword_else)
			{
				getToken();
				Exp e2 = exp();
				return new ExpConditional(e, e1, e2);
				
			}
			else
			{
				errorMsg(9);
				return null;
			}
		}
		else {
			errorMsg(10);
			return null;
		}
	
	}
	
	public static FunExp fExp()
	{
		//<fun exp> --> <fun op> <exp list>
		FunOp op = funOp();
		getToken();
		ExpList eList = exList();
		return new FunExp(op, eList);
	}
	
	public static FunOp funOp()
	{
		//<fun op> --> <fun name> | <arith op> | <bool op> | <comp op>
		if(state==State.Id || state==State.Add || state==State.Sub || state==State.Mul || state==State.Div || state==State.Keyword_or || state==State.Keyword_and || state==State.Keyword_not
				|| state==State.Ge || state==State.Gt || state== State.Le || state==State.Lt || state==State.Eq)
		{
			return new FunOp(t);
		}
		else
		{
			errorMsg(11);
			return null;
		}
		
	}
	
	public static ExpList exList()
	{
		//<exp list> --> empty string | <exp> <exp list>
		if(state == State.Id || state==State.Float || state==State.Int || state == State.FloatE || state==State.Keyword_true || state==State.Keyword_false|| state==State.LParen || state== State.Keyword_if)
		{
			Exp e = exp(); //e's are being extracted correctly but need te be printed the right way
			ExpList eList = exList();
			return new MultipleExp(e, eList);
		}
		else //List is empty or contains no other elements
		{
			return null;
		}
	}
	public static void errorMsg(int i)
	{
		errorFound = true;
				
		display(t + " : Syntax Error, unexpected symbol where");

		switch( i )
		{
		case 2: displayln(" id, int, float, keyword true, keyword false, keyword if or ( expected."); return;
		case 3:	displayln(" = expected."); return;
		case 5:	displayln(" id expected."); return;		
		case 6: displayln(" keyword: boolean, float, int expected."); return;
		case 7: displayln(" ( or empty string expected."); return;
		case 8: displayln(" ) expected."); return;
		case 9: displayln(" else expected."); return;
		case 10: displayln(" then expected."); return;
		case 11: displayln(" id, bool op, arith op or comp op expected."); return;
		}
	}

	public static void main(String argv[])
	{
		// argv[0]: input file containing an assignment list
		// argv[1]: output file displaying the parse tree
		
		setIO( argv[0], argv[1] );
		setLex();

		getToken();
		FunDefList funDefList = fDefList(); // build a parse tree
		if ( ! t.isEmpty() )
		{
			displayln(t + " : Syntax error, unexpected symbol found."); //Display a standard error message if the token is empty.
		}
		else if ( ! errorFound )
		{
			funDefList.printParseTree(""); // print the parse tree in linearly indented form in argv[1] file
		}
		closeIO();
	}
}
