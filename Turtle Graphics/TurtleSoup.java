/* Copyright (c) 2007-2014 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */

package turtle;

import java.lang.Math;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class TurtleSoup {

	/**
	 * Draw a square.
	 * 
	 * @param turtle
	 *            the turtle context
	 * @param sideLength
	 *            length of each side
	 */
	public static void drawSquare(Turtle turtle, int sideLength) {

		for (int i = 0; i < 4; i++) {
			turtle.forward(sideLength);
			turtle.turn(90);
		}

	}

	/**
	 * Draw a circle.
	 * 
	 * @param turtle
	 *            the turtle context
	 * @param circleValue
	 *            the value for circle calculation
	 */
	public static void circle(Turtle turtle, double circleValue) {
		for (int i = 0; i < 360; i++) {
			turtle.forward((int) (circleValue * .0174));
			turtle.turn(1);
		}
	}

	/**
	 * Draws a triangle.
	 * 
	 * 
	 * @param turtle
	 *            the turtle used in the calculations.
	 * @param randomNum
	 *            the random number used for calculating the sizes.
	 */
	public static void triangle(Turtle turtle, int randomNum) {
		int length = 1;
		while (length <= 3) {
			turtle.forward(randomNum);
			turtle.turn(120);
			length = length + 1;
		}
	}

	/**
	 * Determine inside angles of a regular polygon.
	 * 
	 * There is a simple formula for calculating the inside angles of a polygon;
	 * you should derive it and use it here.
	 * 
	 * @param sides
	 *            number of sides, where sides must be > 2
	 * @return angle in degrees, where 0 <= angle < 360
	 */
	public static double calculateRegularPolygonAngle(int sides) {

		double doubleSides = (double) sides;
		double angleConverter = 180.0;
		double returnValue = (doubleSides - 2.0) * angleConverter;
		returnValue = returnValue / doubleSides;

		return returnValue;
	}

	/**
	 * Determine number of sides given the size of interior angles of a regular
	 * polygon.
	 * 
	 * There is a simple formula for this; you should derive it and use it here.
	 * Make sure you *properly round* the answer before you return it (see
	 * java.lang.Math). HINT: it is easier if you think about the exterior
	 * angles.
	 * 
	 * @param angle
	 *            size of interior angles in degrees
	 * @return the integer number of sides
	 */
	public static int calculatePolygonSidesFromAngle(double angle) {

		double value1 = 180.0 - angle;
		double value2 = Math.round(360.0 / value1);

		int returnVal = (int) value2;
		return returnVal;

	}

	/**
	 * Given the number of sides, draw a regular polygon.
	 * 
	 * (0,0) is the lower-left corner of the polygon; use only right-hand turns
	 * to draw.
	 * 
	 * @param turtle
	 *            the turtle context
	 * @param sides
	 *            number of sides of the polygon to draw
	 * @param sideLength
	 *            length of each side
	 */
	public static void drawRegularPolygon(Turtle turtle, int sides,
			int sideLength) {

		double calculatedRegularPolygonAngle = calculateRegularPolygonAngle(sides);

		for (int i = 0; i < sides; i++) {
			turtle.forward(sideLength);
			turtle.turn(180 - calculatedRegularPolygonAngle);
		}

	}

	/**
	 * Given the current direction, current location, and a target location,
	 * calculate the heading towards the target point.
	 * 
	 * The return value is the angle input to turn() that would point the turtle
	 * in the direction of the target point (targetX,targetY), given that the
	 * turtle is already at the point (currentX,currentY) and is facing at angle
	 * currentHeading. The angle must be expressed in degrees, where 0 <= angle
	 * < 360.
	 *
	 * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math
	 * libraries
	 * 
	 * @param currentHeading
	 *            current direction as clockwise from north
	 * @param currentX
	 *            currentY current location
	 * @param targetX
	 *            targetY target point
	 * @return adjustment to heading (right turn amount) to get to target point,
	 *         must be 0 <= angle < 360.
	 */
	public static double calculateHeadingToPoint(double currentHeading,
			int currentX, int currentY, int targetX, int targetY) {

		// Calculates the targeted value using atan2.
		double targetValue = 90 + (-180
				* Math.atan2(targetY - currentY, targetX - currentX) / Math.PI);

		// Calculated part = target - heading.
		double calculatedPart = targetValue - currentHeading;

		if (targetValue > 0) {
			targetValue = targetValue;
		}

		if (targetValue < 0) {
			targetValue = targetValue - 180;
		}

		if (calculatedPart < 0) {
			calculatedPart = calculatedPart + 360.0;
		}

		if (calculatedPart > 0) {
			// return calculatedPart;
			calculatedPart = calculatedPart + 0;
		}

		// Return it.
		return calculatedPart;

	}

	/**
	 * Given a sequence of points, calculate the heading adjustments needed to
	 * get from each point to the next.
	 * 
	 * Assumes that the turtle starts at the first point given, facing up (i.e.
	 * 0 degrees). For each subsequent point, assumes that the turtle is still
	 * facing in the direction it was facing when it moved to the previous
	 * point. You should use calculateHeadingToPoint() to implement this
	 * function.
	 * 
	 * @param xCoords
	 *            list of x-coordinates (must be same length as yCoords)
	 * @param yCoords
	 *            list of y-coordinates (must be same length as xCoords)
	 * @return list of heading adjustments between points, of size (# of points)
	 *         - 1.
	 */
	public static List<Double> calculateHeadings(List<Integer> xCoords,
			List<Integer> yCoords) {

		// The list to return.
		List<Double> returnList = new ArrayList<Double>();

		// Variables used in do-while loop.
		double temp = 0;
		int listSize = xCoords.size();
		listSize--;
		int i = 0;

		do {

			// Do nothing if temp value less than 360.
			if (temp < 360) {
				temp = 0 + temp;
			}

			// Subtract by 360 if greater than 360.
			if (temp > 360) {
				temp = -360 + temp;
			}
			if (i > 0) {
				temp = returnList.get(-1 + i) + temp;
			}

			// Calculate the value to add using calculateHeadingToPoint.
			double addValue = (calculateHeadingToPoint(temp, xCoords.get(i),
					yCoords.get(i), xCoords.get(1 + i), yCoords.get(1 + i)));

			// Add the value to the list.
			returnList.add(addValue);
			i++;

		} while (i < listSize);

		// Return the new list.
		return returnList;

	}

	/**
	 * Draw your personal, custom art.
	 * 
	 * Many interesting images can be drawn using the simple implementation of a
	 * turtle. For this function, draw something interesting; the complexity can
	 * be as little or as much as you want. We'll be peer-voting on the
	 * different images, and the highest-rated one will win a prize.
	 * 
	 * @param turtle
	 *            the turtle context
	 */
	public static void drawPersonalArt(Turtle turtle) {

		Random rnd = new Random();
		int randomNum = 0;
		int randomNum2 = 0;
		int randomColor = 0;
		double circleValue = 0;

		for (int x = 0; x < 500; x++) {

			randomColor = rnd.nextInt((5) + 1);
			if (randomColor == 0) {
				turtle.color(PenColor.BLUE);
			}
			if (randomColor == 1) {
				turtle.color(PenColor.GREEN);
			}
			if (randomColor == 2) {
				turtle.color(PenColor.RED);
			}
			if (randomColor == 3) {
				turtle.color(PenColor.PINK);
			}
			if (randomColor == 4) {
				turtle.color(PenColor.YELLOW);
			}
			if (randomColor == 5) {
				turtle.color(PenColor.MAGENTA);
			}
			if (randomColor == 6) {
				turtle.color(PenColor.CYAN);
			}
			if (randomColor == 7) {
				turtle.color(PenColor.GREEN);
			}
			if (randomColor == 8) {
				turtle.color(PenColor.ORANGE);
			}
			if (randomColor == 9) {
				turtle.color(PenColor.BLACK);
			}

			randomNum = rnd.nextInt((350) + 1);
			randomNum2 = rnd.nextInt((250) + 1);
			drawSquare(turtle, randomNum - 155);
			drawSquare(turtle, randomNum2 - 125);

		}

		turtle.turn(280);
		turtle.forward(125);

		for (int i = 0; i < 2500; i++) {
			turtle.color(PenColor.RED);
			randomNum = rnd.nextInt((350) + 1);
			triangle(turtle, randomNum);
		}
		turtle.turn(210);
		turtle.forward(50);

		for (int i = 0; i < 200; i++) {
			turtle.color(PenColor.ORANGE);
			randomNum = rnd.nextInt((50) + 1);
			triangle(turtle, -randomNum);
		}

		turtle.forward(250);
		for (int i = 0; i < 300; i++) {
			turtle.color(PenColor.BLUE);
			randomNum = rnd.nextInt((350) + 1);
			triangle(turtle, randomNum - 250);
		}

		turtle.turn(180);
		turtle.color(PenColor.PINK);
		turtle.forward(360);
		turtle.turn(90);
		turtle.forward(200);

		for (int x = 0; x < 500; x++) {

			randomNum = rnd.nextInt((350) + 1);
			if (randomNum - 155 < 0) {
				turtle.color(PenColor.CYAN);
			} else {
				turtle.color(PenColor.RED);
			}
			drawSquare(turtle, randomNum - 155);
		}

		turtle.turn(90);
		turtle.forward(300);

		for (int x = 0; x < 500; x++) {

			randomNum = rnd.nextInt((200) + 1);
			if (randomNum - 155 < 0) {
				turtle.color(PenColor.PINK);
			} else {
				turtle.color(PenColor.MAGENTA);
			}
			drawSquare(turtle, randomNum - 55);
		}

		turtle.color(PenColor.BLACK);
		turtle.turn(125);
		turtle.forward(200);
		turtle.turn(-150);
		turtle.forward(100);

		for (int i = 0; i < 2500; i++) {
			randomNum = rnd.nextInt((200) + 1);
			if (randomNum < 100) {
				turtle.color(PenColor.BLUE);
			}
			if (randomNum > 100) {
				turtle.color(PenColor.GREEN);
			}

			triangle(turtle, randomNum);
		}

		turtle.turn(150);
		turtle.forward(250);

		for (int x = 0; x < 500; x++) {

			randomColor = rnd.nextInt((5) + 1);
			if (randomColor == 0) {
				turtle.color(PenColor.BLUE);
			}
			if (randomColor == 1) {
				turtle.color(PenColor.GREEN);
			}
			if (randomColor == 2) {
				turtle.color(PenColor.RED);
			}
			if (randomColor == 3) {
				turtle.color(PenColor.PINK);
			}
			if (randomColor == 4) {
				turtle.color(PenColor.YELLOW);
			}
			if (randomColor == 5) {
				turtle.color(PenColor.MAGENTA);
			}
			if (randomColor == 6) {
				turtle.color(PenColor.CYAN);
			}
			if (randomColor == 7) {
				turtle.color(PenColor.GREEN);
			}
			if (randomColor == 8) {
				turtle.color(PenColor.ORANGE);
			}
			if (randomColor == 9) {
				turtle.color(PenColor.BLACK);
			}

			randomNum = rnd.nextInt((55) + 1);
			randomNum2 = rnd.nextInt((55) + 1);
			drawSquare(turtle, randomNum + 55);
			turtle.turn(80);

			drawSquare(turtle, randomNum2 + 78);

			randomNum2 = rnd.nextInt((200) + 1);
			circle(turtle, randomNum2 - 10);

		}

	}

	/**
	 * Main method.
	 * 
	 * This is the method that runs when you run "java TurtleSoup".
	 */
	public static void main(String args[]) {
		DrawableTurtle turtle = new DrawableTurtle();

		// drawRegularPolygon(turtle, 10, 50);
		drawPersonalArt(turtle);
		// draw the window
		turtle.draw();
	}

}
