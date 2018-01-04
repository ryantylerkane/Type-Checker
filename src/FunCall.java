import java.util.*;

class FunCall extends FunExp
{
	String funName;
	ExpList expList; // expList is null if <exp list> is empty.
	int pNum = 1; //Variable needed to get variables for a user-defined function call during Eval.
	
	FunCall(String s, ExpList e)
	{
		funName = s;
		expList = e;
	}
	
	void printParseTree(String indent)
	{
		String indent1 = indent+" ";
		String indent2 = indent1+" ";
		
		IO.displayln(indent + indent.length() + " <exp>");
		IO.displayln(indent1 + indent1.length() + " <fun exp>");
		IO.displayln(indent2 + indent2.length() + " " + funName);
		if ( expList != null )
			expList.printParseTree(indent2);
	}

	TypeVal typeEval(HashMap<String,TypeVal> paramMap)
	{
		TypeVal returnType = TypeChecker.funTypeMap.get(funName);
		if ( returnType == null )
		{
			IO.displayln("Error: undefined function name "+funName+" found");
			return null;
		}
		int numOfArgs;
		if ( expList == null )
			numOfArgs = 0;
		else
			numOfArgs = expList.numArgs();
		int numOfParams;
		HashMap<Integer,TypeVal> paramNumTypeMap = TypeChecker.paramNumTypeMap.get(funName);
		if ( paramNumTypeMap == null )
			numOfParams = 0;
		else
			numOfParams = paramNumTypeMap.size();
		if ( numOfArgs != numOfParams )
		{
			IO.displayln("Error: # of arguments not equal to # of formal parameters in function call to "+funName);
			return null;
		}
		if ( expList != null )
		{
			TypeVal expListType = expList.typeEvalFunCall(paramMap, 1, paramNumTypeMap);
			if ( expListType == TypeVal.Correct )
				return returnType;
			else
				return TypeVal.Error;
		}
		else
			return returnType;
	}
	

	@Override
	Val Eval(HashMap<String, Val> funCallState) {
		if(TypeChecker.paramTypeMap.get(funName) != null) //If the function has no parameters, do not attempt to add any parameters to the state.
			return expList.EvalFunCall(funCallState, funName);
		return Parser.funMap.get(funName).bodyExp.Eval(funCallState); //Use the newly-built state (or parameterless function) to Evaluate the body functions with the necessary parameters.
	}
}