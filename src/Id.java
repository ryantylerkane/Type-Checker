import java.util.*;

class Id extends Exp
{
	String id;
	
	Id(String s)
	{
		id = s;
	}
	
	void printParseTree(String indent)
	{
		IO.displayln(indent + indent.length() + " <exp>");
		String indent1 = indent+" ";
		IO.displayln(indent1 + indent1.length() + " " + id);
	}

	TypeVal typeEval(HashMap<String,TypeVal> paramMap)
	{
		TypeVal idType = paramMap.get(id);
		if ( idType != null )
			return idType;
		else
		{
			IO.displayln("Error: undefined variable "+id+" found");
			return null;			
		}
	}

	@Override
	Val Eval(HashMap<String, Val> funCallState) {
		if(funCallState.get(id) ==null)
		{
			System.err.println("The provided id " + "'" + id + "'" + " is not assigned to a value.");
			return null;
		}
		else
		{
			return funCallState.get(id).cloneVal();
		}
	}
	
	
}