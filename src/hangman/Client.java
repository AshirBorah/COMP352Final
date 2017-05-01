package hangman;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {

	private static Character guess;
	private static int state;
	private static int blank;

	public static void main(String[] args) throws IOException {
		// networking code
		Socket clientSocket = new Socket("Localhost", 6801);
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

		System.out.println("Lets play HANGMAN\n\n");
		System.out.println("Please enter your name:");
		Scanner in = new Scanner(System.in);
		String name = in.nextLine();

		// use server to get the word instead
		outToServer.writeBytes(name + " " + 0 + "\r\n");
		boolean cont = true;
		while (cont) {
			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			String word = processRequest(inFromServer);
			ArrayList<Character> used = new ArrayList<Character>();
			System.out.println("Got word:" + word);
			blank = word.length();
			state = 5;
			char[] wordChar = new char[blank];
			hide(wordChar);// word hidden
			while (blank != 0 && state != 11) {
				clearConsole();
				display(wordChar); // to edit
				do {
					guess = new Character(in.next().charAt(0));
					if (!Character.isLetter(guess)) {
						System.out.println("Please input a character!");
					} else if (used.contains(guess)) {
						System.out.println(
								"You already used " + guess + ".\n We are being nice and allowing you to re-enter");
					}
				} while (!Character.isLetter(guess) || used.contains(guess));
				guess = Character.toLowerCase(guess);
				used.add(new Character(guess));
				reveal(wordChar, guess, word);

				System.out.println("Blank: " + blank);
				hangman(state);
				System.out.println("Letters used: ");
				System.out.println(used.toString());// edit
				System.out.print("\n\n");
			}
			if (state == 11) {
				hangman(state);
				System.out.println("Game over" + "\nThe word was: " + word);
			} else {
				System.out.println(word);
				hangman(state);
				System.out.println("You win");
			}
			int score = 12 - state;
			outToServer = new DataOutputStream(clientSocket.getOutputStream());
			outToServer.writeBytes(name + " " + score + "\n");
			System.out.println("Continue?: (Y/N)");
			cont = in.next().trim().equalsIgnoreCase("y");
		}
		in.close();
		clientSocket.close();
	}

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

	// creates the array word
	public static void hide(char word[]) {
		for (int i = 0; i < word.length; i++) {
			word[i] = '_';
		}
	}

	public static void display(char word[]) {
		for (int i = 0; i < word.length; i++) {
			System.out.print(word[i]);
		}
		System.out.println();
	}

	// reveals the letters if present
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

	// displays the hangman
	public static void hangman(int state) {
		// if (state == 0) {
		// System.out.println();
		// System.out.println(" ");
		// System.out.println(" ");
		// System.out.println(" ");
		// System.out.println(" ");
		// System.out.println(" ");
		// System.out.println(" ");
		// } else if (state == 1) {
		// System.out.println();
		// System.out.println(" ");
		// System.out.println(" ");
		// System.out.println(" ");
		// System.out.println(" ");
		// System.out.println(" ");
		// System.out.println("/ ");
		// } else if (state == 2) {
		// System.out.println();
		// System.out.println(" ");
		// System.out.println(" ");
		// System.out.println(" ");
		// System.out.println(" ");
		// System.out.println(" ");
		// System.out.println("/ \\ ");
		// } else if (state == 3) {
		// System.out.println();
		// System.out.println(" | ");
		// System.out.println(" | ");
		// System.out.println(" | ");
		// System.out.println(" | ");
		// System.out.println(" | ");
		// System.out.println("/ \\ ");
		// } else if (state == 4) {
		// System.out.println();
		// System.out.println(" |--- ");
		// System.out.println(" | ");
		// System.out.println(" | ");
		// System.out.println(" | ");
		// System.out.println(" | ");
		// System.out.println("/ \\ ");
		// } else if (state == 5) {
		if (state == 5) {
			System.out.println();
			System.out.println(" |---   ");
			System.out.println(" |   |  ");
			System.out.println(" |      ");
			System.out.println(" |      ");
			System.out.println(" |      ");
			System.out.println("/ \\    ");
		} else if (state == 6) {
			System.out.println();
			System.out.println(" |---   ");
			System.out.println(" |   |  ");
			System.out.println(" |   o  ");
			System.out.println(" |      ");
			System.out.println(" |      ");
			System.out.println("/ \\    ");
		} else if (state == 7) {
			System.out.println();
			System.out.println(" |---   ");
			System.out.println(" |   |  ");
			System.out.println(" |   o  ");
			System.out.println(" |   |  ");
			System.out.println(" |      ");
			System.out.println("/ \\    ");
		} else if (state == 8) {
			System.out.println();
			System.out.println(" |---   ");
			System.out.println(" |   |  ");
			System.out.println(" |   o  ");
			System.out.println(" |  /|  ");
			System.out.println(" |      ");
			System.out.println("/ \\    ");
		} else if (state == 9) {
			System.out.println();
			System.out.println(" |---   ");
			System.out.println(" |   |  ");
			System.out.println(" |   o  ");
			System.out.println(" |  /|\\");
			System.out.println(" |      ");
			System.out.println("/ \\    ");
		} else if (state == 10) {
			System.out.println();
			System.out.println(" |---   ");
			System.out.println(" |   |  ");
			System.out.println(" |   o  ");
			System.out.println(" |  /|\\");
			System.out.println(" |  /   ");
			System.out.println("/ \\    ");
		} else if (state == 11) {
			System.out.println();
			System.out.println(" |---   ");
			System.out.println(" |   |  ");
			System.out.println(" |   o  ");
			System.out.println(" |  /|\\");
			System.out.println(" |  / \\");
			System.out.println("/ \\    ");
		}
	}

	// selects a random word for the game

	public static void clearConsole() throws IOException {
		// Runtime.getRuntime().exec("clear");
	}
}
