import java.util.HashMap;

public class MultipleParameter extends Parameters {
	
	SingleParameter param;
	Parameters multipleParam;
	
	MultipleParameter(SingleParameter sp, Parameters mp)
	{
		multipleParam = mp;
		param =sp;
	}
	public void printParseTree(String indent)
	{
		param.printParseTree(indent);
		multipleParam.printParseTree(indent);
	}
	
	public HashMap<String, TypeVal> buildParamTypeMaps()
	{
		HashMap<String, TypeVal> parameters = new HashMap<String, TypeVal>();
		parameters.putAll(param.buildParamTypeMaps());
		parameters.putAll(multipleParam.buildParamTypeMaps());
		return parameters;
	}
	
	public HashMap<Integer, TypeVal> buildNumMaps(int numParams)
	{
		HashMap<Integer, TypeVal> pNums = new HashMap<Integer, TypeVal>();
		pNums.putAll(param.buildNumMaps(numParams));
		pNums.putAll(multipleParam.buildNumMaps(numParams+1));
		return pNums;
	}
	
}
