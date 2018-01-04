import java.util.*;

class Header
{
	String type;
	String funName;
	ParameterList parameterList; // parameterList is null if <parameter list> is empty.
	
	Header(String t, String f, ParameterList p)
	{
		type = t;
		funName = f;
		parameterList = p;
	}

	String returnType()
	{
		return type;
	}

	String funName()
	{
		return funName;
	}

	void printParseTree(String indent)
	{
		IO.displayln(indent + indent.length() + " <header>");
							  
		String indent1 = indent+" ";

		IO.displayln(indent1 + indent1.length() + " <type> " + type);
		IO.displayln(indent1 + indent1.length() + " <fun name> " + funName);
		if ( parameterList != null )
		{
			IO.displayln(indent1 + indent1.length() + " <parameter list>");
			parameterList.printParseTree(indent1+" ");
		}
	}

	void buildTypeMaps()
	{
		TypeChecker.funTypeMap.put(funName, TypeVal.toTypeVal(type));
		if ( parameterList != null )
		{
			HashMap <Integer, String> funParamTemp = new HashMap<Integer, String>(); //Declare a new HashMap to serve as the value of the funFormalParamAndBodyExp HashMap.
			HashMap<String,TypeVal> paramMap = new HashMap<String,TypeVal>();
			HashMap<Integer,TypeVal> paramNumMap = new HashMap<Integer,TypeVal>();

			parameterList.buildTypeMaps(1, paramMap, paramNumMap, funParamTemp);

			TypeChecker.funFormalParamAndBodyExp.put(funName, funParamTemp); //Add the function name along with the parameters and their position in the list.
			TypeChecker.paramTypeMap.put(funName, paramMap);
			TypeChecker.paramNumTypeMap.put(funName, paramNumMap);
		}			
	}
}
