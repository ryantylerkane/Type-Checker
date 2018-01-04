
public class MultipleFunDef extends FunDefList {
	
	SingleFunDef funDef;
	FunDefList list;
	
	MultipleFunDef(SingleFunDef fd, FunDefList l)
	{
		funDef=fd;
		list = l;
	}
	
	public void printParseTree(String indent)
	{
		funDef.printParseTree(indent);
		list.printParseTree(indent);
	}
	
	public void buildTypeMaps()
	{
		funDef.buildTypeMaps();
		list.buildTypeMaps();
	}
	
	public TypeVal typeEval()
	{
		if(list==null && funDef.typeEval() != TypeVal.Error)
		{
			return funDef.typeEval();
			//return TypeVal.Correct;
		}
		else if (list != null && funDef.typeEval() != TypeVal.Error)
		{
			
			return list.typeEval();
		}
		else
		{
			return TypeVal.Error;
		}
	}

}
