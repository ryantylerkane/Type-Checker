
public class FunName {

	String id;
	
	FunName (String i)
	{
		id = i;
	}
	
	public String getName()
	{
		return id;
	}
	
	public void printParseTree(String indent) 
	{
		IO.displayln(indent+indent.length() + " <fun name> " + id);
	}
}
