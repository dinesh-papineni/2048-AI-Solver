package systemModel;

/**
 * A Move object describes the motion of one tile (or two tiles in the case of a merge move) in the game.
 * 
 * @author Sayantani Ghosh, Piyush Mantri, Dinesh Papineni
 */
public class Move
{
	/**
	 * Old index of the first tile.
	 */
	private final int		oldIndex;

	/**
	 * Old position of the second tile, in case of a merge.
	 */
	private final int		oldIndex2;

	/**
	 * New position of the tile or tiles.
	 */
	private final int		newIndex;

	/**
	 * Indicates whether this is a merge move or not.
	 */
	private final boolean	merged;

	/**
	 * Current value in the cell or cells to be moved.
	 */
	private final int		value;

	/**
	 * Row or column in the grid (depending on direction).
	 */
	private int				rowOrColumn;

	/**
	 * Direction in the grid.
	 */
	private Direction		dir;

	/**
	 * Constructs a single-tile move from oldPos to newPos.
	 * 
	 * @param oldIndex
	 *            old index of the tile within an array
	 * @param newIndex
	 *            new index of the tile within an array
	 * @param value
	 *            current value of the tile
	 */
	public Move(int oldIndex, int newIndex, int value)
	{
		this.oldIndex = oldIndex;
		this.newIndex = newIndex;
		this.oldIndex2 = -1;
		this.value = value;
		merged = false;
		dir = null;
		rowOrColumn = -1;
	}

	/**
	 * Constructs a merge move from oldIndex and oldIndex2 to newIndex.
	 * 
	 * @param oldIndex
	 *            old index of the tile within an array
	 * @param oldIndex2
	 *            old index of second tile within an array
	 * @param newIndex
	 *            new index of the tile within an array
	 * @param value
	 *            current value of the tile
	 */
	public Move(int oldIndex, int oldIndex2, int newIndex, int value)
	{
		this.oldIndex = oldIndex;
		this.newIndex = newIndex;
		this.oldIndex2 = oldIndex2;
		this.value = value;
		merged = true;
		dir = null;
		rowOrColumn = -1;

	}

	/**
	 * Sets a direction and row/column index for interpreting this move within a grid.
	 * 
	 * @param givenRowOrColumn
	 *            row or column index
	 * @param givenDirection
	 *            direction to set
	 */
	public void setDirection(int givenRowOrColumn, Direction givenDirection)
	{
		dir = givenDirection;
		rowOrColumn = givenRowOrColumn;
	}

	/**
	 * Returns the old index of the first (or only) tile represented by this move.
	 * 
	 * @return index of first tile
	 */
	public int getOldIndex()
	{
		return oldIndex;
	}

	/**
	 * Returns the old index of the second tile represented by this move, in case of a merge. Returns -1 if this is not a merge move.
	 * 
	 * @return index of second tile, or -1 if this is not a merge
	 */
	public int getOldIndex2()
	{
		return oldIndex2;
	}

	/**
	 * Returns the new index of the tile or tiles represented by this move.
	 * 
	 * @return new index for move
	 */
	public int getNewIndex()
	{
		return newIndex;
	}

	/**
	 * Determines whether this is a merge move or a single tile move.
	 * 
	 * @return true if this is a merge move, false otherwise
	 */
	public boolean isMerged()
	{
		return merged;
	}

	/**
	 * Returns a direction for interpreting this move in a 2D grid.
	 * 
	 * @return direction for this move, or null if none has been set
	 */
	public Direction getDirection()
	{
		return dir;
	}

	/**
	 * Returns a row or column index for interpreting this move in a 2D grid.
	 * 
	 * @return row or column index, or -1 if none has been set
	 */
	public int getRowOrColumn()
	{
		return rowOrColumn;
	}

	/**
	 * Returns the current (old) value of the tile or tiles represented by this move.
	 * 
	 * @return value of tiles in this move
	 */
	public int getValue()
	{
		return value;
	}

	/**
	 * Determines whether this Move object is equal to the given object.
	 * 
	 * @return true if the given object is a Move and all attributes are the same as those in this Move
	 */
	public boolean equals(Object obj)
	{
		if (obj == null || obj.getClass() != this.getClass())
			return false;
		Move other = (Move) obj;
		return (oldIndex == other.oldIndex && oldIndex2 == other.oldIndex2 && newIndex == other.newIndex && value == other.value && rowOrColumn == other.rowOrColumn && dir == other.dir);
	}

	/**
	 * Returns a string description of this move.
	 * 
	 * @return a string description of this move
	 */
	public String toString()
	{
		String rowAndDirection = "";
		if (rowOrColumn >= 0 && dir != null)
		{
			if (dir == Direction.UP || dir == Direction.DOWN)
			{
				rowAndDirection = " (column " + rowOrColumn + " " + dir + ")";
			}
			else if (dir == Direction.LEFT || dir == Direction.RIGHT)
			{
				rowAndDirection = " (row " + rowOrColumn + " " + dir + ")";
			}
		}
		if (merged)
			return "Merge " + oldIndex + " and " + oldIndex2 + " to " + newIndex + rowAndDirection;
		else
			return "Move " + oldIndex + " to " + newIndex + rowAndDirection;
	}
}