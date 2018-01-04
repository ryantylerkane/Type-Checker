import java.util.HashMap;

public class SingleParameter extends Parameters {

	Type t; 
	String id;
	
	SingleParameter(Type type, String i)
	{
		id = i;
		t = type;
	}
	
	public void printParseTree(String indent)
	{
		IO.displayln(indent + indent.length() + " <parameter> " + t.getType() + " " + id);
	}
	
	public HashMap<String, TypeVal> buildParamTypeMaps()
	{
		HashMap<String, TypeVal> params = new HashMap<String, TypeVal>();
		params.put(id,TypeVal.toTypeVal(t.getType()));
		return params;
				
	}
	public HashMap<Integer, TypeVal> buildNumMaps(int numParams)
	{
		HashMap<Integer, TypeVal> pNums = new HashMap<Integer, TypeVal>();
		pNums.put(numParams, TypeVal.toTypeVal(t.getType()));
		return pNums;
	}
	
}
