import java.util.Scanner;



public class Main {
	private static Complex getComplex(String prompt, Scanner in)
	{
		float real, imag;
		System.out.println( prompt );
		System.out.print("Real part: ");
		real = in.nextFloat();
		System.out.print("Imaginary part: ");
		imag = in.nextFloat();
		return new Complex( real, imag);
	}
	
	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
		Complex rightNumber = new Complex();
		Complex leftNumber = new Complex();
		Complex result = new Complex();
		int choice;
		
		do {
			System.out.println("\n\nComplex Number Calculator\n");
			System.out.println("\t1. Add\n\t2. Subtract\n\t3. Multiply\n\t4. Divide\n\t5. Quit\n\n");
			choice = in.nextInt();
			
			switch (choice) {
			case 1: 
				leftNumber = getComplex("Left Number", in);
				rightNumber = getComplex("Right Number", in);
				result = leftNumber.add(rightNumber);
				System.out.println(leftNumber + " + " + rightNumber + " = " + result );
				break;
			case 2: 
				leftNumber = getComplex("Left Number", in);
				rightNumber = getComplex("Right Number", in);
				result = leftNumber.subtract(rightNumber);
				System.out.println(leftNumber + " - " + rightNumber + " = " + result );
				break;
			case 3: 
				leftNumber = getComplex("Left Number", in);
				rightNumber = getComplex("Right Number", in);
				result = leftNumber.multiply(rightNumber);
				System.out.println(leftNumber + " * " + rightNumber + " = " + result );
				break;
			case 4: 
				leftNumber = getComplex("Left Number", in);
				rightNumber = getComplex("Right Number", in);
				result = leftNumber.divide(rightNumber);
				System.out.println(leftNumber + " / " + rightNumber + " = " + result );
				break;
			case 5: System.out.println("Goodbye!"); break;
			default: System.out.println("Please enter 1 - 5");
			}
		} while (choice != 5);
	}
}
