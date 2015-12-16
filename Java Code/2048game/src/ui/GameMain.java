package ui;

import java.awt.Dimension;
import java.util.Random;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import systemModel.Game;

/**
 * Main class for the GUI for a 2048 game.
 * 
 * @author Sayantani Ghosh, Piyush Mantri, Dinesh Papineni
 */
public class GameMain
{
	public static JFrame			frame			= new JFrame("2048");
	/**
	 * Size of grid for the game.
	 */
	private static final int		GRID_SIZE		= 4;

	/**
	 * Attempt to animate movement of tiles.
	 */
	private static final boolean	USE_ANIMATION	= true;

	/**
	 * Print lots of output to the console.
	 */
	private static final boolean	VERBOSE			= true;

	/**
	 * Tile size in pixels.
	 */
	public static final int			TILE_SIZE		= 100;

	/**
	 * Font size for displaying score.
	 */
	public static final int			SCORE_FONT		= 24;

	/**
	 * Entry point. Main thread passes control immediately to the Swing event thread.
	 * 
	 * @param args
	 *            not used
	 */
	public static void main(String[] args)
	{
		final Random rand = new Random();
		Runnable r = new Runnable()
		{
			public void run()
			{
				create(GRID_SIZE, rand, USE_ANIMATION, VERBOSE);
			}
		};
		SwingUtilities.invokeLater(r);
	}

	/**
	 * Helper method for instantiating the components.
	 * 
	 * @param gridSize
	 *            grid size
	 * @param rand
	 *            random
	 * @param useAnimation
	 *            animation
	 * @param verbose
	 *            verbose
	 */
	private static void create(int gridSize, Random rand, boolean useAnimation, boolean verbose)
	{
		Game game = new Game(gridSize, rand);
		ScorePanel scorePanel = new ScorePanel();
		GamePanel panel = new GamePanel(game, scorePanel, useAnimation, verbose);
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(panel);
		mainPanel.add(scorePanel);
		frame.getContentPane().add(mainPanel);
		Dimension d = new Dimension(gridSize * GameMain.TILE_SIZE, gridSize * GameMain.TILE_SIZE);
		panel.setPreferredSize(d);
		d = new Dimension(gridSize * GameMain.TILE_SIZE, GameMain.TILE_SIZE);
		scorePanel.setPreferredSize(d);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.grabFocus();
		frame.setVisible(true);
	}
}