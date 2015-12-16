package ui;

/**
 * Encapsulates a row and column.
 * 
 * @author Sayantani Ghosh, Piyush Mantri, Dinesh Papineni
 */
public class Position
{
	/**
	 * Row position of tile
	 */
	int	row;
	/**
	 * Column position of tile
	 */
	int	col;

	/**
	 * Constructor
	 * 
	 * @param givenRow
	 *            row
	 * @param givenCol
	 *            column
	 */
	public Position(int givenRow, int givenCol)
	{
		row = givenRow;
		col = givenCol;
	}

	/**
	 * Returns row position
	 * 
	 * @return row
	 */
	public int getRow()
	{
		return row;
	}

	/**
	 * Returns column position
	 * 
	 * @return column
	 */
	public int getCol()
	{
		return col;
	}

	/**
	 * Converts row column positions to string for printing.
	 */
	public String toString()
	{
		return "(" + row + ", " + col + ")";
	}
}