package hangman;

import java.net.*;

/**
 * Multi-threaded web server that runs on port 6789 This port number is
 * pre-determined
 * 
 * @author Daniel Ngo && Ashir Borah
 */

public final class WebServer {

	public static void main(String[] args) throws Exception {

		// Set the port number
		int port = 6789;

		// Establish the listen socket
		ServerSocket socket = new ServerSocket(port);

		// Process HTTP service requests in an infinite loop.
		while (true) {
			// Listen for a TCP connection request
			Socket connection = socket.accept();
			// Construct an object to process the HTTP request message
			HttpRequest request = new HttpRequest(connection);
			// Create a new thread to process the request
			Thread thread = new Thread(request);
			// Start the thread.
			thread.start();
		}
	}
}
