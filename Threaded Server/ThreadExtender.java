public class ThreadExtender extends Thread {

	protected NumberPrinter printer;

	public ThreadExtender(String threadName, NumberPrinter p) {
		super(threadName);
		this.printer = p;

	}

	@Override
	public void run() {
		for (int i = 100; i <= 199; i += 10) {
			printer.printTenNumbers(i);
			//System.out.print(i + "\t");
			try {
				Thread.sleep((long) (Math.random() * 10));

			} catch (InterruptedException e) {
			}

			// Thread.yield();
		}
		
		printer.printString(this.getName() + " completed.");
	

	}

}
