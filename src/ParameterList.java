import java.util.HashMap;

public class ParameterList {

	Parameters param;

	ParameterList(Parameters p)
	{
		param = p;
	}
	
	public void printParseTree(String indent)
	{
		IO.displayln(indent + indent.length()+  " <parameter list> " );
		param.printParseTree(indent+ " ");
	}
	
	public HashMap<String, TypeVal> buildParamTypeMaps()
	{
		return param.buildParamTypeMaps();
	}
	
	public HashMap<Integer, TypeVal> buildNumMaps(int numParams)
	{
		return param.buildNumMaps(numParams);
	}
	
}
