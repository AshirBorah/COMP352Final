package hangman;

import java.util.ArrayList;
import java.util.Collections;

public class GameState {
	ArrayList<Player> scoreBoard;

	class Player implements Comparable<Player> {
		private String name;
		private int score;

		public Player(String name) {
			this.name = name;
			score = 0;
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

	public GameState() {
		scoreBoard = new ArrayList<Player>();
	}

	public void addPlayer(String name) {
		scoreBoard.add(new Player(name));
	}

	public int findIndex(String name) {
		for (int i = 0; i < scoreBoard.size(); i++) {
			if (scoreBoard.get(i).getName().equals(name)) {
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
	}
}
