package systemModel;

import java.util.ArrayList;
import java.util.Random;

/**
 * Contains the state and logic for an implementation of the game.
 * 
 * @author Sayantani Ghosh, Piyush Mantri, Dinesh Papineni
 */
public class Game
{
	private Random		rand;
	private final int	size;
	private int[][]		grid;
	private int			score;

	/**
	 * Constructs a game with a grid of the given size, using a default random number generator.
	 * 
	 * @param givenSize
	 *            size of the grid for this game
	 */
	public Game(int givenSize)
	{
		this(givenSize, new Random());
	}

	/**
	 * Constructs a game with a grid of the given size, using the given instance of Random for the random number generator.
	 * 
	 * @param givenSize
	 *            size of the grid for this game
	 * @param givenRandom
	 *            given instance of Random
	 */
	public Game(int givenSize, Random givenRandom)
	{
		size = givenSize;
		grid = new int[size][size];
		rand = givenRandom;
		TilePosition t = generate();
		grid[t.getRow()][t.getCol()] = t.getValue();
		t = generate();
		grid[t.getRow()][t.getCol()] = t.getValue();
	}

	/**
	 * Returns the value in the cell at the given row and column.
	 * 
	 * @param row
	 *            given row
	 * @param col
	 *            given column
	 * @return value in the cell at the given row and column
	 */
	public int getCell(int row, int col)
	{
		return grid[row][col];
	}

	/**
	 * Sets the value of the cell at the given row and column.
	 * 
	 * @param row
	 *            given row
	 * @param col
	 *            given column
	 * @param value
	 *            value to be set
	 */
	public void setCell(int row, int col, int value)
	{
		grid[row][col] = value;
	}

	/**
	 * Returns the size of this game's grid.
	 * 
	 * @return size of the grid
	 */
	public int getSize()
	{
		return size;
	}

	/**
	 * Returns the current score.
	 * 
	 * @return score for this game
	 */
	public int getScore()
	{
		return score;
	}

	/**
	 * Copy a row or column from the grid into a new one-dimensional array.
	 * 
	 * @param rowOrColumn
	 *            index of the row or column
	 * @param dir
	 *            direction from which to begin copying
	 * @return array containing the row or column
	 */
	public int[] copyRowOrColumn(int rowOrColumn, Direction dir)
	{
		int size = grid.length;
		int[] array = new int[size];
		for (int i = 0; i < size; i += 1)
		{
			switch (dir)
			{
				case LEFT:
					array[i] = grid[rowOrColumn][i];
					break;
				case RIGHT:
					array[i] = grid[rowOrColumn][size - i - 1];
					break;
				case UP:
					array[i] = grid[i][rowOrColumn];
					break;
				case DOWN:
					array[i] = grid[size - i - 1][rowOrColumn];
					break;
			}
		}
		return array;
	}

	/**
	 * Updates the grid by copying the given one-dimensional array into a row or column of the grid.
	 * 
	 * @param arr
	 *            the array from which to copy
	 * @param rowOrColumn
	 *            index of the row or column
	 * @param dir
	 *            direction from which to begin copying
	 */
	public void updateRowOrColumn(int[] arr, int rowOrColumn, Direction dir)
	{
		int size = arr.length;
		for (int i = 0; i < size; i++)
		{
			switch (dir)
			{
				case LEFT:
					grid[rowOrColumn][i] = arr[i];
					break;
				case RIGHT:
					grid[rowOrColumn][size - i - 1] = arr[i];
					break;
				case UP:
					grid[i][rowOrColumn] = arr[i];
					break;
				case DOWN:
					grid[size - i - 1][rowOrColumn] = arr[i];
					break;
			}
		}
	}

	/**
	 * Plays one step of the game by collapsing the grid in the given direction.
	 * 
	 * @param dir
	 *            direction in which to collapse the grid
	 * @return Result object containing moves and new tile position
	 */
	public Result collapse(Direction dir)
	{
		Result result = new Result();
		TilePosition newTile = null;
		for (int i = 0; i < size; i += 1)
		{
			int[] temp = copyRowOrColumn(i, dir);
			ArrayList<Move> miniMoves = GameUtil.collapseArray(temp);
			updateRowOrColumn(temp, i, dir);
			for (Move moves : miniMoves)
			{
				if (moves.isMerged())
					score += moves.getValue() * 2;
				moves.setDirection(i, dir);
				result.addMove(moves);
			}
		}
		if (result.getMoves().size() > 0)
		{
			newTile = generate();
			grid[newTile.getRow()][newTile.getCol()] = newTile.getValue();
			result.setNewTile(newTile);
		}
		return result;
	}

	/**
	 * Use this game's instance of Random to generate a new tile.
	 * 
	 * @return a new TilePosition containing the row, column, and value of the selected new tile, or null if the grid has no empty cells
	 */
	public TilePosition generate()
	{
		int chance = rand.nextInt(100);
		int value;
		if (chance >= 0 && chance < 90)
			value = 2;
		else
			value = 4;
		ArrayList<TilePosition> positions = new ArrayList<>();
		for (int row = 0; row < grid.length; row += 1)
			for (int col = 0; col < grid[0].length; col += 1)
				if (grid[row][col] == 0)
					positions.add(new TilePosition(row, col, value));
		if (positions.size() == 0)
			return null;
		return positions.get(rand.nextInt(positions.size()));
	}

	/**
	 * Gets the grid
	 * 
	 * @return grid
	 */
	public int[][] getGrid()
	{
		return grid;
	}

	/**
	 * Sets the grid values in the parameter to the current grid.
	 * 
	 * @param grid
	 *            the grid to be assigned
	 */
	public void setGrid(int[][] grid)
	{
		this.grid = grid;
	}
}
