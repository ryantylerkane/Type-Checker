
public class ExpFloat extends Exp{
	
	float f;
	
	ExpFloat(String temp)
	{
		f = Float.parseFloat(temp);
	}
	
	@Override
	public void printParseTree(String indent)
	{
		super.printParseTree(indent);
		IO.displayln((indent+= " ") + indent.length() +" " + f);
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
