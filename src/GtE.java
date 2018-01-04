import java.util.*;

class GtE extends FunExp
{
	ExpList expList;
	
	GtE(ExpList e)
	{
		expList = e;
	}
	
	void printParseTree(String indent)
	{
		IO.displayln(indent + indent.length() + " <exp>");
		
		String indent1 = indent+" ";
		String indent2 = indent1+" ";
		
		IO.displayln(indent1 + indent1.length() + " <fun exp>");
		IO.displayln(indent2 + indent2.length() + " >");
		expList.printParseTree(indent2);		
	}

	TypeVal typeEval(HashMap<String,TypeVal> paramMap)
	{
		if ( expList == null || expList.numArgs() != 2 )
		{
			IO.displayln("Error: > operator requires exactly two arguments");
			return null;
		}
		TypeVal exp1Type = expList.firstExp().typeEval(paramMap);
		TypeVal exp2Type = expList.secondExp().typeEval(paramMap);

		if ( (exp1Type == TypeVal.Int || exp1Type == TypeVal.Float) && (exp2Type == TypeVal.Int || exp2Type == TypeVal.Float) )
			return TypeVal.Boolean;
		else
		{
			IO.displayln("Type Error: one or both arguments of > have incompatible types");
			return TypeVal.Error;
		}
	}

	/*As per Project 3, the operands can either be float or int, leaving the possibility of having to compare
	 *an int to a float (or in the case of equal, boolean and boolean). By using the .floatVal() function we can cast all integers as a float and therefore
	 *we can treat all operands as floats and make a single comparison in a return statement.
	 */
	@Override
	Val Eval(HashMap<String, Val> funCallState) {
		return new BoolVal(expList.firstExp().Eval(funCallState).floatVal() > expList.secondExp().Eval(funCallState).floatVal());

	}
}