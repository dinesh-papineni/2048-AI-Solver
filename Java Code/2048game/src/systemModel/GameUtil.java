package systemModel;

import java.util.ArrayList;

/**
 * Utility class containing the basic logic for performing moves in a game.
 * 
 * @author Sayantani Ghosh, Piyush Mantri, Dinesh Papineni
 */
public class GameUtil
{
	/**
	 * Returns the index of the first nonempty cell that is on or after the given index <code>start</code>, or -1 if there is none.
	 * 
	 * @param arr
	 *            given array
	 * @param start
	 *            index at which to start looking
	 * @return index of the first nonempty cell, or -1 if none is found
	 */
	public static int findNextNonemptyCell(int[] arr, int start)
	{
		if (start >= arr.length)
			return -1;
		for (int i = start; i < arr.length; i++)
		{
			if (arr[i] != 0)
				return i;
		}
		return -1;
	}

	/**
	 * Given an array and a starting index, finds a move that would merge or move a tile to that index, if such a move exists.
	 * 
	 * @param arr
	 *            array in which to search for move
	 * @param index
	 *            index for destination of move
	 * @return Move object describing the move, or null if there is no move
	 */
	public static Move findNextMove(int[] arr, int index)
	{
		int nextCell = findNextNonemptyCell(arr, index + 1);
		if (nextCell == -1)
			return null;
		if (arr[index] != 0)
		{
			if (arr[nextCell] == arr[index])
				return new Move(nextCell, index, index, arr[nextCell]);
		}
		else
		{
			int nextNextCell = findNextNonemptyCell(arr, nextCell + 1);
			if (nextNextCell != -1 && arr[nextCell] == arr[nextNextCell])
				return new Move(nextCell, nextNextCell, index, arr[nextCell]);
			else
				return new Move(nextCell, index, arr[nextCell]);
		}
		return null;
	}

	/**
	 * Updates the array according to the given Move.
	 * 
	 * @param arr
	 *            given array to be modified
	 * @param move
	 *            the move to be applied to the array
	 */
	public static void applyOneMove(int[] arr, Move move)
	{
		if (move.isMerged())
		{
			arr[move.getOldIndex()] = 0;
			arr[move.getOldIndex2()] = 0;
			arr[move.getNewIndex()] = move.getValue() * 2;
		}
		else
		{
			arr[move.getOldIndex()] = 0;
			arr[move.getNewIndex()] = move.getValue();
		}
	}

	/**
	 * Collapses the array to the left by performing a sequence of moves, and returns a list of moves that were performed.
	 * 
	 * @param arr
	 *            array to be collapsed
	 * @return list of all moves performed in the collapse
	 */
	public static ArrayList<Move> collapseArray(int[] arr)
	{
		ArrayList<Move> moves = new ArrayList<>();
		for (int i = 0; i < arr.length; i++)
		{
			Move nextMove = findNextMove(arr, i);
			if (nextMove != null)
			{
				moves.add(nextMove);
				applyOneMove(arr, nextMove);
			}
		}
		return moves;
	}
}
