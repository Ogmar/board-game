
//Player Class holding all player information in the game

public class Player {

	// Player instance variables

	private String Name;
	private int level;
	private int x;
	private int y;
	private int energy;

	// Constructors

	public Player() {
		this.Name = "";
		this.setLevel(0);
		this.x = 0;
		this.y = 0;
		this.energy = 5;
	}

	public Player(String Name) {
		this.Name = Name;
		this.x = 0;
		this.y = 0;
		this.energy = 10;
	}

	public Player(int a, int b, int c) {
		this.energy = 10;
		this.Name = "";
	}

	public Player(Player object) {
		this.Name = object.Name;
		this.x = object.x;
		this.y = object.y;
		this.energy = object.energy;
		this.level = object.level;
	}

	// Mutators/Accessor for each instance variable

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getName() {
		return Name;
	}

	public void setName(String Name) {
		this.Name = Name;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	// Method to transfer coordinates of one player to the other

	public void moveTo(Player p) {
		this.x = p.x;
		this.y = p.y;
		this.level = p.level;
	}

	// Method to check if player is on last case

	public boolean won(Board b) {
		return (this.x == (b.getSize() - 1) && this.y == (b.getSize() - 1) && this.level == (b.getLevel() - 1));
	}

	// Method to verify if players are on the same case

	public boolean equals(Player p) {
		return (this.x == p.x && this.y == p.y && this.level == p.level);

	}

	// toString method

	public String toString() {
		return this.Name + " is on level " + level + " at location (" + x + "," + y + ") and has " + energy
				+ " units of energy";
	}

	// Switches between 0 and 1 to substitute players turns

	public static int Switch(int start) {

		if (start == 0) {
			return 1;
		}

		else {
			return 0;
		}
	}

}
