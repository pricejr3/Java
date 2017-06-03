// Jarred Price
// PRICEJR3
// CSE 321
// 10 FEB 2016


import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Scanner;

public class UtilitiesTest {

	
	
	// Test to test for everything.
	@Test
	public void userTest() {
		Scanner keyboard = new Scanner( System.in );
		System.out.println("Enter two integers: ");
		int x = keyboard.nextInt();
		int y = keyboard.nextInt();
		assertEquals("Utilities User Test", Math.max(x, y), Utilities.max(x, y));
	}
	
	// Tests 1-10 check for positives, negatives and including 0.
	// Checks to see answers that are actually correct.
	
	/*
	 * Testing for X > Y with both positive
	 */
	@Test
	public void test1() {
		assertEquals("Utilities Test 1 (X > Y:", 15, Utilities.max(5, 15));
	}
	
	/*
	 * Testing for X < Y with both positive 
	 */
	@Test
	public void test2() {
		assertEquals("Utilities Test 2 (X < Y:", 15, Utilities.max(15, 5));
	}
	
	/*
	 * Testing for X > 0 
	 */
	@Test
	public void test3() {
		assertEquals("Utilities Test 3 (X > 0:", 5, Utilities.max(5, 0));
	}
	
	/*
	 * Testing for 0 < Y 
	 */
	@Test
	public void test4() {
		assertEquals("Utilities Test 4 (0 < Y:", 5, Utilities.max(0, 5));
	}
	
	
	/*
	 * Testing for -X > -Y 
	 */
	@Test
	public void test5() {
		assertEquals("Utilities Test 5 (-X > -Y:", -5, Utilities.max(-5, -15));
	}
	
	/*
	 * Testing for -X < -Y 
	 */
	@Test
	public void test6() {
		assertEquals("Utilities Test 6 (-X < -Y:", -5, Utilities.max(-15, -5));
	}
	
	/*
	 * Testing for -X < Y 
	 */
	@Test
	public void test7() {
		assertEquals("Utilities Test 7 (-X < Y:", 15, Utilities.max(-5, 15));
	}
	
	/*
	 * Testing for -X < 0
	 */
	@Test
	public void test8() {
		assertEquals("Utilities Test 8 (-X < 0:", 0, Utilities.max(-5, 0));
	}
	
	/*
	 * Testing for X > -Y 
	 */
	@Test
	public void test9() {
		assertEquals("Utilities Test 9 (X > -Y:", 5, Utilities.max(5, -10));
	}
	
	/*
	 * Testing for 0 > -Y  
	 */
	@Test
	public void test10() {
		assertEquals("Utilities Test 10 (0 > -Y:", 0, Utilities.max(0, -10));
	}




}
