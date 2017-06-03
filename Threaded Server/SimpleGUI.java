
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;


public class SimpleGUI extends JFrame implements ActionListener{
	
	protected NumberPrinter printer;


	// ID number for serialization
	private static final long serialVersionUID = 1L;
	
	
	// Question here
	public SimpleGUI(NumberPrinter p)
	{
		this("GUI Thread", p);
		this.printer = p;
	
	}

	public SimpleGUI(String guiName, NumberPrinter p)
	{
		super(guiName);
		this.printer = p;
		
		this.setSize( new Dimension(100, 100));

		Container contentPane = this.getContentPane();
		
		// Button to be pressed when to display numbers
		JButton numberButton= new JButton("Print Numbers");
		
		contentPane.add( numberButton );
		
		// Register a listener for the request game button
		numberButton.addActionListener( this);
		
		// Make the window visible
		this.setVisible(true);
		
		// Stop the program when the window is closed.
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	} // end TicToeBoard constructor
	
			
	public void actionPerformed(ActionEvent event) 
	{
		int lineCounter = 1;
		for(int i = 300; i < 399; i+=10){
			
			
			printer.printTenNumbers(i);
			//System.out.println( "\t\t\t" + i);
			
			try {
				   Thread.sleep((long)(Math.random() * 10));
			} catch (InterruptedException e) {}

					
		}
		
		printer.printString(this.getName() + " completed.");
		

	} // end actionPerformed

	
} // end SimpleGUI class

