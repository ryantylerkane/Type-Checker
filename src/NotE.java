import java.util.*;

class NotE extends FunExp
{
	ExpList expList;
	
	NotE(ExpList e)
	{
		expList = e;
	}
	
	void printParseTree(String indent)
	{
		IO.displayln(indent + indent.length() + " <exp>");
		
		String indent1 = indent+" ";
		String indent2 = indent1+" ";
		
		IO.displayln(indent1 + indent1.length() + " <fun exp>");
		IO.displayln(indent2 + indent2.length() + " not");
		expList.printParseTree(indent2);	
	}

	TypeVal typeEval(HashMap<String,TypeVal> paramMap)
	{
		if ( expList == null || expList.numArgs() != 1 )
		{
			IO.displayln("Error: not operator requires exactly one argument");
			return null;
		}
		TypeVal expListType = expList.typeEvalBool(paramMap);
		if ( expListType != TypeVal.Error )
			return TypeVal.Boolean;
		else
		{
			IO.displayln("Type Error: argument of not operator has incompatible types");
			return TypeVal.Error;
		}	
	}

	@Override
	Val Eval(HashMap<String, Val> funCallState) {
		if(expList.firstExp().Eval(funCallState).floatVal() == 0.0f)
		{
			return new BoolVal(true);
		}
		else if(expList.firstExp().Eval(funCallState).floatVal() == 1.0f)
		{
			return new BoolVal(false);
		}
		else
		{
			return null; //For error checking purposes.
		}
	}
}