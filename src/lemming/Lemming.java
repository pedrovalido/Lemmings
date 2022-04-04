package lemming;

public class Lemming {
	private char tribe;
	private int points;

	public Lemming(char tribe, int points) {
		this.tribe = tribe;
		this.points = points;
	}
	
	public char getTribe() {
		return tribe;
	}
	
	public long getPoints() {
		return points;
	}
}