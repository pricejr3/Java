
public class NumberPrinter 
{

	synchronized public void printTenNumbers(int startingNumber)
	{

		for(int i = startingNumber; i < startingNumber + 10; i++ ){
			
			System.out.print( i + " ");
			
			try {
				Thread.sleep((long)(Math.random() * 10));
			} catch (InterruptedException e) {}
						
		}
		
		System.out.println();
	}
	
	synchronized public void printString( String s){
		System.out.println(s);
	}
}
