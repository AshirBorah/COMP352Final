package hangman;

public class Player implements Comparable<Player> {
	String ip;
	private String name;
	private int score;

	public Player(String name, String ip) {
		this.name = name;
		score = 0;
		this.ip = ip;
	}

	public Player(String name, String ip, int score) {
		this.name = name;
		this.score = score;
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

	public void updateScore(int score) {
		this.score += score;
	}

	@Override
	public String toString() {
		return "Player [name=" + name + ",   score:" + score + "]";
	}

	public String sendToServer() {
		return name + "; " + score + "; " + ip;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ip.hashCode() + name.hashCode();
		return result;
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