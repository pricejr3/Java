/**
 * Jarred Price 
 * Instructor: Dr. Kiper 
 * CSE 211
 *
 * 17 February 2015.
 * 
 * This program serves as a calculator for Complex Numbers.
 *
 **/
public class Complex {

	// Initialize the real and imaginary parts that are used to create complex
	// numbers.
	public float rPart = 0.0f;
	public float iPart = 0.0f;

	// Creates a complex number with no values for the real and imaginary parts,
	// e.g. ( 0 + 0i).
	public Complex() {
		rPart = 0;
		iPart = 0;
	}

	// Creates a complex number with the inputed float numbers.
	public Complex(float r, float i) {
		iPart = i;
		rPart = r;
	}

	/**
	 * Performs addition of two complex numbers.
	 * 
	 * @return the new complex number created from addition.
	 */
	public Complex add(Complex other) {
		return new Complex(this.rPart + other.rPart, this.iPart + other.iPart);
	}

	/**
	 * Performs subtraction of two complex numbers.
	 * 
	 * @return the new complex number created from subtraction.
	 */
	public Complex subtract(Complex other) {
		return new Complex(this.rPart - other.rPart, this.iPart - other.iPart);
	}

	/**
	 * Performs multiplication of two complex numbers.
	 * 
	 * @return the new complex number created from multiplication.
	 */
	public Complex multiply(Complex other) {
		return new Complex(this.rPart * other.rPart - this.iPart * other.iPart,
				this.rPart * other.iPart + this.iPart * other.rPart);
	}

	/**
	 * Performs division of two complex numbers.
	 * 
	 * @return the new complex number created from division.
	 */
	public Complex divide(Complex other) {
		float part1 = (this.rPart * other.rPart + this.iPart * other.iPart)
				/ (other.rPart * other.rPart + other.iPart * other.iPart);
		float part2 = (this.iPart * other.rPart - this.rPart * other.iPart)
				/ (other.rPart * other.rPart + other.iPart * other.iPart);

		return new Complex(part1, part2);
	}

	/**
	 * Converts the complex number to a nice string representation.
	 * 
	 * @return the string representation of the complex number.
	 */
	public String toString() {

		if (iPart > 0)
			return rPart + " + " + iPart + "i";
		if (rPart == 0)
			return iPart + "i";
		if (iPart == 0)
			return "" + rPart;
		return rPart + " - " + (-iPart) + "i";

	}


}
