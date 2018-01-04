
public class Header{

	Type type;
	FunName name;
	ParameterList parameters;
	
	Header(Type t, FunName n, ParameterList p)
	{
		type=t;
		name=n;
		parameters=p;
	}
	
	public void printParseTree(String indent) 
	{
		IO.displayln(indent + indent.length() + " <header> ");
		type.printParseTree(indent + " ");
		name.printParseTree(indent+ " ");
		if (parameters!=null) //Need to update this
		{
			parameters.printParseTree(indent+ " ");
		}
	}
	
	public void buildTypeMaps()
	{
		TypeChecker.funTypeMap.put(name.id,TypeVal.toTypeVal(type.getType()));
		if(parameters!=null)
		{
			TypeChecker.paramTypeMap.put(name.id, parameters.buildParamTypeMaps());
			TypeChecker.paramNumTypeMap.put(name.id, parameters.buildNumMaps(1));
		}
	
	}
}
