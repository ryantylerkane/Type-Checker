import java.util.*;

class Bool extends Exp
{
	boolean boolElem;
	
	Bool(boolean b)
	{
		boolElem = b;
	}
	
	void printParseTree(String indent)
	{
		IO.displayln(indent + indent.length() + " <exp>");
		String indent1 = indent+" ";
		IO.displayln(indent1 + indent1.length() + " " + boolElem);
	}

	TypeVal typeEval(HashMap<String,TypeVal> paramMap)
	{
		return TypeVal.Boolean;
	}

	@Override
	Val Eval(HashMap<String, Val> funCallState) {
		return new BoolVal(boolElem);
	}
}