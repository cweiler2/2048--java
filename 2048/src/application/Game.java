package application;

import java.util.*;

public class Game 
{
	private int[][] gameBoard;
	private Random r = new Random();
	
	public Game() // creates a new 4x4 array for the game board and adds 2 new numbers
	{
		gameBoard = new int[4][4];
		addNewNumber();
		addNewNumber();
	}
	
	public int[][] getGameBoard()
	{
		return gameBoard;
	}
	public void printGameBoard()
	{
		// used this function for testing purposes
	}
	
	public void addNewNumber()
	{
		// finds all of the empty spaces on the game board and adds the x and y values to separate array lists
		ArrayList<Integer> emptySpacesX = new ArrayList<Integer>();
		ArrayList<Integer> emptySpacesY = new ArrayList<Integer>();
		for(int x=0; x<4; x++)
		{
			for(int y=0; y<4; y++)
			{
				if (gameBoard[x][y] == 0)
				{
					emptySpacesX.add(new Integer(x));
					emptySpacesY.add(new Integer(y));
				}
			}
		}
		
		int choiceX = r.nextInt(emptySpacesX.size()); // gets a random x value from the empty spaces
		int numberChooser = r.nextInt(10);
		int newNumber;
		if(numberChooser == 0) // if the random integer is 0 (10% chance) the new number will be 4 otherwise its 2
		{
			newNumber = 4;
		}
		else
		{
			newNumber = 2;
		}
		
		// gets the random value from the empty spaces for x and y then adds the (x,y) coordinate to the game board
		// choiceX is used to find the empty space in the y coordinate also because they were added to the array lists as pairs and need to be at the same spot 
		int X = emptySpacesX.get(choiceX);
		int Y = emptySpacesY.get(choiceX);
		gameBoard[X][Y] = newNumber;
	}
	
	public void pushUp() // pushes the tiles down
	{
		// nested for loop to loop through the game board
		for (int y=0; y<4; y++)
		{
			boolean[] alreadyCombined = {false, false, false, false}; // used to make sure 2 values aren't combined in one move
			for(int x=1; x<4; x++) // uses X to loop through the top row not x so start at x=1 not x=0, top row dosent move up
			{
				if (gameBoard[x][y] != 0) // if the spot isnt equal to 0
				{
					int value = gameBoard[x][y]; // asign that value to a variable
					int X = x-1;
					while ((X >= 0) && (gameBoard[X][y] == 0)) // while the 
						{
							X--;
						}
					if (X == -1) // if tile is moved to the top of the board
						{
							gameBoard[0][y] = value; // put value to the tiles new location
							gameBoard[x][y] = 0; // set its previous spot to 0
						}
					else if (gameBoard[X][y] != value) // if the tiles can't combine
					{
						gameBoard[x][y] = 0; // set the tiles previous loaction to 0
						gameBoard[X+1][y] = value; // put the tile one spot before the tile it can't combine with
					}
					else
					{
						if (alreadyCombined[X]) // if two tiles already combined at a position
						{
							gameBoard[x][y] = 0; // set the tiles previous postion to 0
							gameBoard[X+1][y] = value; // move the tile one spot before the new tile that was just combined
						}
						else // if two tiles were not combined at this spot on the gameboard yet
						{
							gameBoard[x][y] = 0; // set the tiles previous spot to 0
							gameBoard[X][y] *= 2; // combine it with the tile above it set double its value
							alreadyCombined[X] = true; // set already combined to true so another tile can't combine with tihs tile in the same move
						}
					}
				}
			}
		}
	}
	
	public void pushDown()
	{
		// same as push up function except altered to match with the direction, same thing for push left and right
		for (int y=0; y<4; y++)
		{
			boolean[] alreadyCombined = {false, false, false, false};
			for(int x=2; x>-1; x--)
			{
				if (gameBoard[x][y] != 0)
				{
					int value = gameBoard[x][y];
					int X = x+1;
					while ((X <= 3) && (gameBoard[X][y] == 0))
						{
							X++;
						}
					if (X == 4)
						{
							gameBoard[3][y] = value;
							gameBoard[x][y] = 0;
						}
					else if (gameBoard[X][y] != value)
					{
						gameBoard[x][y] = 0;
						gameBoard[X-1][y] = value;
					}
					else
					{
						if (alreadyCombined[X])
						{
							gameBoard[x][y] = 0;
							gameBoard[X-1][y] = value;
						}
						else
						{
							gameBoard[x][y] = 0;
							gameBoard[X][y] *= 2;
							alreadyCombined[X] = true;
						}
					}
				}
			}
		}
	}
	
	public void pushLeft()
	{
		for (int x=0; x<4; x++)
		{
			boolean[] alreadyCombined = {false, false, false, false};
			for(int y=1; y<4; y++)
			{
				if (gameBoard[x][y] != 0)
				{
					int value = gameBoard[x][y];
					int Y = y-1;
					while ((Y >= 0) && (gameBoard[x][Y] == 0))
						{
							Y--;
						}
					if (Y == -1)
						{
							gameBoard[x][0] = value;
							gameBoard[x][y] = 0;
						}
					else if (gameBoard[x][Y] != value)
					{
						gameBoard[x][y] = 0;
						gameBoard[x][Y+1] = value;
					}
					else
					{
						if (alreadyCombined[Y])
						{
							gameBoard[x][y] = 0;
							gameBoard[x][Y+1] = value;
						}
						else
						{
							gameBoard[x][y] = 0;
							gameBoard[x][Y] *= 2;
							alreadyCombined[Y] = true;
						}
					}
				}
			}
		}
	}
	
	public void pushRight()
	{
		for (int x=0; x<4; x++)
		{
			boolean[] alreadyCombined = {false, false, false, false};
			for(int y=2; y>-1; y--)
			{
				if (gameBoard[x][y] != 0)
				{
					int value = gameBoard[x][y];
					int Y = y+1;
					while ((Y <= 3) && (gameBoard[x][Y] == 0))
						{
							Y++;
						}
					if (Y == 4)
						{
							gameBoard[x][3] = value;
							gameBoard[x][y] = 0;
						}
					else if (gameBoard[x][Y] != value)
					{
						gameBoard[x][y] = 0;
						gameBoard[x][Y-1] = value;
					}
					else
					{
						if (alreadyCombined[Y])
						{
							gameBoard[x][y] = 0;
							gameBoard[x][Y-1] = value;
						}
						else
						{
							gameBoard[x][y] = 0;
							gameBoard[x][Y] *= 2;
							alreadyCombined[Y] = true;
						}
					}
				}
			}
		}
	}
}
