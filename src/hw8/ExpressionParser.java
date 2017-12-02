package hw8;

import java.util.*;

/** An immutable class, stores variable to {true,false} mappings
 */
class Context {

	HashMap<String,Boolean> map = new HashMap<String,Boolean>();
	boolean lookup(String var) {
		return map.get(var).booleanValue();
	}
	public Context(HashMap<String,Boolean> m) {
		map = new HashMap<>(m);
	}
}

/**
 * Class ExpressionParser encapsulates a Boolean expression and a Context in 
 * which the expression is evaluated
 */
public class ExpressionParser {
	
	private BooleanExp expression;
	private Context context;
	
	/**
	 * This method is part of public interface. DO NOT REMOVE or change signature.
	 * 
	 * @param e is encapsulated Boolean expression
	 * @param hm is String->Boolean context map
	 */	
	public void init(BooleanExp e, HashMap<String,Boolean> hm) {
		expression = e;
		context = new Context(hm);
	}
	
		
	/** 
	  *  This method is part of public interface. DO NOT REMOVE or change signature.
	  * 
	  *  @param: str the string expression in preorder. E.g., AND OR x y z represents (x OR y) AND z
	  *  @return: returns the corresponding Boolean expression or null if str is invalid 
	  *  static "position" is used to avoid passing "position" as argument to the recursive calls
	  *  str must be a sequence of white-space separated strings, e.g., "OR x y", "AND OR x y OR z w"
	 */	
	public static int position;	
	public static BooleanExp parse(String str) {
		if (position >= str.length()) {
			return null;
		}
		String token;
		int i = str.indexOf(" ",position);

		// Read the next token from String str.
		if (i != -1)
			token = str.substring(position,i+1);
		else 
			token = str.substring(position);
		
		// Advance "position" beyond token
		position += token.length();

		// If token is AND, parse the left operand into "left", 
		// then parse the right operand into "right" and create 
		// an And Boolean Expression
		if (token.equals("AND ")) {
			BooleanExp left = parse(str);
			BooleanExp right = parse(str);
			if ((left == null) || (right == null)) { 
				return null;
			}
			return new AndExp(3,left,right,null,null);
		}
		if (token.equals("NOT ")) {
			BooleanExp left = parse(str);
		//	BooleanExp right = parse(str);
			//if ((left == null) && (right == null) || ((left!=null) && right!=null)) { 
			//	return null;
		//	}
			return new NotExp(2,left,null,null,null);
		}
		else if (token.equals("OR ")) {
			BooleanExp left = parse(str);
			BooleanExp right = parse(str);
			if ((left == null) || (right == null)) {
				return null;
			}
			return new OrExp(4,left,right,null,null);			
		}
		else if (token.equals("true") || token.equals("true ")) {
			return new Constant(0,null,null,Boolean.TRUE,null);
		}
		else if (token.equals("false") || token.equals("false ")) {
			return new Constant(0,null,null,Boolean.FALSE,null);
		}
		// Otherwise, the token is a variable (e.g., x, xyz). 
		// Get rid of the white space if necessary 
		else { 
		    if (token.charAt(token.length()-1)==' ') {
		    	token = token.substring(0,token.length()-1);
		    }
		    return new VarExp(1,null,null,null,token);
		}
			
	}
	/**
	 * This method is part of public interface. Do not remove or change signature.
	 * 
	 * @return boolean value of the enclosed expression,
	 *         evaluated in Context context.
	 */	
	public boolean evaluate() {
		return evaluate(context, expression);		
	}

	/**
	 * This method is part of public interface. Do not remove or change signature.
	 * 
	 * @param preorder  If True, returns expression in Preorder, False returns Inorder 
	 * @return string corresponding to Preorder of expression if preorder is True
	 * 	   string corresponding to Inorder of expression otherwise
	 */	
	public String print(boolean preorder) {
		return print(preorder,expression);
	}
	/**
	 * 
	 * @param preorder
	 * @param exp
	 * @return string corresponding to Preorder of exp if preorder is True
	 * 	   string corresponding to Inorder of exp otherwise
	 */	
	private String print(boolean preorder, BooleanExp exp) {
		StringBuffer result = new StringBuffer();
		if (preorder) { // print in Preorder
			/*switch (exp.getExpressionCode()) {
				case BooleanExp.AND: 
					result.append("AND ");
					result.append(print(preorder,exp.getLeft()));
					result.append(" ");
					result.append(print(preorder,exp.getRight()));
					break;
				case BooleanExp.OR:
					result.append("OR ");
					result.append(print(preorder,exp.getLeft()));
					result.append(" ");
					result.append(print(preorder,exp.getRight()));
					break;
				case BooleanExp.CONST:
					result.append(exp.getValue());
					break;
				case BooleanExp.VAR:
					result.append(exp.getVarString());
					break;
			}*/
			return exp.printPreorder();
		}			
		else { // print Inorder, getting rid of redundant parentheses
		/*	switch (exp.getExpressionCode()) {
				case BooleanExp.AND: 
					if (exp.getLeft().getExpressionCode() > BooleanExp.AND) {
						// if left operand is an expression of equal or lower precedence, parens are needed 
						result.append("("); result.append(print(preorder,exp.getLeft())); result.append(")");
					}
					else {
						// otherwise, i.e., of higher precedence, no parens needed
						result.append(print(preorder,exp.getLeft())); 
					}
					result.append(" AND ");
					if (exp.getRight().getExpressionCode() >= BooleanExp.AND) {
						result.append("("); result.append(print(preorder,exp.getRight())); result.append(")");
					}
					else {
						result.append(print(preorder,exp.getRight())); 
					}				
					break;
				case BooleanExp.OR:
					if (exp.getLeft().getExpressionCode() > BooleanExp.OR) {
						result.append("("); result.append(print(preorder,exp.getLeft())); result.append(")");
					}
					else {
						result.append(print(preorder,exp.getLeft())); 
					}
					result.append(" OR ");
					if (exp.getRight().getExpressionCode() >= BooleanExp.OR) {
						result.append("("); result.append(print(preorder,exp.getRight())); result.append(")");
					}
					else {
						result.append(print(preorder,exp.getRight())); 
					}				
					break;				
				case BooleanExp.CONST:
					result.append(exp.getValue());
					break;
				case BooleanExp.VAR:
					result.append(exp.getVarString());
					break;
				}
			}
			return result.toString();*/
			return exp.printInorder();
		}
		
	}
	/**
	 * 
	 * @param context
	 * @param exp
	 * @return value of BooleanExp exp in Context context
	 */
	private boolean evaluate(Context context, BooleanExp exp) {
		/*
		if (exp.getExpressionCode() == BooleanExp.AND) {
				return evaluate(context, exp.getLeft()) && 
						evaluate(context, exp.getRight()); 
		}
		else if (exp.getExpressionCode() == BooleanExp.OR) {
				return evaluate(context, exp.getLeft()) || 
						evaluate(context, exp.getRight());
		}
		else if (exp.getExpressionCode() == BooleanExp.CONST) {
				return exp.getValue();
		}
		else if (exp.getExpressionCode() == BooleanExp.VAR) {
				return context.lookup(exp.getVarString());
		}
		return false;*/
		return exp.evaluate(context);
	}

		
	public boolean visitorEvaluate() {
		// TODO: Implement your Visitor-based evaluation here.
		EvaluateVisitor ev=new EvaluateVisitor(context);
		expression.accept(ev);
		return ev.getEval();
	}
	
	public String visitorPrint() {
		PrintInorder pi=new PrintInorder();
		expression.accept(pi);
		return pi.getInorder();
	}
	
	
}
