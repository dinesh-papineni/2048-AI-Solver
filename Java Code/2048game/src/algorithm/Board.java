package algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Game board.
 * 
 * @author Sayantani Ghosh, Piyush Mantri, Dinesh Papineni
 */
public class Board implements Cloneable
{
	/**
	 * The size of the board
	 */
	public static final int	BOARD_SIZE			= 4;
	/**
	 * The maximum combination in which the game terminates
	 */
	public static final int	TARGET_POINTS		= 2048;
	/**
	 * The theoretical minimum win score until the target point is reached
	 */
	public static final int	MINIMUM_WIN_SCORE	= 18432;
	/**
	 * The score so far
	 */
	private int				score				= 0;
	/**
	 * The board values
	 */
	private int[][]			boardArray;
	/**
	 * Random Generator which is used in the creation of random cells
	 */
	private final Random	randomGenerator;
	/**
	 * Number of empty cells
	 */
	private Integer			emptyCells			= null;

	/**
	 * Constructor that initializes the board randomly.
	 */
	public Board()
	{
		boardArray = new int[BOARD_SIZE][BOARD_SIZE];
		randomGenerator = new Random(System.currentTimeMillis());
		addRandomCell();
		addRandomCell();
	}

	/**
	 * Constructor that initializes the board based on the given board array.
	 * 
	 * @param newBoard
	 *            given board array.
	 */
	public Board(int newBoard[][])
	{
		randomGenerator = new Random(System.currentTimeMillis());
		boardArray = newBoard;
	}

	/**
	 * Deep clone
	 * 
	 * @return cloned object
	 * @throws CloneNotSupportedException
	 *             clone not supported
	 */
	@Override
	public Object clone() throws CloneNotSupportedException
	{
		Board copy = (Board) super.clone();
		copy.boardArray = clone2dArray(boardArray);
		return copy;
	}

	/**
	 * Returns the score
	 * 
	 * @return score
	 */
	public int getScore()
	{
		return score;
	}

	/**
	 * Returns the board array
	 * 
	 * @return board array
	 */
	public int[][] getBoardArray()
	{
		return clone2dArray(boardArray);
	}

	/**
	 * Returns the RandomGenerator field
	 * 
	 * @return random generator
	 */
	public Random getRandomGenerator()
	{
		return randomGenerator;
	}

	/**
	 * Performs one move (up, down, left or right).
	 * 
	 * @param direction
	 *            move direction
	 * @return score
	 */
	public int move(DirectionStatus direction)
	{
		int points = 0;
		if (direction == DirectionStatus.UP)
			rotateLeft();
		else if (direction == DirectionStatus.RIGHT)
		{
			rotateLeft();
			rotateLeft();
		}
		else if (direction == DirectionStatus.DOWN)
			rotateRight();
		for (int i = 0; i < BOARD_SIZE; ++i)
		{
			int lastMergePosition = 0;
			for (int j = 1; j < BOARD_SIZE; ++j)
			{
				if (boardArray[i][j] == 0)
					continue;
				int previousPosition = j - 1;
				while (previousPosition > lastMergePosition && boardArray[i][previousPosition] == 0)
					--previousPosition;
				if (previousPosition == j)
				{
					// can't move this
				}
				else if (boardArray[i][previousPosition] == 0)
				{
					boardArray[i][previousPosition] = boardArray[i][j];
					boardArray[i][j] = 0;
				}
				else if (boardArray[i][previousPosition] == boardArray[i][j])
				{
					boardArray[i][previousPosition] *= 2;
					boardArray[i][j] = 0;
					points += boardArray[i][previousPosition];
					lastMergePosition = previousPosition + 1;
				}
				else if (boardArray[i][previousPosition] != boardArray[i][j] && previousPosition + 1 != j)
				{
					boardArray[i][previousPosition + 1] = boardArray[i][j];
					boardArray[i][j] = 0;
				}
			}
		}
		score += points;
		if (direction == DirectionStatus.UP)
			rotateRight();
		else if (direction == DirectionStatus.RIGHT)
		{
			rotateRight();
			rotateRight();
		}
		else if (direction == DirectionStatus.DOWN)
			rotateLeft();
		return points;
	}

	/**
	 * Returns the id's of the empty cells, numbered by row.
	 * 
	 * @return ids of empty cells
	 */
	public List<Integer> getEmptyCellIds()
	{
		List<Integer> cellList = new ArrayList<>();
		for (int i = 0; i < BOARD_SIZE; ++i)
			for (int j = 0; j < BOARD_SIZE; ++j)
				if (boardArray[i][j] == 0)
					cellList.add(BOARD_SIZE * i + j);
		return cellList;
	}

	/**
	 * Counts the number of empty cells
	 * 
	 * @return number of empty cells
	 */
	public int getNumberOfEmptyCells()
	{
		if (emptyCells == null)
			emptyCells = getEmptyCellIds().size();
		return emptyCells;
	}

	/**
	 * Checks if any of the cells in the board has value equal or larger than the target.
	 * 
	 * @return if any of the cells in the board has value equal or larger than the target
	 */
	public boolean hasWon()
	{
		if (score < MINIMUM_WIN_SCORE)
			return false;
		for (int i = 0; i < BOARD_SIZE; ++i)
			for (int j = 0; j < BOARD_SIZE; ++j)
				if (boardArray[i][j] >= TARGET_POINTS)
					return true;
		return false;
	}

	/**
	 * Checks whether the game is terminated
	 * 
	 * @return whether game is terminated.
	 * @throws java.lang.CloneNotSupportedException
	 *             clone not supported
	 */
	public boolean isGameTerminated() throws CloneNotSupportedException
	{
		boolean terminated = false;
		if (hasWon() == true)
			terminated = true;
		else
		{
			if (getNumberOfEmptyCells() == 0)
			{
				Board copyBoard = (Board) this.clone();
				if (copyBoard.move(DirectionStatus.UP) == 0 && copyBoard.move(DirectionStatus.RIGHT) == 0 && copyBoard.move(DirectionStatus.DOWN) == 0 && copyBoard.move(DirectionStatus.LEFT) == 0)
					terminated = true;
			}
		}
		return terminated;
	}

	/**
	 * Performs an Up, Right, Down or Left move
	 * 
	 * @param direction
	 *            move direction
	 * @return action status
	 * @throws java.lang.CloneNotSupportedException
	 *             clone not supported
	 */
	public ActionStatus action(DirectionStatus direction) throws CloneNotSupportedException
	{
		ActionStatus result = ActionStatus.CONTINUE;
		int[][] currBoardArray = getBoardArray();
		int newPoints = move(direction);
		int[][] newBoardArray = getBoardArray();
		boolean newCellAdded = false;
		if (!isEqual(currBoardArray, newBoardArray))
			newCellAdded = addRandomCell();
		if (newPoints == 0 && newCellAdded == false)
		{
			if (isGameTerminated())
				result = ActionStatus.NO_MORE_MOVES;
			else
				result = ActionStatus.INVALID_MOVE;
		}
		else
		{
			if (newPoints >= TARGET_POINTS)
				result = ActionStatus.WIN;
			else
			{
				if (isGameTerminated())
					result = ActionStatus.NO_MORE_MOVES;
			}
		}
		return result;
	}

	/**
	 * Sets the value to an empty cell.
	 * 
	 * @param i
	 *            cell location
	 * @param j
	 *            cell location
	 * @param value
	 *            cell value
	 */
	public void setEmptyCell(int i, int j, int value)
	{
		if (boardArray[i][j] == 0)
		{
			boardArray[i][j] = value;
			emptyCells = null;
		}
	}

	/**
	 * Rotates the board on the left
	 */
	private void rotateLeft()
	{
		int[][] rotatedBoard = new int[BOARD_SIZE][BOARD_SIZE];
		for (int i = 0; i < BOARD_SIZE; ++i)
			for (int j = 0; j < BOARD_SIZE; ++j)
				rotatedBoard[BOARD_SIZE - j - 1][i] = boardArray[i][j];
		boardArray = rotatedBoard;
	}

	/**
	 * Rotates the board on the right
	 */
	private void rotateRight()
	{
		int[][] rotatedBoard = new int[BOARD_SIZE][BOARD_SIZE];
		for (int i = 0; i < BOARD_SIZE; ++i)
			for (int j = 0; j < BOARD_SIZE; ++j)
				rotatedBoard[i][j] = boardArray[BOARD_SIZE - j - 1][i];
		boardArray = rotatedBoard;
	}

	/**
	 * Creates a new Random Cell
	 * 
	 * @return cell added or not
	 */
	private boolean addRandomCell()
	{
		List<Integer> emptyCells = getEmptyCellIds();
		int listSize = emptyCells.size();
		if (listSize == 0)
			return false;
		int randomCellId = emptyCells.get(randomGenerator.nextInt(listSize));
		int randomValue = (randomGenerator.nextDouble() < 0.9) ? 2 : 4;
		int i = randomCellId / BOARD_SIZE;
		int j = randomCellId % BOARD_SIZE;
		setEmptyCell(i, j, randomValue);
		return true;
	}

	/**
	 * Clones a 2D array
	 * 
	 * @param original
	 *            original board
	 * @return cloned board
	 */
	private int[][] clone2dArray(int[][] original)
	{
		int[][] copy = new int[original.length][];
		for (int i = 0; i < original.length; ++i)
			copy[i] = original[i].clone();
		return copy;
	}

	/**
	 * Checks whether the two input boards are same.
	 * 
	 * @param currentBoardArray
	 *            current board
	 * @param newBoardArray
	 *            new board
	 * @return same or not
	 */
	public boolean isEqual(int[][] currentBoardArray, int[][] newBoardArray)
	{
		boolean equal = true;
		for (int i = 0; i < currentBoardArray.length; i++)
		{
			for (int j = 0; j < currentBoardArray.length; j++)
			{
				if (currentBoardArray[i][j] != newBoardArray[i][j])
				{
					equal = false;
					return equal;
				}
			}
		}
		return equal;
	}
}