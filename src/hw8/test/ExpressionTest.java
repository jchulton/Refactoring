package hw8.test;

import hw8.*;
import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import java.util.*;

public class ExpressionTest {

	private static ExpressionParser parser = null;
	private static HashMap<String,Boolean> contextMap = null;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    	parser = new ExpressionParser();
    	contextMap = new HashMap<String,Boolean>();
    	contextMap.put("x", true);
    	contextMap.put("y", false);
    	contextMap.put("z", true);
    	contextMap.put("w", false);
    }

	@Test
	public void test0() {
		ExpressionParser.position = 0;
		BooleanExp e = ExpressionParser.parse("x");
		parser.init(e,contextMap);
		assertEquals("x",e.toString());
		assertEquals(true,parser.evaluate());
		assertEquals("x",parser.print(false));
		assertEquals("x",parser.print(true)); //ANA: new
	}
    
	@Test
	public void test1() {
		ExpressionParser.position = 0;
		BooleanExp e = ExpressionParser.parse("true");
		parser.init(e,contextMap);
		assertEquals("true",e.toString());
		assertEquals(true,parser.evaluate());
		assertEquals("true",parser.print(false));
		assertEquals("true",parser.print(true)); //ANA: new
	}
	
	@Test
	public void test2() {
		
		ExpressionParser.position = 0;
		BooleanExp e = ExpressionParser.parse("OR AND x y z");
		assertEquals("OR AND x y z",e.toString());
		parser.init(e, contextMap);
		assertEquals(true,parser.evaluate());
		assertEquals("x AND y OR z",parser.print(false));
		assertEquals("OR AND x y z",parser.print(true)); //ANA: new
	}

	@Test
	public void test3() {		
		ExpressionParser.position = 0;
		BooleanExp e = ExpressionParser.parse("AND OR x y OR x w");
		assertEquals("AND OR x y OR x w",e.toString());
		parser.init(e, contextMap);
		assertEquals(true,parser.evaluate());
		assertEquals("(x OR y) AND (x OR w)",parser.print(false));
		assertEquals("AND OR x y OR x w",parser.print(true));

	}

	@Test
	public void test4() {		
		ExpressionParser.position = 0;
		BooleanExp e = ExpressionParser.parse("NOT x");
		assertEquals("NOT x",e.toString());
		parser.init(e, contextMap);
		assertEquals(false,parser.evaluate());
		assertEquals("NOT x",parser.print(false));

	}
	
	@Test
	public void test5() {		
		ExpressionParser.position = 0;
		BooleanExp e = ExpressionParser.parse("AND NOT x OR x w");
		assertEquals("AND NOT x OR x w",e.toString());
		parser.init(e, contextMap);
		assertEquals(false,parser.evaluate());
		assertEquals("NOT x AND (x OR w)",parser.print(false));

	}
	
}
