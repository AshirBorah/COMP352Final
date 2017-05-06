package hangman;

import java.io.BufferedReader;
import java.io.*;
import javax.sound.sampled.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Client class that handles client side of the hangman game
 * Display the current state of the hangman and number of blank spaces left
 * that still needed to be guessed.
 * @author Ashir Borah and Daniel Ngo
 *
 */
public class Client {

	private static Character guess;
	private static int state;
	private static int blank;

	public static void main(String[] args) throws IOException {
		// networking code
		// get the ip of the server
		String host = args[0];
		// get the port number 
		int port = Integer.parseInt(args[1]);
		Socket clientSocket = new Socket(host, port);
		// get the output stream of the socket
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

		System.out.println("Lets play HANGMAN\n\n");
		System.out.println("Please enter your name:");
		Scanner in = new Scanner(System.in);
		String name = in.nextLine();
		// Send the player's name to the server
		outToServer.writeBytes("n " + name + "\r\n");
		boolean cont = true;
		while (cont) {
			// get the input stream from server of the socket
			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			// get the word from the server and print out the current score board
			String word = processRequest(inFromServer);
			clientSocket.close();
			// Initialize the ArrayList which holds character that has already been used.
			ArrayList<Character> used = new ArrayList<Character>();
			blank = word.length();
			state = 1;
			char[] wordChar = new char[blank]; 
			hide(wordChar);// word hidden
			while (blank != 0 && state != 7) {
				display(wordChar); // display the current state of the hidden word
				do {
					guess = new Character(in.next().charAt(0));
					if (!Character.isLetter(guess)) { // if the user doenst input a letter
						System.out.println("Please input a character!");
					} else if (used.contains(guess)) { // if the user input a letter that has already been used
						System.out.println(
								"You already used " + guess + ".\n We are being nice and allowing you to re-enter");
					}
				} while (!Character.isLetter(guess) || used.contains(guess));
				guess = Character.toLowerCase(guess);
				used.add(new Character(guess)); // add the letter to list of used-letter
				reveal(wordChar, guess, word); // reveal the guessed letter if it's in the correct word
				System.out.println("Blank: " + blank); // print out the number of blank spaces left
				hangman(state); // show the current state of the hangman.
				System.out.println("Letters used: "); 
				System.out.println(used.toString());// show the list of used letters
				System.out.print("\n\n");
			}
			if (state == 7) { // if the hangman is fully displayed
				hangman(state); // display the hangman 
				System.out.println("Game over" + "\nThe word was: " + word); // Game is over, print out the correct word
			} else { // if the word has been successfully guessed
				System.out.println(word); // print out the word
				hangman(state); // display the final state of the hangman
				System.out.println("You win"); // player wins
			}
			int score = 7 - state; // initialize the score of each player depending on the current state of the hangman 
			if (score > 0) { // If the player wins
				try { // play the winning-applause sound file
					File yourFile = new File("applause-2.wav");
					AudioInputStream stream;
					AudioFormat format;
					DataLine.Info info;
					Clip clip;
					stream = AudioSystem.getAudioInputStream(yourFile);
					format = stream.getFormat();
					info = new DataLine.Info(Clip.class, format);
					clip = (Clip) AudioSystem.getLine(info);
					clip.open(stream);
					clip.start();
				} catch (Exception e) {
					System.err.println(e);
				}
			} else { // if the player loses
				try { // play the losing sound file
					File yourFile = new File("maybe-next-time.wav");
					AudioInputStream stream;
					AudioFormat format;
					DataLine.Info info;
					Clip clip;
					stream = AudioSystem.getAudioInputStream(yourFile);
					format = stream.getFormat();
					info = new DataLine.Info(Clip.class, format);
					clip = (Clip) AudioSystem.getLine(info);
					clip.open(stream);
					clip.start();
				} catch (Exception e) {
					System.err.println(e);
				}
			}
			// Asking if the player want to play the game again
			System.out.println("Continue?: (Y/N)");
			cont = in.next().trim().equalsIgnoreCase("y");
			System.out.println(cont); 
			// creates new socket that connects to the server
			clientSocket = new Socket(host, port);
			// Get the output stream to the server
			outToServer = new DataOutputStream(clientSocket.getOutputStream());
			outToServer.writeBytes(score + "" + "\r\n"); // update the score to the server
		}
		in.close();
		outToServer.close();
		clientSocket.close();
		System.out.println("Thank you for playing");
	}

	// Get the word and the current scoreboard from the server
	// return the picked word
	private static String processRequest(BufferedReader inFromServer) throws IOException {

		if (inFromServer == null) {
			throw new IllegalStateException();
		}
		String word = inFromServer.readLine();
		String score = "";
		while (score != null) {
			System.out.println(score);
			score = inFromServer.readLine();
		}
		return word;
	}

	/**
	 * Hide the word, replace each character with a "_"
	 * @param word the word that needed to be replaced
	 */
	public static void hide(char word[]) {
		for (int i = 0; i < word.length; i++) {
			word[i] = '_';
		}
	}

	/**
	 * Display the current state of the hidden word
	 * @param word the hidden word broken into array of characters
	 */
	public static void display(char word[]) {
		for (int i = 0; i < word.length; i++) {
			System.out.print(word[i]);
		}
		System.out.println();
	}

	/**
	 * Reveal the character if it is present in the correct word
	 * @param word the current state of the hidden word
	 * @param guess the letter that the player has guessed
	 * @param cWord the correct word
	 */
	public static void reveal(char word[], Character guess, String cWord) {
		int flag = 0;
		for (int i = 0; i < word.length; i++) {
			if (word[i] == '_') {
				if (guess == cWord.charAt(i)) {
					word[i] = guess;
					blank--;
					flag++;
				}
			}
		}
		if (flag == 0) {
			state++;
		}
	}

	/**
	 * Displays the hangman
	 * @param state current state of the game
	 */
	public static void hangman(int state) {
		if (state == 1) {
			System.out.println();
			System.out.println(" |---   ");
			System.out.println(" |   |  ");
			System.out.println(" |      ");
			System.out.println(" |      ");
			System.out.println(" |      ");
			System.out.println("/ \\    ");
		} else if (state == 2) {
			System.out.println();
			System.out.println(" |---   ");
			System.out.println(" |   |  ");
			System.out.println(" |   o  ");
			System.out.println(" |      ");
			System.out.println(" |      ");
			System.out.println("/ \\    ");
		} else if (state == 3) {
			System.out.println();
			System.out.println(" |---   ");
			System.out.println(" |   |  ");
			System.out.println(" |   o  ");
			System.out.println(" |   |  ");
			System.out.println(" |      ");
			System.out.println("/ \\    ");
		} else if (state == 4) {
			System.out.println();
			System.out.println(" |---   ");
			System.out.println(" |   |  ");
			System.out.println(" |   o  ");
			System.out.println(" |  /|  ");
			System.out.println(" |      ");
			System.out.println("/ \\    ");
		} else if (state == 5) {
			System.out.println();
			System.out.println(" |---   ");
			System.out.println(" |   |  ");
			System.out.println(" |   o  ");
			System.out.println(" |  /|\\");
			System.out.println(" |      ");
			System.out.println("/ \\    ");
		} else if (state == 6) {
			System.out.println();
			System.out.println(" |---   ");
			System.out.println(" |   |  ");
			System.out.println(" |   o  ");
			System.out.println(" |  /|\\");
			System.out.println(" |  /   ");
			System.out.println("/ \\    ");
		} else if (state == 7) {
			System.out.println();
			System.out.println(" |---   ");
			System.out.println(" |   |  ");
			System.out.println(" |   o  ");
			System.out.println(" |  /|\\");
			System.out.println(" |  / \\");
			System.out.println("/ \\    ");
		}
	}
}
