
public interface MailInterfaceListener {
	
	/**
	 * Uses contents of GUI Text fields to construct an
	 * email message and send it.
	 * 
	 * @returns True if the message was successfully sent. False otherwise.
	 */
	public boolean sendMail();
	
	/**
	 * Performs any required closing operations and releases resources
	 * when the GUI is closed down.
	 */
	public void close();
	
} // end MailInterfaceListener interface
