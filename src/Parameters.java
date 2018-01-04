import java.util.HashMap;

public abstract class Parameters {

	abstract void printParseTree(String indent);
	abstract HashMap<String, TypeVal> buildParamTypeMaps();
	abstract HashMap<Integer, TypeVal> buildNumMaps(int numParams);
}
