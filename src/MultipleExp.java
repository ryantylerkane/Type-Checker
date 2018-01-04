
public class MultipleExp extends ExpList {
	
	Exp e;
	ExpList list;
	
	static int paramCount;
	
	MultipleExp (Exp ex, ExpList l)
	{
		e = ex;
		list = l;
	}
	
	public void printParseTree(String indent)
	{
		e.printParseTree(indent);
		if(list!=null)
		{
		list.printParseTree(indent);
		}
	}
	
	public TypeVal typeEval()
	{
		paramCount++; //Increase the count in the event that we are iterating through a list of parameters for a function call.
	
		
		if(list != null && e.typeEval() == list.typeEval() || list==null)
		{
			return e.typeEval();
		}
		else
		{
			return TypeVal.Error;
		}
	}


	public TypeVal compEval()
	{
		
		if(list != null  && e.typeEval()==TypeVal.Float && list.typeEval()==TypeVal.Int)
		{
			return e.typeEval();
		}
		else if(list != null  && e.typeEval()==TypeVal.Int && list.typeEval()==TypeVal.Float)
		{
			return e.typeEval();
		}
		else if(list != null && e.typeEval()==TypeVal.Int && list.typeEval()==TypeVal.Int)
		{
			return TypeVal.Int;
		}
		else if(list != null && e.typeEval()==TypeVal.Float && list.typeEval()==TypeVal.Float)
		{
			return TypeVal.Float;
		}
		else
		{
			return TypeVal.Error;
		}
		
	}
}
