/**
 * Jarred Price 
 * 
 * This program uses JUnit to test the Complex numbers class.
 *
 **/


import static org.junit.Assert.*;
import org.junit.Test;
public class ComplexTester {

	/*
	 * This tests to see if the toString() method works.
	 */
	@Test
	public void testToString() {

		Complex cmplxOne = new Complex(-5f, 5f);
		String compareMe = cmplxOne.toString();
		assertEquals("Addition of these should yield -5.0 + 5.0i",
				"-5.0 + 5.0i", compareMe);
	}

	/*
	 * This tests to see if the toString() method works for the default, no
	 * parameter constructor.
	 */
	@Test
	public void testToStringDefaultConstructor() {

		Complex cmplxOne = new Complex();
		String compareMe = cmplxOne.toString();
		assertEquals("Addition of these should yield 0.0i", "0.0i", compareMe);
	}

	/*
	 * This tests to see if the default constructor works.
	 */
	@Test
	public void testDefaultConstructor() {

		Complex cmplxOne = new Complex();
		Complex other = new Complex();
		String compareMe = cmplxOne.add(other).toString();
		assertEquals("Addition of these should yield 0.0i", "0.0i", compareMe);
	}

	/*
	 * This tests to see if the default constructor works with a Complex object
	 * not made from the default constructor.
	 */
	@Test
	public void testDefaultConstructor2() {

		Complex cmplxOne = new Complex();
		Complex other = new Complex(5f, 198f);
		String compareMe = cmplxOne.add(other).toString();
		assertEquals("Addition of these should yield 5.0 + 198.0i",
				"5.0 + 198.0i", compareMe);
	}

	/*
	 * This tests to see if a Complex object can yield made with the largest
	 * value of a float.
	 */
	@Test
	public void inBoundsFloat() {

		float max = Float.MAX_VALUE;
		Complex cmplxOne = new Complex(max, 0f);
		Complex other = new Complex(0f, 0f);
		String compareMe = cmplxOne.add(other).toString();
		assertEquals("Addition of these should yield 3.4028235E38",
				"3.4028235E38", compareMe);
	}

	/*
	 * This tests to see if a Complex object can yield made with the larger than
	 * a float value.
	 */
	@Test
	public void outOfBoundsFloat() {

		float max = Float.MAX_VALUE;
		Complex cmplxOne = new Complex(max + 99999, 0f);
		Complex other = new Complex(max, 0f);
		String compareMe = cmplxOne.add(other).toString();
		assertEquals("Addition of these should yield Infinity", "Infinity",
				compareMe);
	}

	/*
	 * This tests to see if the addition of 2 nums yields: 1 + 1i and 1 + 1i
	 * actually equals 2.0 + 2.0i.
	 */
	@Test
	public void testAddition() {

		Complex cmplxOne = new Complex(1f, 1f);
		Complex other = new Complex(1f, 1f);
		String compareMe = cmplxOne.add(other).toString();
		assertEquals("Addition of these should yield 2.0 + 2.0i", "2.0 + 2.0i",
				compareMe);
	}

	/*
	 * Tests subtraction.
	 */
	@Test
	public void testSubtraction() {

		Complex cmplxOne = new Complex(5f, 5f);
		Complex other = new Complex(1f, 1f);
		String compareMe = cmplxOne.subtract(other).toString();
		assertEquals("Subtraction of these should yield 4.0 + 4.0i",
				"4.0 + 4.0i", compareMe);
	}

	/*
	 * Tests division.
	 */
	@Test
	public void testDivision() {

		Complex cmplxOne = new Complex(5f, 5f);
		Complex other = new Complex(1f, 1f);
		String compareMe = cmplxOne.divide(other).toString();
		assertEquals("Division of these should yield 5.0", "5.0", compareMe);
	}

	/*
	 * Tests multiplication.
	 */
	@Test
	public void testMultiplication() {

		Complex cmplxOne = new Complex(9f, 4f);
		Complex other = new Complex(1f, 77f);
		String compareMe = cmplxOne.multiply(other).toString();
		assertEquals("Multiplication of these should yield -299.0 + 697.0i",
				"-299.0 + 697.0i", compareMe);
	}

	/*
	 * Tests values with decimals
	 */
	@Test
	public void testValuesWithDecimals() {

		Complex cmplxOne = new Complex(0.0000000000000009f,
				4.76777777777777777f);
		Complex other = new Complex(1.55555555555555555555f,
				99899.99999999999877f);
		String compareMe = cmplxOne.add(other).toString();
		assertEquals("Addition of these should yield 1.5555556 + 99904.766i",
				"1.5555556 + 99904.766i", compareMe);
	}

	/*
	 * This tests to see if the addition of: 0 + 0i and 0 + 0i actually equals
	 * 0.0i.
	 */
	@Test
	public void testAdditionofZeroes() {

		Complex cmplxOne = new Complex(0f, 0f);
		Complex other = new Complex(0f, 0f);
		String compareMe = cmplxOne.add(other).toString();
		assertEquals("Addition of these should yield 0.0i", "0.0i", compareMe);
	}

	/*
	 * This tests to see 0.0 works for all..
	 */
	@Test
	public void testZeroPointZero() {

		Complex cmplxOne = new Complex(0.0000000000000f, 0.00f);
		Complex other = new Complex(0.0000f, 0.0f);
		String compareMe = cmplxOne.add(other).toString();
		assertEquals("Addition of these should yield 0.0i", "0.0i", compareMe);
	}

	/*
	 * This tests to see if the addition of positive and negative real values.
	 */
	@Test
	public void testAdditionOfRealPositivesRealNegatives() {

		Complex cmplxOne = new Complex(5f, 5f);
		Complex other = new Complex(-7f, 6f);
		String compareMe = cmplxOne.add(other).toString();
		assertEquals("Addition of these should yield -2.0 + 11.0i",
				"-2.0 + 11.0i", compareMe);
	}

	/*
	 * This tests to see if the addition of positive and negative real values.
	 */
	@Test
	public void testAdditionOfRealPositivesRealNegatives2() {

		Complex cmplxOne = new Complex(-5f, 5f);
		Complex other = new Complex(7f, 6f);
		String compareMe = cmplxOne.add(other).toString();
		assertEquals("Addition of these should yield 2.0 + 11.0i",
				"2.0 + 11.0i", compareMe);
	}

	// ///////////////////////////////////////////////
	/*
	 * Tests to see if addition works with all negatives.
	 */
	@Test
	public void testAdditionOfAllNegatives() {

		Complex cmplxOne = new Complex(-5f, -54f);
		Complex other = new Complex(-7f, -96f);
		String compareMe = cmplxOne.add(other).toString();
		assertEquals("Addition of these should yield -12.0 - 150.0i",
				"-12.0 - 150.0i", compareMe);
	}

	/*
	 * Tests to see if subtraction works with all negatives.
	 */
	@Test
	public void testSubtractionOfAllNegatives() {

		Complex cmplxOne = new Complex(-5f, -9f);
		Complex other = new Complex(-3f, -18f);
		String compareMe = cmplxOne.subtract(other).toString();
		assertEquals("Subtraction of these should yield -2.0 + 9.0i",
				"-2.0 + 9.0i", compareMe);
	}

	/*
	 * Tests to see if multiplication works with all negatives.
	 */
	@Test
	public void testMultiplicationOfAllNegatives() {

		Complex cmplxOne = new Complex(-5f, -9f);
		Complex other = new Complex(-3f, -18f);
		String compareMe = cmplxOne.multiply(other).toString();
		assertEquals("Multiplication of these should yield -147.0 + 117.0i",
				"-147.0 + 117.0i", compareMe);
	}

	/*
	 * Tests to see if multiplication works with all negatives.
	 */
	@Test
	public void testDivisionOfAllNegatives() {

		Complex cmplxOne = new Complex(-5f, -9f);
		Complex other = new Complex(-3f, -18f);
		String compareMe = cmplxOne.divide(other).toString();
		assertEquals("Division of these should yield 0.5315315 - 0.1891892i",
				"0.5315315 - 0.1891892i", compareMe);
	}
	
	// Test cases below - testing all permutations of positives and negatives.
	
	
	/*
	 * 
	 * Testing addition of all positives.
	 */
	@Test
	public void testAddAllPositives() {
		 
	    Complex one = new Complex(14, 16);
	    Complex two = new Complex(6, 5);
	    Complex three = one.add(two);
	    assertEquals("20.0 + 21.0i", three.toString());
	  
	  }
	
	
	@Test
	public void additionTestOnePositive() {
		 
	    Complex one = new Complex(14, -16);
	    Complex two = new Complex(-6, -5);
	    Complex three = one.add(two);
	    assertEquals("8.0 - 21.0i", three.toString());
	  
	  }
	
	@Test
	public void additionTestTwoPositives() {
		 
	    Complex one = new Complex(14, 16);
	    Complex two = new Complex(-6, -5);
	    Complex three = one.add(two);
	    assertEquals("8.0 + 11.0i", three.toString());
	  
	  }
	
	@Test
	public void additionTestThreePositives() {
		 
	    Complex one = new Complex(14, 16);
	    Complex two = new Complex(6, -5);
	    Complex three = one.add(two);
	    assertEquals("20.0 + 11.0i", three.toString());
	  
	  }
	
	@Test
	public void multiplicationThreePositives() {
		 
	    Complex one = new Complex(14, 16);
	    Complex two = new Complex(6, -5);
	    Complex three = one.multiply(two);
	    assertEquals("164.0 + 26.0i", three.toString());
	  
	  }
	
	@Test
	public void multiplicationTwoPositives() {
		 
	    Complex one = new Complex(14, 16);
	    Complex two = new Complex(-6, -5);
	    Complex three = one.multiply(two);
	    assertEquals("-4.0 - 166.0i", three.toString());
	  
	  }
	
	@Test
	public void multiplicationOnePositive() {
		 
	    Complex one = new Complex(-14, 16);
	    Complex two = new Complex(-6, -5);
	    Complex three = one.multiply(two);
	    assertEquals("164.0 - 26.0i", three.toString());
	  
	  }
	
	@Test
	public void divisionOnePositive() {
		 
	    Complex one = new Complex(14, -16);
	    Complex two = new Complex(-6, -5);
	    Complex three = one.divide(two);
	    assertEquals("-0.06557377 + 2.7213116i", three.toString());
	  
	  }
	
	
	@Test
	public void divisionTwoPositives() {
		 
	    Complex one = new Complex(14, 16);
	    Complex two = new Complex(-6, -5);
	    Complex three = one.divide(two);
	    assertEquals("-2.6885245 - 0.4262295i", three.toString());
	  
	  }
	
	
	@Test
	public void divisionThreePositives() {
		 
	    Complex one = new Complex(14, 16);
	    Complex two = new Complex(6, -5);
	    Complex three = one.divide(two);
	    assertEquals("0.06557377 + 2.7213116i", three.toString());
	  
	  }
	
	
	@Test
	public void subtractionThreePositives() {
		 
	    Complex one = new Complex(14, 16);
	    Complex two = new Complex(6, -5);
	    Complex three = one.subtract(two);
	    assertEquals("8.0 + 21.0i", three.toString());
	  
	  }
	
	
	@Test
	public void subtractionTwoPositives() {
		 
	    Complex one = new Complex(14, 16);
	    Complex two = new Complex(-6, -5);
	    Complex three = one.subtract(two);
	    assertEquals("20.0 + 21.0i", three.toString());
	  
	  }
	
	
	@Test
	public void subtractionOnePositive() {
		 
	    Complex one = new Complex(14, -16);
	    Complex two = new Complex(-6, -5);
	    Complex three = one.subtract(two);
	    assertEquals("20.0 - 11.0i", three.toString());
	  
	  }
	
	
	@Test
	public void toStringAgain() {
		 
	    Complex one = new Complex(0.0000000f, 1.4f);
	    String compareMe = one.toString();
	    assertEquals("0.0 + 1.4i", compareMe);
	  
	  }
	
	
	/*
	 * Testing to see if you can't divide by zero.
	 */
	@Test
	public void divideByZero(){
		Complex one = new Complex(1.00000f, 34343.000f);
		Complex two = new Complex(0.0f, 0.0f);
		Complex three = one.divide(two);
		String compareMe = three.toString();
		assertTrue(compareMe.equals("NaN - NaNi"));
		
	}
	
	
	
	
	
	
	
	
	


}
