import java.io.IOException;


/**
 * @author bachmaer
 * 
 *         To change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Generation - Code and Comments
 */
public class MessageBuilder implements MailInterfaceListener {

	// Server, sender, recipient, subject and body for the message.
	private String server;
	private String from;
	private String to;
	private String subject;
	private String body;

	// TCP connection to the server.
	SMTPConnection smtpConnection = null;

	// GUI interface for the mail application
	MailInterface mailFace;

	/**
	 * Instantiates an interface and stores a reference to it in a data member.
	 * 
	 */
	public MessageBuilder() {
		mailFace = new MailInterface(this);

	} // end constructor

	/**
	 * Called by the interface when the send message is pressed. It opens an
	 * SMTPConnection with the appropriate server. Puts together mail message
	 * commands, and message data and uses the SMTP connection to send them.
	 * 
	 * @return true if the mail was sent successfully.
	 */
	public boolean sendMail() {

		// Creates the String to print the from and the from values + CRLF's...
		String mailPrint = ("from: " + from + SMTPConnection.CRLF

		+ "to: " + to + SMTPConnection.CRLF + "subject: " + subject + SMTPConnection.CRLF);

		// Retrieve the data contained in the text fields and areas of the GUI
		server = mailFace.getServer();
		from = mailFace.getFrom();
		to = mailFace.getTo();
		subject = mailFace.getSubject();
		body = mailFace.getEscapedBody();

		// Instantiate an SMTPConnection object if one has not already be
		// created
		try {
			smtpConnection = new SMTPConnection(server);
		} catch (IOException e) {

			e.printStackTrace();

		}

		// Send SMTP commands and check the response to each in order to
		// send and email message.

		try {
			if (smtpConnection.send("mail from: <" + from + ">", 250) == false) {

				System.out.println("There is an error with FROM!");

				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();

		}

		// Then do the TO...
		try {
			
			// code 250... like on the board.
			if (smtpConnection.send("rcpt to: <" + to + ">", 250) == false) {

				System.out.println("There is an error with TO!");
				
				return false;

			}
		} catch (IOException e) {

			e.printStackTrace();

		}

		// Try this...
		try {

			
			// Coder 354... must put a space after first string
			if (smtpConnection.send("data " + mailPrint + body + smtpConnection
					+ SMTPConnection.CRLF + ".", 354) == false) {

				System.out.println("There is an error with the BODY!");
				
				return false;

			}
		} catch (IOException e) {

			e.printStackTrace();
		}

		try {

			
			// code = 250
			if (smtpConnection.send("subject: " + subject, 250) == false) {
				
				System.out.println("There is an error with the SUBJECT!");
				
				return false;
			}
		} catch (IOException e) {

			e.printStackTrace();

		}

		// Return true if successful... that is if false isn't returned
		// previously..
		return true;

	} // end sendMail

	/**
	 * Performs any required closing operations and releases resources when the
	 * GUI is closed down.
	 */
	public void close() {
		// If the smtpConnection is not null, close it down.
		if (smtpConnection != null) {
			smtpConnection.close();
		}

	} // end close

	/**
	 * Checks whether the email address is valid. Checks that the address has
	 * only one @-sign and is not the empty string.
	 * 
	 * @return true if the address is valid
	 */
	@SuppressWarnings("unused")
	private boolean isValidAddress(final String address) {

		int atPlace = address.indexOf('@');

		if (address.length() == 0 || atPlace < 1
				|| (address.length() - atPlace) <= 1
				|| atPlace != address.lastIndexOf('@')) {

			return false;
		} else {

			return true;
		}

	} // end isValid

	public static void main(String[] args) {
		new MessageBuilder();

	} // end main

} // end MailSender
