
public class ExpBoolLiteral extends Exp {
	
	String bool;
	
	ExpBoolLiteral(String result)
	{
		bool = result;
	}
	

	@Override
	public void printParseTree(String indent)
	{
		super.printParseTree(indent);
		IO.displayln((indent+= " ") + indent.length() +" " + bool);
	}

	public TypeVal typeEval()
	{
		return TypeVal.Boolean;
	}
	
	public TypeVal compEval() //Never used, included to maintain structure for parse tree due to class hierarchy.
	{
		return TypeVal.Boolean;
	}
}
