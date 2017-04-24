package hangman;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.SynchronousQueue;

public class Client {

	private static Character guess;
	private static int state;
	private static int blank;

	public static void main(String[] args) throws IOException {
		System.out.println("Lets play HANGMAN\n\n");
		Scanner in = new Scanner(System.in);
		Random rand = new Random();
		ArrayList<Character> used = new ArrayList<Character>();
		// use client to get the word instead
		String word = selectWord(rand).toLowerCase();
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
			System.out.print("\n\n\n\n\n\n");
		}
		if (state == 11) {
			hangman(state);
			System.out.println("Game over" + "\nThe word was: " + word);
		} else {
			System.out.println(word);
			hangman(state);
			System.out.println("You win");
		}
		in.close();
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
//		if (state == 0) {
//			System.out.println();
//			System.out.println("        ");
//			System.out.println("        ");
//			System.out.println("        ");
//			System.out.println("        ");
//			System.out.println("        ");
//			System.out.println("        ");
//		} else if (state == 1) {
//			System.out.println();
//			System.out.println("        ");
//			System.out.println("        ");
//			System.out.println("        ");
//			System.out.println("        ");
//			System.out.println("        ");
//			System.out.println("/       ");
//		} else if (state == 2) {
//			System.out.println();
//			System.out.println("        ");
//			System.out.println("        ");
//			System.out.println("        ");
//			System.out.println("        ");
//			System.out.println("        ");
//			System.out.println("/ \\    ");
//		} else if (state == 3) {
//			System.out.println();
//			System.out.println(" |      ");
//			System.out.println(" |      ");
//			System.out.println(" |      ");
//			System.out.println(" |      ");
//			System.out.println(" |      ");
//			System.out.println("/ \\    ");
//		} else if (state == 4) {
//			System.out.println();
//			System.out.println(" |---   ");
//			System.out.println(" |      ");
//			System.out.println(" |      ");
//			System.out.println(" |      ");
//			System.out.println(" |      ");
//			System.out.println("/ \\    ");
//		} else if (state == 5) {
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

	public static void clearConsole() throws IOException {
//		Runtime.getRuntime().exec("clear");
	}
}
