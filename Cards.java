import javafx.scene.image.ImageView;

public class Cards {
	private String suit;
	private int rank;
	private ImageView img;
	private int points;
	public Cards(String suit, String rank, ImageView img) {
		this.suit = suit;
		this.rank = Integer.parseInt(rank);
		this.img = img;
		this.points = this.CalculatePoints();
	}
	private int CalculatePoints() {
		if(this.suit == "s") {
			return 4*this.rank;
		} else if(this.suit == "c") {
			return this.rank;
		} else if(this.suit == "h") {
			return 3*this.rank;
		} else if(this.suit == "d"){
			return 2*this.rank;
		} else {
			return 0;
		}
	}
	
	public String getRank() {
		if(this.rank == 1) {
			return "Ace";
		} else if(this.rank == 11) {
			return "J";
		} else if(this.rank == 12) {
			return "Q";
		} else if(this.rank == 13) {
			return "K";
		} else {
			return Integer.toString(this.rank);
		}
	}
	public int getNum() {
		return this.rank;
	}
	public int getPoints() {
		return points;
	}
	public String getSuit() {
		if(this.suit == "s") {
			return "Spade";
		} else if(this.suit == "c") {
			return "Club";
		} else if(this.suit == "h") {
			return "Heart";
		} else {
			return "Diamond";
		}
	}
	public ImageView getImage() {
		return this.img;
	}
	public String printCosts() {
		return String.format("|%9s %4s: %3d points.|\n", this.getSuit(), this.getRank(), this.points);
	}
	public String calcDiff(Cards other) {
		String diff = "Difference";
		int d = 0;
		if(other.getPoints() == this.getPoints()) {
			if(other.getNum() > this.getNum()) {
				d = other.getNum() - this.getNum();
			} else {
				d = this.getNum() - other.getNum();
			}
		} else {
			d = other.getPoints()-this.getPoints() < 0? ((-1)*(other.getPoints()-this.getPoints())) : other.getPoints()-this.getPoints();
		}
		return String.format("|%14s: %3d points.|\n", diff, d );
	}
	@Override
	public String toString() {
		return getSuit() + " " + this.getRank();
	}
	
}