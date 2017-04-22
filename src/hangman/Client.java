package hangman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.SynchronousQueue;

public class Client {

	public static Character guess;
	static int state;
	static int blank;

	public static void main(String[] args)
	{
		System.out.println("Lets play HANGMAN\n\n");
		Scanner in=new Scanner(System.in);
		Random rand=new Random();
		ArrayList<Character> used=new ArrayList<Character>();
		//use client to get the word instead
		String word=selectWord(rand);
		int blank=word.length();
		char[] wordChar = word.toCharArray();
		hide(wordChar);
		while (blank!=0 && state!=11)
		{
			display(word); // to edit

			do{
				guess=new Character(in.next().charAt(0));
				if(Character.isLetter(guess)){
					System.out.println("Please input a character!");
				}
				else if(used.contains(guess)){
					System.out.println("You already used "+guess+".\n We are being nice and allowing you to re-enter");
				}
			}while(Character.isLetter(guess)||used.contains(guess));
			guess=Character.toLowerCase(guess);
			used.add(new Character(guess));
			reveal (wordChar, guess);

			System.out.println();
			hangman(state);
			System.out.println("Letters used: ");
			display(charUsed); // edit
			System.out.print("\n\n\n\n\n\n");
		}
		if (state==11)
		{        hangman(state);
		System.out.println("Game over"+"\nThe word was: "+word);
		}
		else {
			System.out.println(word);
			hangman(state);
			System.out.println("You win");
		}
	}

	// creates the array word
	public static void hide(char word[]) {
		for (int i = 0; i < word.length; i++) {
			word[i] = '_';
		}
	}

	// reveals the letters if present
	public static void reveal(char word[], Character guess) {
		int flag = 0;
		for (int i = 0; i < word.length; i++) {
			if (word[i] == '_') {
				if (guess == cword[i]) {
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
		if (state == 0) { 
			System.out.println();
			System.out.println("        ");
			System.out.println("        ");
			System.out.println("        ");
			System.out.println("        ");
			System.out.println("        ");
			System.out.println("        ");
		} else if (state == 1) {			
			System.out.println();
			System.out.println("        ");
			System.out.println("        ");
			System.out.println("        ");
			System.out.println("        ");
			System.out.println("        ");
			System.out.println("/       ");
		} else if (state == 2) {
			System.out.println();
			System.out.println("        ");
			System.out.println("        ");
			System.out.println("        ");
			System.out.println("        ");
			System.out.println("        ");
			System.out.println("/ \\    ");
		} else if (state == 3) {
			System.out.println();
			System.out.println(" |      ");
			System.out.println(" |      ");
			System.out.println(" |      ");
			System.out.println(" |      ");
			System.out.println(" |      ");
			System.out.println("/ \\    ");
		} else if (state == 4) {
			System.out.println();
			System.out.println(" |---   ");
			System.out.println(" |      ");
			System.out.println(" |      ");
			System.out.println(" |      ");
			System.out.println(" |      ");
			System.out.println("/ \\    ");
		} else if (state == 5) {
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

}
