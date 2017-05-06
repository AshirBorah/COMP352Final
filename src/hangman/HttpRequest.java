package hangman;

import java.io.*;
import java.util.*;
import java.net.*;

/**
 * Handling request for the multi-threaded server
 * 
 * @author Daniel Ngo && Ashir Borah
 */

final class HttpRequest implements Runnable {
	final static String CRLF = "\r\n";
	Socket socket;
	static final boolean verbose = true;
	HashMap<String, Integer> scoreB;
	HashMap<String, String> nameDB;
	Random rand;

	/**
	 * Constructor for the HTTP Request class
	 * @param socket the socket that is currently holding communication between server and clients
	 * @param scoreB2 the HashMap that holds score each each player
	 * @param nameDB2 the HashMap that holds name of each player
	 * @throws Exception 
	 */
	public HttpRequest(Socket socket, HashMap<String, Integer> scoreB2, HashMap<String, String> nameDB2)
			throws Exception {
		this.socket = socket;
		this.scoreB = scoreB2;
		this.nameDB = nameDB2;
		rand = new Random();
	}

	// Implement the run() method of the Runnable interface.
	public void run() {
		try {
			processRequest();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void processRequest() throws Exception {
		System.out.println("Processing request");
		// Get a reference to the socket's input stream
		InputStream is = socket.getInputStream();
		// Set up input stream filters
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		// Get the request line of the HTTP request message
		String requestLine = br.readLine();
		String[] tokens = requestLine.split(" ");
		System.out.println(requestLine);
		// request format: IP ScoreUpdate Continue
		System.out.println(getScoreBoard());
		if (requestLine.charAt(0) == 'n') {
			scoreB.put(socket.getInetAddress().toString(), 0);
			nameDB.put(socket.getInetAddress().toString(), tokens[1]);
		} else {
			System.out.println("Inside cond");
			int score = scoreB.get(socket.getInetAddress().toString());
			System.out.println(score);
			scoreB.put(socket.getInetAddress().toString(), score + Integer.parseInt(requestLine));
		}

		// Construct the response message
		String response = selectWord(rand) + "\n" + getScoreBoard();

		// Send the response
		System.out.println("Sending: " + response);
		DataOutputStream os = new DataOutputStream(socket.getOutputStream());
		os.writeBytes(response + CRLF);
		os.close();
	}

	/**
	 * Randomly select the word from a dictionary for the game
	 * @param rand random generator to select the word
	 * @return the word that was selected from the dictionary
	 * @throws FileNotFoundException if the dictionary is not found
	 */
	public static String selectWord(Random rand) throws FileNotFoundException {
		File file = new File("WordList.txt");
		Scanner reader = new Scanner(file);
		int numWord = reader.nextInt();// number of words in the file
		int pickNum = rand.nextInt(numWord) + 1;
		for (int i = 0; i < pickNum; i++) {
			reader.nextLine();
		}
		String word = reader.nextLine();
		reader.close();
		return word;
	}

	/**
	 * Get the current scoreboard that contains name of each player and their score
	 * @return the current scoreboard
	 */
	public String getScoreBoard() {
		StringBuilder sb = new StringBuilder();
		ArrayList<String> pList = new ArrayList<String>();
		for (String pl : scoreB.keySet()) {
			pList.add(nameDB.get(pl) + " " + scoreB.get(pl) + "\n");
		}
		Collections.sort(pList);
		for (int i = 0; i < pList.size(); i++) {
			sb.append(pList.get(i).toString() + "\n");
		}
		return sb.toString();
	}
}