
public class ExpID extends Exp {

	String id;

	
	ExpID(String i)
	{
		id = i; 
	}
	
	@Override
	public void printParseTree(String indent)
	{
		super.printParseTree(indent);
		IO.displayln((indent+= " ") + indent.length() + " " + id);
	}
	
	
	public TypeVal typeEval()
	{
		//System.out.println(id);
		if(TypeChecker.funTypeMap.containsKey(id) ) //Check if the ID is a function
		{
			return TypeChecker.funTypeMap.get(id); //Return the return type of that function
		}
			
		
		else if(TypeChecker.paramTypeMap.get(funName).containsKey(id))//Check if the ID is a parameter of the current function.
		{	
				//prevFunction = "";
				//numParams = 0;
				//currentPNum = -1;
				return TypeChecker.paramTypeMap.get(funName).get(id); //Get the type of the parameter and return it
		}
		else //There is no function or local parameter of this type
		{
			IO.displayln("Type Error: The ID isn't a function or a parameter of the current function.");
			return TypeVal.Error;
		}
	}
	
	public TypeVal compEval() //Never used, included to maintain structure for parse tree due to class hierarchy.
	{
		if(TypeChecker.funTypeMap.containsKey(id)) //Check if the ID is a function
		{
			return TypeChecker.funTypeMap.get(id); //Return the return type of that function
		}
			
		else if(TypeChecker.paramTypeMap.get(funName).containsKey(id))//Check if the ID is a parameter
		{
			return TypeChecker.paramTypeMap.get(funName).get(id); //Get the type of the parameter and return it
		}
		else //There is no function or local parameter of this type
		{
			IO.displayln("Type Error: The id: "+ id + "is not a function or a parameter of " + funName + ".");
			return TypeVal.Error;
		}
	}
}
