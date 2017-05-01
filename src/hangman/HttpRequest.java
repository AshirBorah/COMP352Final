package hangman;

import java.io.*;
import java.util.*;
import java.net.*;

/**
 * Handling HTTP Request for the multi-threaded web server
 * 
 * @author Daniel Ngo && Ashir Borah
 */

final class HttpRequest implements Runnable {
	final static String CRLF = "\r\n";
	Socket socket;
	static final boolean verbose = true;
	static HashMap<String, Player> scoreB;
	Random rand;

	// Constructor
	public HttpRequest(Socket socket) throws Exception {
		this.socket = socket;
		scoreB = new HashMap<String, Player>();
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
		// Get a reference to the socket's input and output streams
		InputStream is = socket.getInputStream();
		DataOutputStream os = new DataOutputStream(socket.getOutputStream());

		// ObjectInputStream input = new
		// ObjectInputStream(socket.getInputStream());
		// GameState gs = (GameState) input.readObject();
		// scoreB = gs.getScoreBoard();
		// String ip=gs.getMyIP();
		// String word = "Harry"; //use selectword() here

		// Set up input stream filters
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		// Get the request line of the HTTP request message
		String requestLine = br.readLine();
		String tokens[] = requestLine.split(" ");
		// request format: IP ScoreUpdate Continue
		String ip = socket.getInetAddress().toString();
		int scoreUpdate = Integer.parseInt(tokens[1]);
		String name = tokens[0];

		// Display the request line
		if (verbose) {
			System.out.println();
			System.out.println(requestLine);
		}
		if (scoreB.containsKey(ip)) {
			Player temp = scoreB.get(ip);
			temp.updateScore(scoreUpdate);
			scoreB.put(ip, temp);
		} else {
			Player temp = new Player(name, ip, scoreUpdate);
			temp.setName(name);
			scoreB.put(ip, temp);
		}
		// Get and display the header lines.

		// Construct the response message
		String response = selectWord(rand) + "\n" + getScoreBoard();

		// Send the response
		os.writeBytes(response);
		os.writeBytes(CRLF);
		os.close();
		br.close();
		socket.close(); // close socket
	}

	public static String selectWord(Random rand) throws FileNotFoundException {
		File file = new File("src/WordList.txt");
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

	public String getScoreBoard() {
		StringBuilder sb = new StringBuilder();
		ArrayList<Player> pList = new ArrayList<Player>();
		for (String pl : scoreB.keySet()) {
			pList.add(scoreB.get(pl));
		}
		Collections.sort(pList);
		for (int i = 0; i < pList.size(); i++) {
			sb.append(pList.get(i).toString() + "\n");
		}
		return sb.toString();
	}
}