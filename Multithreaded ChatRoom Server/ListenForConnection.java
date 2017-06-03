import java.net.ServerSocket;


public class ListenForConnection {

	
	public static void main(String[] args) throws Exception
	{		
		ServerSocket serverSocket = new ServerSocket(32150);
		
		new ChatThread( "ListenForConnection", serverSocket.accept()  );
		
	}

}
