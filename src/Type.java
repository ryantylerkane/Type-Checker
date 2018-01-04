
public class Type {
	
	String t;
	
	Type(String type)
	{
		t = type;
	}
	
	public String getType()
	{
		return t;
	}
	
	public void printParseTree(String indent) 
	{
		IO.displayln(indent + indent.length() +  " <type> " + t);
		
}

}
