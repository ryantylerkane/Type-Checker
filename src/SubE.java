import java.util.*;

class SubE extends FunExp
{
	ExpList expList;
	
	SubE(ExpList e)
	{
		expList = e;
	}
	
	void printParseTree(String indent)
	{
		IO.displayln(indent + indent.length() + " <exp>");
		
		String indent1 = indent+" ";
		String indent2 = indent1+" ";
		
		IO.displayln(indent1 + indent1.length() + " <fun exp>");
		IO.displayln(indent2 + indent2.length() + " -");
		expList.printParseTree(indent2);		
	}

	TypeVal typeEval(HashMap<String,TypeVal> paramMap)
	{
		if ( expList == null )
		{
			IO.displayln("Error: - operator requires at least one argument");
			return null;
		}
		TypeVal expListType = expList.typeEvalArith(paramMap);
		if ( expListType != TypeVal.Error )
			return expListType;
		else
		{
			IO.displayln("Type Error: some arguments of - operator have incompatible types");
			return TypeVal.Error;
		}	
	}

	@Override
	Val Eval(HashMap<String, Val> funCallState) {
		return expList.EvalSub(funCallState);
	}
}