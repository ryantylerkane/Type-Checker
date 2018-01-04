
public class ExpFloatE extends Exp{

	float floatE;
	
	ExpFloatE(String f)
	{
		floatE=Float.parseFloat(f);
	}
	

	@Override
	public void printParseTree(String indent)
	{
		super.printParseTree(indent);
		IO.displayln((indent+= " ") + indent.length() +" " + floatE);
	}
	
	public TypeVal typeEval()
	{
		return TypeVal.Float;
	}
	
	public TypeVal compEval() //Never used, included to maintain structure for parse tree due to class hierarchy.
	{
		return TypeVal.Float;
	}
}
