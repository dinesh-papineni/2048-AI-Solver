package algorithm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Estimates the next move.
 * 
 * @author Sayantani Ghosh, Piyush Mantri, Dinesh Papineni
 */
public class NextMove
{
	/**
	 * Player v/s Computer
	 */
	public enum Player
	{
		/**
		 * Computer
		 */
		COMPUTER,
		/**
		 * User
		 */
		USER
	}

	/**
	 * Finds the best next move.
	 * 
	 * @param theBoard
	 *            game board
	 * @param depth
	 *            depth of tree to be searched
	 * @return best direction to be moved
	 * @throws CloneNotSupportedException
	 *             clone not supported
	 */
	public static DirectionStatus findBestMove(Board theBoard, int depth) throws CloneNotSupportedException
	{
		Map<String, Object> result = alphaBetaPruning(theBoard, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, Player.USER);
		return (DirectionStatus) result.get("Direction");
	}

	/**
	 * Finds the best move bay using the Alpha-Beta pruning algorithm.
	 * 
	 * @param theBoard
	 *            game board
	 * @param depth
	 *            depth of tree to be searched
	 * @param alpha
	 *            alpha value
	 * @param beta
	 *            beta value
	 * @param player
	 *            player
	 * @return game tree
	 * @throws CloneNotSupportedException
	 *             clone not supported
	 */
	private static Map<String, Object> alphaBetaPruning(Board theBoard, int depth, int alpha, int beta, Player player) throws CloneNotSupportedException
	{
		Map<String, Object> result = new HashMap<>();
		DirectionStatus bestDirection = null;
		int bestScore;
		if (theBoard.isGameTerminated())
		{
			if (theBoard.hasWon())
				bestScore = Integer.MAX_VALUE;
			else
				bestScore = Math.min(theBoard.getScore(), 1);
		}
		else if (depth == 0)
			bestScore = heuristicScore(theBoard, theBoard.getScore(), theBoard.getNumberOfEmptyCells(), mergeScore(theBoard.getBoardArray()), maxValue(theBoard.getBoardArray()));
		else
		{
			if (player == Player.USER)
			{
				for (DirectionStatus direction : DirectionStatus.values())
				{
					Board newBoard = (Board) theBoard.clone();
					int points = newBoard.move(direction);
					if (points == 0 && newBoard.isEqual(theBoard.getBoardArray(), newBoard.getBoardArray()))
						continue;
					Map<String, Object> currentResult = alphaBetaPruning(newBoard, depth - 1, alpha, beta, Player.COMPUTER);
					int currentScore = ((Number) currentResult.get("Score")).intValue();
					if (currentScore > alpha)
					{
						alpha = currentScore;
						bestDirection = direction;
					}
					if (beta <= alpha)
						break;
				}
				bestScore = alpha;
			}
			else
			{
				List<Integer> moves = theBoard.getEmptyCellIds();
				int[] possibleValues = { 2, 4 };
				int i, j;
				abloop: for (Integer cellId : moves)
				{
					i = cellId / Board.BOARD_SIZE;
					j = cellId % Board.BOARD_SIZE;
					for (int value : possibleValues)
					{
						Board newBoard = (Board) theBoard.clone();
						newBoard.setEmptyCell(i, j, value);
						Map<String, Object> currentResult = alphaBetaPruning(newBoard, depth - 1, alpha, beta, Player.USER);
						int currentScore = ((Number) currentResult.get("Score")).intValue();
						if (currentScore < beta)
							beta = currentScore;
						if (beta <= alpha)
							break abloop;
					}
				}
				bestScore = beta;
				if (moves.isEmpty())
					bestScore = 0;
			}
		}
		result.put("Score", bestScore);
		result.put("Direction", bestDirection);
		return result;
	}

	/**
	 * Max Value Heuristic
	 * 
	 * @param boardArray
	 *            game board
	 * @return max value
	 */
	private static int maxValue(int boardArray[][])
	{
		int max = 0;
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				if (boardArray[i][j] > max)
					max = boardArray[i][j];
		return max;
	}

	/**
	 * Merge score heuristic
	 * 
	 * @param boardArray
	 *            game board
	 * @return merge score
	 */
	private static int mergeScore(int[][] boardArray)
	{
		int mergeScore = 0;
		int size = boardArray.length;
		for (int i = 0; i < size; i++)
		{
			for (int j = 0; j < size; j++)
			{
				try
				{
					if (boardArray[i][j] == boardArray[i][j + 1] && boardArray[i][j] != 0)
						mergeScore++;
					if (boardArray[i][j] == boardArray[i + 1][j] && boardArray[i][j] != 0)
						mergeScore++;
					if (boardArray[i][j] == boardArray[i][j - 1] && boardArray[i][j] != 0)
						mergeScore++;
					if (boardArray[i][j] == boardArray[i - 1][j] && boardArray[i][j] != 0)
						mergeScore++;
					if (i == 0)
					{
						if (boardArray[i][j] == boardArray[size - 1][j] && boardArray[i][j] != 0)
							mergeScore++;
					}
					if (i == size - 1)
					{
						if (boardArray[i][j] == boardArray[0][j] && boardArray[i][j] != 0)
							mergeScore++;
					}
					if (j == 0)
					{
						if (boardArray[i][j] == boardArray[i][size - 1] && boardArray[i][j] != 0)
							mergeScore++;
					}
					if (j == size - 1)
					{
						if (boardArray[i][j] == boardArray[i][0] && boardArray[i][j] != 0)
							mergeScore++;
					}
				}
				catch (ArrayIndexOutOfBoundsException e)
				{

				}
			}
		}
		return mergeScore;
	}

	/**
	 * Estimates a heuristic score by taking into account the real score, the number of empty cells and the clustering score of the board.
	 * 
	 * @param theBoard
	 *            game board
	 * @param actualScore
	 *            actual score
	 * @param numberOfEmptyCells
	 *            number of empty cells
	 * @param mergeScore
	 *            merge score
	 * @param maxValue
	 *            max value
	 * @return heuristic score
	 */
	private static int heuristicScore(Board theBoard, int actualScore, int numberOfEmptyCells, int mergeScore, int maxValue)
	{
		int score = (int) (monotonocity(theBoard.getBoardArray()) + numberOfEmptyCells + mergeScore + maxValue);
		return Math.max(score, Math.min(actualScore, 1));
	}

	/**
	 * Monotonicity Heuristic
	 * 
	 * @param boardArray
	 *            board
	 * @return monotonicity count
	 */
	private static double monotonocity(int[][] boardArray)
	{
		double[] totals = { 0, 0, 0, 0 };
		for (int i = 0; i < 4; i++)
		{
			int current = 0;
			int next = current + 1;
			while (next < 4)
			{
				while (next < 4 && !cellOccupied(i, next, boardArray))
					next++;
				if (next >= 4)
					next--;
				double currentValue, nextValue;
				if (cellOccupied(i, current, boardArray))
					currentValue = Math.log(boardArray[i][current]) / Math.log(2);
				else
					currentValue = 0;
				if (cellOccupied(i, next, boardArray))
					nextValue = Math.log(boardArray[i][next]) / Math.log(2);
				else
					nextValue = 0;
				if (currentValue > nextValue)
					totals[0] += nextValue - currentValue;
				else if (nextValue > currentValue)
					totals[1] += currentValue - nextValue;
				current = next;
				next++;
			}
		}
		for (int i = 0; i < 4; i++)
		{
			int current = 0;
			int next = current + 1;
			while (next < 4)
			{
				while (next < 4 && !cellOccupied(next, i, boardArray))
					next++;
				if (next >= 4)
					next--;
				double currentValue, nextValue;
				if (cellOccupied(current, i, boardArray))
					currentValue = (Math.log(boardArray[current][i]) / Math.log(2));
				else
					currentValue = 0;
				if (cellOccupied(next, i, boardArray))
					nextValue = (Math.log(boardArray[next][i]) / Math.log(2));
				else
					nextValue = 0;
				if (currentValue > nextValue)
					totals[2] += nextValue - currentValue;
				else if (nextValue > currentValue)
					totals[3] += currentValue - nextValue;
				current = next;
				next++;
			}
		}
		return Math.max(totals[0], totals[1]) + Math.max(totals[2], totals[3]);
	}

	/**
	 * Whether a cell is occupied or not
	 * 
	 * @param i
	 *            position
	 * @param j
	 *            position
	 * @param boardArray
	 *            board
	 * @return occupied or not
	 */
	private static boolean cellOccupied(int i, int j, int[][] boardArray)
	{
		if (boardArray[i][j] == 0)
			return false;
		return true;
	}
}