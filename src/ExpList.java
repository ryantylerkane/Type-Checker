import java.util.*;

class ExpList
{
	Exp exp;
	ExpList expList; // expList is null at the end of the list.
	int pNum = 1;
	
	ExpList(Exp e, ExpList el)
	{
		exp = e;
		expList = el;
	}

	Exp firstExp()
	{
		return exp;
	}
	
	Exp secondExp()
	{
		return expList.firstExp();
	}

	ExpList tailExpList()
	{
		return expList;
	}

	int numArgs()
	{
		if ( expList == null )
			return 1;
		else
			return 1 + expList.numArgs();
	}
		
	void printParseTree(String indent)
	{
		exp.printParseTree(indent);
		if ( expList != null )
			expList.printParseTree(indent);	
	}

	TypeVal typeEvalArith(HashMap<String,TypeVal> paramMap)
	{
		TypeVal expType = exp.typeEval(paramMap);

		if ( expList == null )
			return expType;
		else
		{
			TypeVal expListType = expList.typeEvalArith(paramMap);
			if ( expType == TypeVal.Int && expListType == TypeVal.Int )
				return TypeVal.Int;
                	else if ( expType == TypeVal.Float && expListType == TypeVal.Float )
				return TypeVal.Float;
			else
				return TypeVal.Error;
		}
	}

	TypeVal typeEvalBool(HashMap<String,TypeVal> paramMap)
	{
		TypeVal expType = exp.typeEval(paramMap);

		if ( expList == null )
			return expType;
		else
		{
			TypeVal expListType = expList.typeEvalBool(paramMap);
			if ( expType == TypeVal.Boolean && expListType == TypeVal.Boolean )
				return TypeVal.Boolean;
			else
				return TypeVal.Error;
		}
	}
	
	TypeVal typeEvalFunCall(HashMap<String,TypeVal> paramMap,
			        int i, HashMap<Integer,TypeVal> paramNumTypeMap)
	{
		TypeVal expType = exp.typeEval(paramMap);
		TypeVal paramType = paramNumTypeMap.get(i);

		if ( expList == null )
		{
			if ( expType == paramType )
				return TypeVal.Correct;
			else
			{
				IO.displayln("Type Error: incompatible type for parameter # "+i);
				return TypeVal.Error;
			}
		}
		else
		{
			TypeVal expListType = expList.typeEvalFunCall(paramMap, i+1, paramNumTypeMap);

			if ( expType == paramType && expListType == TypeVal.Correct )
				return TypeVal.Correct;
			else if ( expType != paramType )
			{
				IO.displayln("Type Error: incompatible type for parameter # "+i);
				return TypeVal.Error;
			}
			else
				return TypeVal.Error;
		}
	}
	
	//FunCall
	
	Val EvalFunCall(HashMap<String, Val> funCallState, String funName)
	{	
		if(TypeChecker.paramTypeMap.get(funName) != null) //If the function has no parameters, do not attempt to add any parameters to the state.
				buildState(funCallState, pNum, funName);
			
			return Parser.funMap.get(funName).bodyExp.Eval(funCallState); //Use the newly-built state (or parameterless function) to Evaluate the body functions with the necessary parameters.
			 
	}
	
	private void buildState(HashMap<String, Val> funCallState, int pNum, String funName)
	{
		String arg = TypeChecker.funFormalParamAndBodyExp.get(funName).get(pNum);
		Val expVal = exp.Eval(funCallState);
		funCallState.put(arg, expVal); //Add the parameter and its value to the state.
		
		if(expList != null)
		{
			expList.buildState(funCallState, pNum+1, funName); //Add the next parameter to the state.
		}
	}
	
	
	Val Eval(HashMap<String, Val> funCallState)
	{
		if (expList == null && exp != null) //There is only a single expression in the list.
		{
			return exp.Eval(funCallState).cloneVal(); //Return the type of the single expression.
			
		}
		
		else if(expList == null && exp == null) //Both the list and the expression are null so there are no evaluations to perform.
		{
			return null;
		}
		
		else //Both of expList and exp are non-empty or exp==null and expList != null
		{ 
			
			if(expList.Eval(funCallState) == exp.Eval(funCallState))
			{
				return expList.Eval(funCallState); 
			}
			else
			{
				return null; //exp==null
			}
			
		}
	}
	
	/*Boolean Operators*/
	Val EvalOr(HashMap<String, Val> funCallState)
	{
		if(expList == null) //There is only one exp remaining.
		{
			return new BoolVal(exp.Eval(funCallState).floatVal()==1.0f);
		}
		else
		{
			return new BoolVal((exp.Eval(funCallState).floatVal() == 1.0f) || (expList.EvalOr(funCallState).floatVal() == 1.0f)); 
		}
	}
	
	Val EvalAnd(HashMap<String, Val> funCallState)
	{
		if(expList == null) //There is only one exp remaining.
		{
			return new BoolVal(exp.Eval(funCallState).floatVal()==1.0f);
		}
		else
		{
			return new BoolVal((exp.Eval(funCallState).floatVal() == 1.0f) && (expList.EvalAnd(funCallState).floatVal() == 1.0f)); 
		}
	}
	
	
	/*Arithmetic Operators*/
	
	Val EvalAdd(HashMap<String, Val> funCallState)
	{
		if(expList == null) //There is only one exp to add.
		{
			return exp.Eval(funCallState);
		}
		else
		{
			if(exp.Eval(funCallState) instanceof IntVal)
			{
				return new IntVal((int) exp.Eval(funCallState).floatVal() + (int) expList.EvalAdd(funCallState).floatVal());
			}
			return new FloatVal(exp.Eval(funCallState).floatVal() + expList.EvalAdd(funCallState).floatVal());
		}
	}
	
Val EvalMul(HashMap<String, Val> funCallState) {
		
		if(expList ==null) //There is only one exp to multiply.
		{
			return new FloatVal(exp.Eval(funCallState).floatVal());
		}
		else {
			
			if(exp.Eval(funCallState) instanceof IntVal)
			{
				return new IntVal((int) exp.Eval(funCallState).floatVal() * (int) expList.EvalAdd(funCallState).floatVal());
			}
			return new FloatVal(exp.Eval(funCallState).floatVal() * expList.EvalMul(funCallState).floatVal());
		}	
	}
	
Val EvalDiv(HashMap<String, Val> funCallState) {
	
	if(expList ==null) //There is only one exp to divide.
	{
		return new FloatVal(exp.Eval(funCallState).floatVal());
	}
	else {
		if(!expList.EvalDiv(funCallState).isZero())
		{
			if(exp.Eval(funCallState) instanceof IntVal)
			{
				return new IntVal((int) exp.Eval(funCallState).floatVal() / (int) expList.EvalAdd(funCallState).floatVal());
			}
			return new FloatVal(exp.Eval(funCallState).floatVal() / expList.EvalDiv(funCallState).floatVal());
		}
		
		else
		{
			System.err.println("Error: Divsion by zero. Terminating program!");
			//Throw an error so that a null value isn't passed to the DivE class.
			throw new ArithmeticException();
		}
	}	
}
	
	Val EvalSub(HashMap<String, Val> funCallState) {
		
		if(expList ==null) //There is only one exp to subtract.
		{
			return new FloatVal(exp.Eval(funCallState).floatVal());
		}
		else {
			
			if(exp.Eval(funCallState) instanceof IntVal)
			{
				return new IntVal((int) exp.Eval(funCallState).floatVal() - (int) expList.EvalAdd(funCallState).floatVal());
			}
			return new FloatVal(exp.Eval(funCallState).floatVal() - expList.EvalSub(funCallState).floatVal());
		}	
	}
	
}