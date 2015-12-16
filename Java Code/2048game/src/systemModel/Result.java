package systemModel;

import java.util.ArrayList;

/**
 * A Result object encapsulates the results of a <code>collapse()</code> operation on an instance of the game.
 * 
 * @author Sayantani Ghosh, Piyush Mantri, Dinesh Papineni
 */
public class Result
{
	/**
	 * List of moves.
	 */
	private ArrayList<Move>	moves;

	/**
	 * New tile position.
	 */
	private TilePosition	newTile;

	/**
	 * Constructs a Result with an empty list of moves and a null TilePosition.
	 */
	public Result()
	{
		moves = new ArrayList<Move>();
		newTile = null;
	}

	/**
	 * Adds a moves to this result's list of moves.
	 * 
	 * @param m
	 *            move to be added
	 */
	public void addMove(Move m)
	{
		moves.add(m);
	}

	/**
	 * Adds a list of moves to this result's list of moves.
	 * 
	 * @param list
	 *            moves to be added
	 */
	public void addMoves(ArrayList<Move> list)
	{
		moves.addAll(list);
	}

	/**
	 * Sets this result's new tile position.
	 * 
	 * @param givenTilePosition
	 *            new tile to add to this result
	 */
	public void setNewTile(TilePosition givenTilePosition)
	{
		newTile = givenTilePosition;
	}

	/**
	 * Returns a reference to this result's list of moves, possibly empty.
	 * 
	 * @return list of moves
	 */
	public ArrayList<Move> getMoves()
	{
		return moves;
	}

	/**
	 * Returns this result's new tile position.
	 * 
	 * @return tile position, or null if none has been set
	 */
	public TilePosition getNewTile()
	{
		return newTile;
	}
}
