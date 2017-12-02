package hw8;

import java.util.Stack;

/**
 * 
 * BooleanExp is an immutable class. It represents boolean constants, boolean variables,
 * Or boolean expressions and And boolean expressions.
 *
 */

public abstract class BooleanExp{

	final static int CONST = 0;
	final static int VAR = 1;
	final static int OR = 4;
	final static int AND = 3;
	final static int NOT = 2;
	
	// Rep invariant:
	// exprCode is AND || OR || CONST || VAR
	// if exprCode == AND || exprCode == OR then left != null and right != null and value = null and varString = null
	// else if exprCode == CONST then left == null and right == null and value != null and varString == null
	// else if exprCode == VAR then left == null and right == null and value == null and varString != null
	private BooleanExp left;
	private BooleanExp right;
	private int exprCode;
	private Boolean value;
	private String varString;
	
	public BooleanExp getLeft() {
		return left;
	}
	public BooleanExp getRight() {
		return right;
	}
	public int getExpressionCode() { 
		return exprCode;
	}
	public boolean getValue() {
		return value.booleanValue();
	}
	public String getVarString() {
		return varString;
	}
	public BooleanExp() {
		this.left = null;
		this.right = null;
		this.value = null;
		this.varString = null;
		this.exprCode = 0;
	}
	public BooleanExp(int exprCode, BooleanExp left, BooleanExp right, Boolean value, String varString) {
		this.left = left;
		this.right = right;
		this.value = value;
		this.varString = varString;
		this.exprCode = exprCode; 
	}
	abstract boolean evaluate(Context context);
	abstract String printPreorder();
	abstract String printInorder();
	abstract void accept(Visitor visitor);
	/*public void accept(Visitor visitor) {
		visitor.visit(this);
	}*/
	/**
	 * @return: String corresponding to Preorder of this BooleanExp
	 * E.g., if BooleanExp is CONST with value true, result is "true"
	 * If BooleanExp is AND with left VAR "x" and right VAR "x", 
	 * result is "AND x y"  
	 */
	public String toString() {
		/*StringBuffer result = new StringBuffer();	
		switch (getExpressionCode()) {
			case BooleanExp.AND: 
				result.append("AND ");
				result.append(getLeft().toString());
				result.append(" ");
				result.append(getRight().toString());
				break;
			case BooleanExp.OR:
				result.append("OR ");
				result.append(getLeft().toString());
				result.append(" ");
				result.append(getRight().toString());
				break;
			case BooleanExp.CONST:
				result.append(getValue());
				break;
			case BooleanExp.VAR:
				result.append(getVarString());
				break;
		}
		return result.toString();
	}*/
	return this.printPreorder();
	}
}
class Constant extends BooleanExp {
	public Constant(int exprCode, BooleanExp left, BooleanExp right, Boolean value, String varString) {
		super(exprCode, left, right, value, varString);
	}
	@Override
	boolean evaluate(Context context) {
		return getValue();
	}
	@Override
	String printPreorder() {
		String value=""+this.getValue();
		System.out.println(value);
		return value;
	}
	@Override
	String printInorder() {
		String value=""+this.getValue();
		return value;
	}
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
class VarExp extends BooleanExp {
	public VarExp(int exprCode, BooleanExp left, BooleanExp right, Boolean value, String varString) {
		super(exprCode, left, right, value, varString);
	}
	@Override
	boolean evaluate(Context context) {
		return context.lookup(this.getVarString());
	}
	@Override
	String printPreorder() {
		if(this.getVarString()==null) {
			return "";
		}
		String value=this.getVarString();
		System.out.println(value);
		return value;
	}
	@Override
	String printInorder() {
		String value=this.getVarString();
		return value;
	}
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
abstract class CompositeExp extends BooleanExp {
	public CompositeExp(int exprCode, BooleanExp left, BooleanExp right, Boolean value, String varString) {
		super(exprCode, left, right, value, varString);
	}
}
class AndExp extends CompositeExp{
	public AndExp(int exprCode, BooleanExp left, BooleanExp right, Boolean value, String varString) {
		super(exprCode, left, right, value, varString);
	}
	@Override
	boolean evaluate(Context context) {
		return getLeft().evaluate(context) && getRight().evaluate(context);
	}
	@Override
	String printPreorder() {
		String result="AND "+ this.getLeft().printPreorder()+ " " + this.getRight().printPreorder();
		return result;
	}
	@Override
	String printInorder() {
		String result="";
		if(this.getLeft().getExpressionCode() > this.getExpressionCode()) {
			result+="(" + this.getLeft().printInorder() + ")";
		}
		else {
			result+=this.getLeft().printInorder();
		}
		result+=(" AND ");
		if(this.getRight().getExpressionCode() >= this.getExpressionCode()) {
			result+="(" + this.getRight().printInorder() + ")";

		}
		else {
			result+=this.getRight().printPreorder();
		}
		return result;
	}
	@Override
	public void accept(Visitor v) {
		this.getLeft().accept(v);
		this.getRight().accept(v);
		v.visit(this);
		}
}
class OrExp extends CompositeExp{
	public OrExp(int exprCode, BooleanExp left, BooleanExp right, Boolean value, String varString) {
		super(exprCode, left, right, value, varString);
	}
	@Override
	boolean evaluate(Context context) {
		return getLeft().evaluate(context) || getRight().evaluate(context);
	}
	@Override
	String printPreorder() {
		String result="OR "+ this.getLeft().printPreorder()+ " " + this.getRight().printPreorder();
		return result;
	}
	@Override
	String printInorder() {
		String result="";
		if(this.getLeft().getExpressionCode() > this.getExpressionCode()) {
			result+="(" + this.getLeft().printInorder() + ")";
		}
		else {
			result+=this.getLeft().printInorder();
		}
		result+=(" OR ");
		if(this.getRight().getExpressionCode() >= this.getExpressionCode()) {
			result+="(" + this.getRight().printInorder() + ")";

		}
		else {
			result+=this.getRight().printPreorder();
		}
		return result;
	}
	@Override
	public void accept(Visitor v) {
		this.getLeft().accept(v);
		this.getRight().accept(v);
		v.visit(this);
		}

}
class NotExp extends CompositeExp{
	/**
	 * Abstract function: represents a 
	 */
	public NotExp(int exprCode, BooleanExp left, BooleanExp right, Boolean value, String varString) {
		super(exprCode, left, right, value, varString);
		checkRep();
	}
	private void checkRep() throws RuntimeException{
		//System.out.println(this.getVarString());
		if(this.getRight()!=null) {
			throw new RuntimeException("The right expression must be null");
		}
		if(this.getExpressionCode()!=2) {
			throw new RuntimeException("The expression code must be 2");
		}
		if(this.getVarString()!=null && !this.getVarString().isEmpty()) {
			throw new RuntimeException("The varString must be null");
		}
		if(this.getLeft().equals(null)) {
			throw new RuntimeException("The left expression cannot be null");
		}
	}
	@Override
	boolean evaluate(Context context) {
		return !this.getLeft().evaluate(context);
	}
	@Override
	String printPreorder() {
		if(this.getRight()==null) {
			String result="NOT "+ this.getLeft().printPreorder();
			return result;
		}
		if(this.getLeft()==null) {
			String result="NOT "+ this.getRight().printPreorder();
			return result;
		}
		String result="NOT "+ this.getLeft().printPreorder() +" "+ this.getRight().printPreorder();
		return result;
	}
	@Override
	String printInorder() {
		String result="";
		result+=("NOT ");
		if(this.getLeft()==null) {
			if(this.getRight().getExpressionCode() >= this.getExpressionCode()) {
				result+="(" + this.getRight().printInorder() + ")";

			}
			else {
				result+=this.getRight().printPreorder();
			}
		return result;
		}
		if(this.getLeft().getExpressionCode() > this.getExpressionCode()) {
			result+="(" + this.getLeft().printInorder() + ")";
		}
		else {
			result+=this.getLeft().printInorder();
		}
		
		return result;
	}
	@Override
	public void accept(Visitor v) {
		this.getLeft().accept(v);
		v.visit(this);
		}
}
	class EvaluateVisitor implements Visitor{
		//private boolean eval=true;
		private Stack<Boolean> stackEval=new Stack<Boolean>();
		Context context=null;
		public EvaluateVisitor(Context con) {
			context=con;
		}
		public void visit(VarExp exp) {
			 stackEval.push(context.lookup(exp.getVarString()));
			 System.out.println(exp.getVarString());
			// System.out.println(eval);
		}
		public void visit(Constant exp) {
			stackEval.push(exp.getValue());
		//	System.out.println(exp.toString());
		//	 System.out.println(eval);
		}
		public void visit(AndExp exp) {
			 exp.getLeft().accept(this);
			 boolean b1=stackEval.pop();
			 exp.getRight().accept(this);
			 boolean b2=stackEval.pop();
			 if(b1 && b2) {
				 stackEval.push(true);
			 }
			 else {
				 stackEval.push(false);
			 }
			 System.out.println(exp.toString());
		//	 System.out.println(b1+" "+ b2+" "+ eval);
		}
		public void visit(OrExp exp) {
			 exp.getLeft().accept(this);
			 boolean b1=stackEval.pop();
			 exp.getRight().accept(this);
			 boolean b2=stackEval.pop();
			 if(b1 || b2) {
				 stackEval.push(true);
			 }
			 else {
				 stackEval.push(false);
			 }
			 System.out.println(exp.toString());
			// System.out.println(b1+" "+ b2+" "+ eval);
		}
		public void visit(NotExp exp) {
			 exp.getLeft().accept(this);
			 boolean b1=stackEval.pop();
			 stackEval.push(!b1);
			 System.out.println(exp.getVarString());
	//		 System.out.println(eval);
		}	
		public boolean getEval() {
			return stackEval.pop();
		}
	}
	class PrintInorder implements Visitor{
		private Stack<String> stackResult=new Stack<String>();
		//@Override
		//public void visit(BooleanExp exp) {
		//	result+="ERROR";
		//}

		@Override
		public void visit(Constant exp) {
			stackResult.push(""+exp.getValue());
		}

		@Override
		public void visit(VarExp exp) {
			stackResult.push(exp.getVarString());
		}
		@Override
		public void visit(AndExp exp) {
			String result="";
			exp.getLeft().accept(this);
			if(exp.getLeft().getExpressionCode() > exp.getExpressionCode()) {
				result+="(" + stackResult.pop() + ")";
			}
			else {
				result+=stackResult.pop();
			}
			result+=(" AND ");
			exp.getRight().accept(this);
			if(exp.getRight().getExpressionCode() >= exp.getExpressionCode()) {
				result+="(" + stackResult.pop() + ")";

			}
			else {
				result+=stackResult.pop();
			}
			stackResult.push(result);
		}

		@Override
		public void visit(OrExp exp) {
			String result="";
			exp.getLeft().accept(this);
			if(exp.getLeft().getExpressionCode() > exp.getExpressionCode()) {
				result+="(" + stackResult.pop() + ")";
			}
			else {
				result+=stackResult.pop();
			}
			result+=(" OR ");
			exp.getRight().accept(this);
			if(exp.getRight().getExpressionCode() >= exp.getExpressionCode()) {
				result+="(" + stackResult.pop() + ")";

			}
			else {
				result+=stackResult.pop();
			}
			stackResult.push(result);
		}
		@Override
		public void visit(NotExp exp) {
			String result="";
			exp.getLeft().accept(this);
			result+=("NOT ");
			if(exp.getLeft().getExpressionCode() > exp.getExpressionCode()) {
				result+="(" + stackResult.pop() + ")";
			}
			else {
				result+=stackResult.pop();
			}
			stackResult.push(result);
		/*	if(exp.getLeft()==null) {
				if(exp.getRight().getExpressionCode() >= exp.getExpressionCode()) {
					result+="(" + exp.getRight().printInorder() + ")";

				}
				else {
					result+=exp.getRight().printPreorder();
				}
			}
			else if(exp.getLeft().getExpressionCode() > exp.getExpressionCode()) {
				result+="(" + exp.getLeft().printInorder() + ")";
			}
			else {
				result+=exp.getLeft().printInorder();
			}	
		}*/
		}
		public String getInorder() {
			return stackResult.pop();
		}
		
	}
	interface Visitor{
		//public void visit(BooleanExp exp);
		public void visit(Constant exp);
		public void visit(VarExp exp);
		public void visit(AndExp exp);
		public void visit(OrExp exp);
		public void visit(NotExp exp);
}
