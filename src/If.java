import java.util.*;

class If extends Exp
{
	Exp exp1;
	Exp exp2;
	Exp exp3;
	
	If(Exp e1, Exp e2, Exp e3)
	{
		exp1 = e1;
		exp2 = e2;
		exp3 = e3;
	}
	
	void printParseTree(String indent)
	{
		IO.displayln(indent + indent.length() + " <exp>");
		
		String indent1 = indent+" ";
		String indent2 = indent1+" ";
		
		IO.displayln(indent1 + indent1.length() + " if");		
		exp1.printParseTree(indent2);
		IO.displayln(indent1 + indent1.length() + " then");
		exp2.printParseTree(indent2);
		IO.displayln(indent1 + indent1.length() + " else");
		exp3.printParseTree(indent2);
	}

	TypeVal typeEval(HashMap<String,TypeVal> paramMap)
	{
		TypeVal exp1Type = exp1.typeEval(paramMap);
		TypeVal exp2Type = exp2.typeEval(paramMap);
		TypeVal exp3Type = exp3.typeEval(paramMap);

		if ( exp1Type == TypeVal.Boolean )
		{
			if ( exp2Type == TypeVal.Int && exp3Type == TypeVal.Int )
				return TypeVal.Int;
			else if ( exp2Type == TypeVal.Float && exp3Type == TypeVal.Float )
				return TypeVal.Float;
			else if ( exp2Type == TypeVal.Boolean && exp3Type == TypeVal.Boolean )
				return TypeVal.Boolean;
			else
			{
				IO.displayln("Type Error: incompatible types found in conditional expression");
				return TypeVal.Error;
			}
		}
		else
		{
			IO.displayln("Type Error: non-boolean expression found after if");
			return TypeVal.Error;
		}		
	}

	@Override
	Val Eval(HashMap<String, Val> funCallState) {
		if(exp1.Eval(funCallState).floatVal() == 1.0f)
		{
			return exp2.Eval(funCallState);
		}
		else
		{
			return exp3.Eval(funCallState);
		}
	}
}