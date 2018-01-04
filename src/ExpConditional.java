
public class ExpConditional extends Exp {
	
	Exp expr1;
	Exp expr2;
	Exp expr3;

	ExpConditional(Exp e1, Exp e2, Exp e3)
	{
		expr1 = e1;
		expr2 = e2;
		expr3= e3;
	}
	
	@Override
	public void printParseTree(String indent)
	{
		super.printParseTree(indent);
		String condSpacing = indent+=" ";
		IO.displayln((condSpacing)+ indent.length() + " " + " if "); 
		expr1.printParseTree(indent+" "); 
		IO.displayln((condSpacing)+indent.length() + " "+ " then "); 
		expr2.printParseTree(indent + " ");
		IO.displayln((condSpacing)+indent.length() + " " + " else ");
		expr3.printParseTree(indent+ " ");
	}
	
	public TypeVal typeEval()
	{
		if(expr1.typeEval() == TypeVal.Boolean)
		{
			if(expr2.typeEval() == TypeVal.Int && expr3.typeEval() == TypeVal.Int)
			{
				return TypeVal.Int;
			}
			else if (expr2.typeEval() == TypeVal.Float && expr3.typeEval() == TypeVal.Float)
			{
				return TypeVal.Float;
			}
			else if(expr2.typeEval() == TypeVal.Boolean && expr3.typeEval() == TypeVal.Boolean)
			{
				return TypeVal.Boolean;
			}
			else 
			{
				IO.displayln("Type Error: Argument proceeding 'then' and argument proceeding 'else' are not of the same type.");
				return TypeVal.Error;
			}
		}
		else
		{
			IO.displayln("Type Error: Expression proceeding if is not of type Boolean.");
			return TypeVal.Error;
		}
	}
	
	public TypeVal compEval() //Never used, included to maintain structure for parse tree due to class hierarchy.
	{
		if(expr1.typeEval() == TypeVal.Boolean)
		{
			if(expr2.typeEval() == TypeVal.Int && expr3.typeEval() == TypeVal.Int)
			{
				return TypeVal.Int;
			}
			else if (expr2.typeEval() == TypeVal.Float && expr3.typeEval() == TypeVal.Float)
			{
				return TypeVal.Float;
			}
			else if(expr2.typeEval() == TypeVal.Boolean && expr3.typeEval() == TypeVal.Boolean)
			{
				return TypeVal.Boolean;
			}
			else 
			{
				IO.displayln("Type Error: Argument proceeding 'then' and argument proceeding 'else' are not of the same type.");
				return TypeVal.Error;
			}
		}
		else
		{
			IO.displayln("Type Error: Expression proceeding if is not of type Boolean.");
			return TypeVal.Error;
		}
	}
}
