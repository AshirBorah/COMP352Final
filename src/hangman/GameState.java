package hangman;

import java.util.ArrayList;
import java.util.Collections;

public class GameState {
	private ArrayList<Player> scoreBoard;
	private String cword;
	private String myIP;
	private int uScore;

	class Player implements Comparable<Player> {
		String ip;
		private String name;
		private int score;

		public Player(String name, String ip) {
			this.name = name;
			score = 0;
			this.ip = ip;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getScore() {
			return score;
		}

		public void setScore(int score) {
			this.score = score;
		}

		@Override
		public String toString() {
			return "Player [name=" + name + ",   score:" + score + "]";
		}

		@Override
		public int compareTo(Player o) {
			if (this.score - o.getScore() != 0) {
				return this.score - o.getScore();
			} else {
				return this.name.compareTo(o.getName());
			}
		}

	}

	public ArrayList<Player> getScoreBoard() {
		return scoreBoard;
	}

	public String getMyIP() {
		return myIP;
	}

	public GameState(String word, String ip) {
		scoreBoard = new ArrayList<Player>();
		cword = word;
		myIP = ip;
	}

	@SuppressWarnings("unchecked")
	public GameState(String word, ArrayList<Player> scorebd, String ip) {
		scoreBoard = (ArrayList<Player>) scorebd.clone();
		cword = word;
		myIP = ip;
	}

	public void addPlayer(String name, String ip) {
		scoreBoard.add(new Player(name, ip));
	}

	public int findIndex(String ip) {
		for (int i = 0; i < scoreBoard.size(); i++) {
			if (scoreBoard.get(i).ip.equals(ip)) {
				return i;
			}
		}
		return -1;
	}

	public void updateScore(String name, int score) {
		int index = findIndex(name);
		if (index != -1) {
			scoreBoard.get(index).setScore(scoreBoard.get(index).getScore() + score);
		}
		Collections.sort(scoreBoard);
		else{
			System.err.println("Player name found: "+name);
		}
	}
}
