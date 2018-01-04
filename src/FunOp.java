
public class FunOp{

	String op;
	
	FunOp(String s)
	{
		op = s;
	}
	public void printParseTree(String indent)
	{
		IO.displayln(indent+indent.length() + " " + op);
	}
	
	public TypeVal typeEval(ExpList l)
	{
		if(op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/"))
		{	
			if(l.typeEval()==TypeVal.Int)
			{
				return TypeVal.Int;
			}
			else if(l.typeEval()==TypeVal.Float)
			{
				return TypeVal.Float;
			}
			else
			{
				IO.displayln("Type Error: The expressions proceeding "+ op + " are of incompatible types."); 
				return TypeVal.Error;
			}
		}
		else if (op.equals("and") || op.equals("or"))
		{
			if(l.typeEval() == TypeVal.Boolean)
			{
				return TypeVal.Boolean;
			}
			else
			{
				IO.displayln("Type Error: Expressions corresponding to '" + op + "' are not of type Boolean.");
				return TypeVal.Error;
			}
		}
		else if (op.equals("not"))
		{
			if(l.typeEval() == TypeVal.Boolean)
			{
				return TypeVal.Boolean;
			}
			else
			{
				IO.displayln("Type Error: The expression proceeding '" + op + "' is not of type Boolean.");
				return TypeVal.Error;
			}
		}
		else if(op.equals("<") || op.equals("<=") || op.equals(">") || op.equals(">="))
		{
			if(l.compEval().isNumberType())
			{
				return TypeVal.Boolean;
			}
			else 
			{
				System.out.println(l.typeEval().toString());
				IO.displayln("Type Error: The two expressions corresponding to " + op + " are both not numeric types.");
				return TypeVal.Error;
			}
		}
		
		else if( op.equals("="))
		{
			if(l.compEval().isNumberType() || l.typeEval() == TypeVal.Boolean)
			{
				return TypeVal.Boolean;
			}
			else
			{
				IO.displayln("Type Error: The expressions on both sides of " + op + " are either non-numeric or are both not Booleans.");
				return TypeVal.Error;
			}
		}
		else if (TypeChecker.funTypeMap.get(op) != null) //Another function is being called.
		{
			MultipleExp.paramCount=0;
			if(l!= null && l.typeEval() == TypeChecker.funTypeMap.get(op) || l==null)
			{
				return TypeChecker.funTypeMap.get(op);
			}
			else
			{
				IO.displayln("Type Error: Parameter #"+MultipleExp.paramCount + " is of an incompatible type.");
				MultipleExp.paramCount=0;
				return TypeVal.Error;
			}
		}
		else if (TypeChecker.paramTypeMap.get(Exp.funName).get(op) != null) //The parameter is a parameter of the current function.
		{
			return TypeChecker.paramTypeMap.get(Exp.funName).get(op);
		}
		else
		{
			IO.displayln("Type Error: The provided operation is not a an arithemtic operator, a boolean operator, a comparison operator or a user-defined function.");
			return TypeVal.Error;
		}
	}
}
