

import java.util.Scanner;
import java.util.Random;

//turn by turn board game where 2 player roll a die and try to get to the end of the board first 

public class LetUsPlay {

	public static void main(String[] args) {

		System.out.println("************************************************************\n"
				+ "\tWELCOME this 3D Board game!\n"
				+ "************************************************************\n");

		Scanner keyboard = new Scanner(System.in);
		Random Generator = new Random();

		// Calling objects for the players, the dice and the board

		Player player[] = new Player[2];
		Dice dice = new Dice();
		Board board = null;

		System.out.print("The default game board has 3 levels and each level has a 4x4 board.\n"
				+ "You can use this board size or change the size\n\t" + "0 to use the board size\n\t"
				+ "-1 to enter your own size\n" + "What do you want to do? ");

		// Giving choice between using the default board and a custom one

		for (boolean Break = false; Break == false;) {

			int input = keyboard.nextInt();

			// Zero for default board

			if (input == 0) {
				board = new Board();
				break;
			}

			// -1 for custom board with chosen size and levels

			else if (input == -1) {

				System.out.print("How many levels would you like? (minimum size 3, max 10) ");

				for (; Break == false;) {

					int level = keyboard.nextInt();

					if (level > 2 && level < 11) {

						System.out.print("\nWhat size do you want the nxn boards on each level to be?\n"
								+ "Minimum size is 3x3, max is 10x10\n" + "Enter the value of n: ");

						for (; Break == false;) {

							int size = keyboard.nextInt();

							if (size > 2 && size < 11) {

								board = new Board(level, size);
								Break = true;
							}

							else {
								System.out.println("Sorry but " + size + " is not a legal choice");
							}

						}

					}

					else {
						System.out.println("Sorry but " + level + " is not a legal choice.");
					}

				}
			}

			// Error message for miss input

			else {
				System.out.println("Sorry but " + input + " is not a legal choice.");
			}

		}

		// Print chosen board

		System.out.println("Your 3D board has been set up and looks like this:\n");
		board.toString();

		// Choose players name

		System.out.print("\nWhat is player 0's name (one word only): ");
		player[0] = new Player(keyboard.next());
		System.out.print("What is player 1's name (one word only): ");
		player[1] = new Player(keyboard.next());

		// Randomly generate which player starts

		int start = Generator.nextInt(2);
		String first = "";
		if (start == 0) {
			first = player[0].getName();
		}

		else if (start == 1) {
			first = player[1].getName();
		}

		// Game has begun

		System.out.println("\nThe game has started " + first + " goes first\n===================================\n");
		for (boolean win = false; win == false;) {

			// Have the two player play there turn

			for (int i = 0; i < 2; i++) {

				System.out.println("It's " + player[start].getName() + "'s turn");

				// If energy is <= 0, energy is adjusted or player lose his turn

				if (player[start].getEnergy() <= 0) {
					int multiplier = Dice.noEnergy(dice);
					player[start].setEnergy(2 * multiplier);

					if (multiplier == 0) {
						System.out.println(
								player[start].getName() + " has no energy and failed his rolls. Turn skipped\n");
						start = Player.Switch(start);
						continue;
					}

					else {
						System.out.println(player[start].getName() + " has no energy and rolls " + multiplier
								+ " double(s). Your energy went up by " + (2 * multiplier));
					}
				}

				// Roll dice on x players turn

				int roll = dice.rollDice();
				System.out.println(player[start].getName() + " " + dice.toString());

				// Gain extra energy if player rolls a double

				if (dice.isDouble()) {
					System.out.println("You rolled a double " + dice.getDie1() + ". Your energy went up by 2 units.");
					player[start].setEnergy(player[start].getEnergy() + 2);
				}

				// Player movement on x and y axis with adjustments to only get valid values

				int x = player[start].getX() + (player[start].getY() + roll) / board.getSize();
				int y = (player[start].getY() + roll) % board.getSize();

				if (x >= board.getSize()) {
					x = x % board.getSize();
					player[start].setLevel(player[start].getLevel() + 1);

					if (player[start].getLevel() >= board.getLevel()) {
						System.out.println("Out of bound. Lose 2 energy.");
						player[start].setEnergy(player[start].getEnergy() - 2);
						player[start].setLevel(player[start].getLevel() - 1);
						x = player[start].getX();
						y = player[start].getY();

					}

				}

				// Player's coordinates after dice rolled

				Player copy = new Player(player[start]);

				player[start].setX(x);
				player[start].setY(y);

				// Make player go backward if he's on second to last case

				if ((x == board.getSize() - 1) && (y == board.getSize() - 2)
						&& (player[start].getLevel() == board.getLevel() - 1)) {
					player[start].setX(x);
					player[start].setY(board.getSize() - 3);

					System.out.println("You landed on the second to last space. Move Backward");
				}

				// If players land on the same spot one of the player will have his location
				// changed

				if (player[start].equals(player[0]) && player[start].equals(player[1])) {

					System.out.println("Landed on other player's location\n" + "What do you want to do?\n"
							+ "\t0-Challenge\n" + "\t1-Forfeit");

					// Player can choose to Challenge or Forfeit the spot

					for (;;) {
						int option = keyboard.nextInt();

						// Player loses a level and 2 energy

						if (option == 1) {
							if (player[start].getLevel() == 0) {
								player[start].setX(0);
								player[start].setY(0);
							} else {
								player[start].setLevel(player[start].getLevel() - 1);
							}
							player[start].setEnergy(player[start].getEnergy() - 2);
							System.out.println("You lost 2 energy and moved backward");
							break;

						} else if (option == 0) {
							Player placeholder = new Player(copy);
							int chall_die = Generator.nextInt();
							if (chall_die < 6) {
								player[start].moveTo(copy);
								System.out.println("You lost the challenge. Lose half of your energy");
								player[start].setEnergy(player[start].getEnergy() / 2);

							} else {
								System.out.println("Bravo! You won the challenge");
								if (start == 0) {
									player[start].moveTo(player[1]);
									player[1].moveTo(placeholder);
									player[1].setEnergy(player[1].getEnergy() / 2);
									player[start].setEnergy(player[start].getEnergy() + player[1].getEnergy());
								}

								else if (start == 1) {
									player[start].moveTo(player[0]);
									player[0].moveTo(placeholder);
									player[0].setEnergy(player[0].getEnergy() / 2);
									player[start].setEnergy(player[start].getEnergy() + player[0].getEnergy());

								}
							}
							break;
						} else {
							System.out.println("Sorry but " + option + " is not a legal choice.");
						}
					}

				}

				// Energy adjusted depending on player coordinates
				player[start].setEnergy(player[start].getEnergy()
						+ board.getEnergyAdj(player[start].getLevel(), player[start].getX(), player[start].getY()));

				System.out.println("You energy is adjusted by "
						+ board.getEnergyAdj(player[start].getLevel(), player[start].getX(), player[start].getY())
						+ " for landing at (" + player[start].getX() + "," + player[start].getY() + ")\n");

				// If player is on last case. Player wins and loop stops

				if (player[start].won(board)) {
					i = 2;
					win = true;
					break;
				}

				// Change turn to other player

				start = Player.Switch(start);
			}

			// Energy resets to 0 if negative

			if (player[0].getEnergy() < 0) {
				player[0].setEnergy(0);
			}

			if (player[1].getEnergy() < 0) {
				player[1].setEnergy(0);
			}

			// Round conclusion

			System.out.println("At the end of this round:");
			System.out.println("\t" + player[0].toString() + "\n\t" + player[1].toString());

			// If player wins, game ends

			if (player[start].won(board)) {
				System.out.println("\n" + player[start].getName() + " won the game\n" + "Game Over");
				break;
			}

			// Make user continue the game by pressing any button

			System.out.print("Enter any key to continue the game....");
			String next = keyboard.next();
			System.out.println("---------------------------------------------------------------------------------\n");
			if (next.length() > 0) {
				continue;
			}
		}

		keyboard.close();
	}

}
