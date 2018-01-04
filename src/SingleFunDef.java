
public class SingleFunDef extends FunDefList {

	Header head;
	Exp expression;
	
	SingleFunDef(Header h, Exp e)
	{
		head=h;
		expression=e;
	}
	

	public void printParseTree(String indent)
	{			

		if(head != null && expression != null)
		{
		IO.displayln(indent + indent.length() + " <fun def>" );
		head.printParseTree(indent+" ");
		expression.printParseTree(indent+" ");
		}
	}
	
	public void buildTypeMaps()
	{
		head.buildTypeMaps();
	}
	

	
	public TypeVal typeEval()
	{
		Exp.funName=head.name.id;
		if(expression != null && TypeChecker.funTypeMap.get(head.name.id) == expression.typeEval()) //If the expression is not empty
		{
			return TypeVal.Correct;
		}
		else //Expression is null or a different type than the return type
		{
			IO.displayln("Type Error: The return type of the function '"+ Exp.funName + "' does not match the type of the body expressions.");
			return TypeVal.Error;
		}
		
	}
}
