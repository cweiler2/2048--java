package application;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.*;

public class Frame 
{

	private Game game;
	private int frameHeight = 396; //
	private int frameWidth = 328;
	private int gameBoardSize = 296;
	private int marginSize = 16;
	private Color backgroundColor = new Color(153, 153, 255);
	private JFrame frame;
	private GameBoard gb;
	@SuppressWarnings("rawtypes")
	private Map numberTiles = new Hashtable(); // used to asign integers values to the image of each tile making it easy to link the images to the actual game board
	
	public Frame() throws IOException
	{
		game = new Game();
		frame = new Listener();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setTitle("2048");
		loadNumberTiles();
		gb = new GameBoard();
		frame.setFocusable(true);
		frame.addKeyListener(new Listener());
		
		// North Panel
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridLayout());
		northPanel.setPreferredSize(new Dimension(frameWidth, 82));
		JLabel gameLabel = new JLabel("2048", SwingConstants.CENTER);
		gameLabel.setFont(new Font("Serif", Font.BOLD, 40));
		northPanel.add(gameLabel);
		northPanel.setBackground(backgroundColor);
		
		// West Panel
		JPanel westPanel = new JPanel();
		westPanel.setPreferredSize(new Dimension(marginSize, gameBoardSize));
		westPanel.setBackground(backgroundColor);
		
		// East Panel
		JPanel eastPanel = new JPanel();
		eastPanel.setPreferredSize(new Dimension(marginSize, gameBoardSize));
		eastPanel.setBackground(backgroundColor);
		
		// South Panel
		JPanel southPanel = new JPanel();
		southPanel.setPreferredSize(new Dimension(frameWidth, marginSize));
		southPanel.setBackground(backgroundColor);
		
		// Add  the panels and  the game board to the frame
		
		frame.getContentPane().add(northPanel, BorderLayout.NORTH);
		frame.getContentPane().add(westPanel, BorderLayout.WEST);
		frame.getContentPane().add(eastPanel, BorderLayout.EAST);
		frame.getContentPane().add(southPanel, BorderLayout.SOUTH);
		frame.getContentPane().add(gb, BorderLayout.CENTER);
		
		frame.getContentPane().setPreferredSize(new Dimension(frameWidth, frameHeight));
		frame.pack();
		frame.setVisible(true);
	}
	
	@SuppressWarnings("unchecked")
	public void loadNumberTiles() throws IOException
	{
		// reads in tile images
		BufferedImage tile0 = ImageIO.read(new File("Pictures\\0tile.png"));
		BufferedImage tile2 = ImageIO.read(new File("Pictures\\2tile.png"));
		BufferedImage tile4 = ImageIO.read(new File("Pictures\\4tile.png"));
		BufferedImage tile8 = ImageIO.read(new File("Pictures\\8tile.png"));
		BufferedImage tile16 = ImageIO.read(new File("Pictures\\16tile.png"));
		BufferedImage tile32 = ImageIO.read(new File("Pictures\\32tile.png"));
		BufferedImage tile64 = ImageIO.read(new File("Pictures\\64tile.png"));
		BufferedImage tile128 = ImageIO.read(new File("Pictures\\128tile.png"));
		BufferedImage tile256 = ImageIO.read(new File("Pictures\\256tile.png"));
		BufferedImage tile512 = ImageIO.read(new File("Pictures\\512tile.png"));
		BufferedImage tile1024 = ImageIO.read(new File("Pictures\\1024tile.png"));
		BufferedImage tile2048 = ImageIO.read(new File("Pictures\\2048tile.png"));
 
		// puts images in the hashtable and asigns them to their values
		numberTiles.put(0, tile0);
		numberTiles.put(2, tile2);
		numberTiles.put(4, tile4);
		numberTiles.put(8, tile8);
		numberTiles.put(16, tile16);
		numberTiles.put(32, tile32);
		numberTiles.put(64, tile64);
		numberTiles.put(128, tile128);
		numberTiles.put(256, tile256);
		numberTiles.put(512, tile512);
		numberTiles.put(1024, tile1024);
		numberTiles.put(2048, tile2048);
	}
	
	public class GameBoard extends JPanel
	{
		private static final long serialVersionUID = 1L;
		
		protected void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			g.setColor(new Color(20,20,20));
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			int[][] board = game.getGameBoard();
			
			// nested for loop to run through the gameboard from the game class
			for (int y=1; y<5; y++)
			{
				for (int x=1; x<5; x++)
				{
					int X = (8*x) + (64 * (x-1)); // prints the tiles on the gaemboard cleanly with padding on each tile
					int Y = (8*y) + (64 * (y-1));
					
					int thisNumber = board[y-1][x-1]; // gets value on the gameboard
					BufferedImage thisTile = (BufferedImage) numberTiles.get(thisNumber); // asigns that value to the image of the tile
					g.drawImage(thisTile, X, Y, null); // draws the tile
					if( thisNumber == 8) // if the 2048 tile is drawn, a popup box says you win and exits the game
					{
						JOptionPane.showOptionDialog(null, "You win!", "Congratulations!", JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
						System.exit(0);
					}
				}
			}
		}
	}
	
	public class Listener extends JFrame implements KeyListener
	{
		private static final long serialVersionUID = 1L;
		
		@Override
		public void keyPressed(KeyEvent e) 
		{
			int key = e.getKeyCode();
			if ( key == KeyEvent.VK_UP) // if the user presses the up arrow, push tiles up, add new number, and repaint the board
			{
				game.pushUp();
				game.addNewNumber();
				gb.repaint();
			}
			else if ( key == KeyEvent.VK_DOWN) // if the user presses the up down, push tiles down, add new number, and repaint the board
			{
				game.pushDown();
				game.addNewNumber();
				gb.repaint();
			}
			else if ( key == KeyEvent.VK_LEFT) // if the user presses the left arrow, push tiles left, add new number, and repaint the board
			{
				game.pushLeft();
				game.addNewNumber();
				gb.repaint();
			}
			else if ( key == KeyEvent.VK_RIGHT) // if the user presses the right arrow, push tiles right, add new number, and repaint the board
			{
				game.pushRight();
				game.addNewNumber();
				gb.repaint();
			}
		}
		@Override
		public void keyReleased(KeyEvent e)
		{
			
		}
		@Override
		public void keyTyped(KeyEvent e) 
		{
			
		}
	}
}
