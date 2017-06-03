public class ThreadMaker {

	public static void main(String[] args) {
		// t1.run(); DOESN'T WORK FOR GOOD REASON

		/*
		 * 
		 * In the main method of the ThreadMaker class, instantiate an object of
		 * the RunnableImplementer class somewhere before the for loop.
		 */

		NumberPrinter np = new NumberPrinter();
		
	
		SimpleGUI gui = new SimpleGUI("Gui Thread", np);
		
		ThreadExtender t1 = new ThreadExtender("t1", np);
		// RunnableImplementer t2 = new RunnableImplementer();
		t1.start();

		RunnableImplementer r1 = new RunnableImplementer(np);
		Thread t = new Thread(r1);
		t.start();

		/*
		 * 
		 * Replace the print statements in the for loops in all four classes
		 * with a call to the printTenNumbers method of the NumberPrinter class.
		 * Call the method through the class data member. Pass the loop index to
		 * the method each time it is called.
		 */

		for (int i = 0; i <= 99; i += 10) {
			// System.out.println(i);
			np.printTenNumbers(i);

			
			try {
				Thread.sleep((long) (Math.random() * 10));
				Thread.yield();
			} catch (InterruptedException e) {

				// Thread.yield();
			}

			np.printString("Thread maker complete");
		}

	}

}