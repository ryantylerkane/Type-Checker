
public class FunExp extends Exp{

	FunOp operation;
	ExpList list;
	
	FunExp(FunOp fo, ExpList e)
	{
		operation = fo;
		list = e;
	}
	
	public void printParseTree(String indent)
	{
		super.printParseTree(indent);
		Parser.displayln((indent+=" ") + indent.length() + " <fun exp> ");
		operation.printParseTree(indent + " ");
		if(list != null)
		{
			list.printParseTree(indent + " ");
		}
	}
	
	public TypeVal typeEval()
	{
		if(operation.typeEval(list)!= TypeVal.Error)
		{
			//return TypeVal.Correct;
			return operation.typeEval(list);
		}
		else
		{
			return TypeVal.Error;
		}
	}
	public TypeVal compEval() //Never used, included to maintain structure for parse tree due to class hierarchy.
	{
		if(operation.typeEval(list) != TypeVal.Error)
		{
			return operation.typeEval(list);
		}
		else
		{
			return TypeVal.Error;
		}
		
	}
}
