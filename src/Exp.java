import java.util.*;

abstract class Exp
{
	abstract void printParseTree(String indent);
	abstract TypeVal typeEval(HashMap<String,TypeVal> paramMap);
	abstract Val Eval(HashMap <String, Val> funCallState);
}