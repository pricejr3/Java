
public class RunnableImplementer implements Runnable {

	protected NumberPrinter printer;
	
	
	 public RunnableImplementer(NumberPrinter p){
		 this.printer = p;
	}

	
	@Override
	public void run() {
		for (int i = 200; i <= 299; i+=10) {
			//System.out.println(i + "\t" + "\t");
			
			printer.printTenNumbers(i);
			
			try {
				Thread.sleep((long) (Math.random() * 10));

			} catch (InterruptedException e) {
			}

			// Thread.yield();
		}
		printer.printString("RunnableImplementer completed.");

	}

}

