package ui;

/**
 * A NormalizedMove is a description of a tile move in the game described in terms of actual row and column positions.
 * 
 * @author Sayantani Ghosh, Piyush Mantri, Dinesh Papineni
 */
public class NormalizedMove
{
	/**
	 * Position of tile from where it will be moved
	 */
	Position	oldPos;
	/**
	 * Position of tile from where it will be moved
	 */
	Position	oldPos2;
	/**
	 * Position of tile to where it will be moved
	 */
	Position	newPos;
	/**
	 * Merging possible or not
	 */
	boolean		merged;
	/**
	 * Value of tile to be merged
	 */
	int			value;

	/**
	 * Constructor for move if merge does not happen.
	 * 
	 * @param oldPos
	 *            old position of tile
	 * @param newPos
	 *            new position of tile
	 * @param value
	 *            value of tile
	 */
	public NormalizedMove(Position oldPos, Position newPos, int value)
	{
		this.oldPos = oldPos;
		this.newPos = newPos;
		this.value = value;
		merged = false;
	}

	/**
	 * Constructor for move if merge happens.
	 * 
	 * @param oldPos
	 *            old position of tile 1
	 * @param oldPos2
	 *            old position of tile 2
	 * @param newPos
	 *            new position of merged tile
	 * @param value
	 *            value of the tile to be merged
	 */
	public NormalizedMove(Position oldPos, Position oldPos2, Position newPos, int value)
	{
		this.oldPos = oldPos;
		this.newPos = newPos;
		this.oldPos2 = oldPos2;
		this.value = value;
		merged = true;
	}

	/**
	 * Returns position of first tile to be moved.
	 * 
	 * @return position of tile
	 */
	public Position getOldPosition()
	{
		return oldPos;
	}

	/**
	 * Returns position of second tile to be moved.
	 * 
	 * @return position of tile
	 */
	public Position getOldPosition2()
	{
		return oldPos2;
	}

	/**
	 * Returns new position of merged tile.
	 * 
	 * @return position of merged tile
	 */
	public Position getNewPosition()
	{
		return newPos;
	}

	/**
	 * Returns value of tile
	 * 
	 * @return value of tile
	 */
	public int getValue()
	{
		return value;
	}

	/**
	 * Converts movement description to string for printing
	 */
	public String toString()
	{
		if (merged)
		{
			return "---> Merging " + value + "'s in " + oldPos + " and " + oldPos2 + " to " + newPos;
		}
		else
		{
			return "---> Moving " + value + " in " + oldPos + " to " + newPos;
		}

	}
}