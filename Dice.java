
import java.util.Random;

//Dice class acting as a die for the game

public class Dice {

	// Variables for each die

	private int die1;
	private int die2;

	// Constructor

	public Dice() {

	}

	// Accessor for each die

	public int getDie1() {
		return die1;
	}

	public int getDie2() {
		return die2;
	}

	// Method for the total dice roll

	public int rollDice() {
		Random Generator = new Random();
		this.die1 = Generator.nextInt(6) + 1;
		this.die2 = Generator.nextInt(6) + 1;
		return this.die1 + this.die2;
	}

	// Method to check if we rolled a double

	public boolean isDouble() {
		return this.die1 == this.die2;
	}

	// toString method

	public String toString() {
		String a = Integer.toString(this.die1);
		String b = Integer.toString(this.die2);

		return "rolled Die1: " + a + " Die2: " + b;
	}

	// Check how many doubles are rolled in 3 rolls

	public static int noEnergy(Dice p) {
		p.rollDice();
		boolean a = p.isDouble();
		p.rollDice();
		boolean b = p.isDouble();
		p.rollDice();
		boolean c = p.isDouble();

		if (a & b & c) {
			return 3;
		}

		else if (a && b || a && c || b && c) {
			return 2;
		}

		else if (a || b || c) {
			return 1;
		}

		else {
			return 0;
		}

	}

}
