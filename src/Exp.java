
abstract class Exp extends ExpList{
	
	public void printParseTree(String indent)
	{
		IO.displayln(indent + indent.length() + " <exp>" );
	}
	
	abstract TypeVal typeEval();
	
	static String funName; //Use a String to hold the current function being typeChecked in the event the ID needs to be checked.

}
