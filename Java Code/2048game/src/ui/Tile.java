package ui;

/**
 * Represents a position and value on a grid that can be animated from an old position to a new position.
 * 
 * @author Sayantani Ghosh, Piyush Mantri, Dinesh Papineni
 */
public class Tile
{
	/**
	 * Tile value
	 */
	private int		currentValue;
	/**
	 * Tile value
	 */
	private int		value;
	/**
	 * Final pixel location
	 */
	private int		x;
	/**
	 * Final pixel location
	 */
	private int		y;
	/**
	 * Whether it is moving
	 */
	private boolean	movingX;
	/**
	 * Whether it is moving
	 */
	private boolean	movingY;
	/**
	 * In case it is moving
	 */
	private double	currentX;
	/**
	 * In case it is moving
	 */
	private double	currentY;
	/**
	 * How much to move each frame
	 */
	private double	incrementX;
	/**
	 * How much to move each frame
	 */
	private double	incrementY;

	/**
	 * Constructor
	 * 
	 * @param x
	 *            position x
	 * @param y
	 *            position y
	 * @param value
	 *            value of tile
	 */
	public Tile(int x, int y, int value)
	{
		movingX = false;
		movingY = false;
		this.x = x * GameMain.TILE_SIZE;
		this.y = y * GameMain.TILE_SIZE;
		this.value = value;
		this.currentX = this.x;
		this.currentY = this.y;
		this.currentValue = value;
	}

	/**
	 * New tiles after moving
	 * 
	 * @param newX
	 *            new position x
	 * @param newY
	 *            new position y
	 * @param newValue
	 *            new tile value
	 * @param frames
	 *            number of frames
	 */
	public void setNew(int newX, int newY, int newValue, int frames)
	{
		x = newX * GameMain.TILE_SIZE;
		y = newY * GameMain.TILE_SIZE;
		value = newValue;
		incrementX = (x - currentX) / frames;
		incrementY = (y - currentY) / frames;
		movingX = true;
		movingY = true;
	}

	/**
	 * Current x value
	 * 
	 * @return current x value
	 */
	public int getCurrentX()
	{
		return (int) Math.round(currentX);
	}

	/**
	 * Current y value
	 * 
	 * @return current x value
	 */
	public int getCurrentY()
	{
		return (int) Math.round(currentY);
	}

	/**
	 * One step in the game
	 */
	public void step()
	{
		if (movingX)
		{
			currentX = currentX + incrementX;
			if (Math.abs(currentX - x) < incrementX)
			{
				currentX = x;
				movingX = false;
			}
		}
		if (movingY)
		{
			currentY = currentY + incrementY;
			if (Math.abs(currentY - y) < incrementY)
			{
				currentY = y;
				movingY = false;
			}
		}
		if (!movingX && !movingY)
			currentValue = value;
	}

	/**
	 * Stop animation if game over.
	 */
	public void finish()
	{
		movingX = false;
		movingY = false;
		currentX = x;
		currentY = y;
		currentValue = value;
	}

	/**
	 * Returns whether a tile is moving
	 * 
	 * @return moving or not
	 */
	public boolean moving()
	{
		return movingX || movingY;
	}

	/**
	 * Returns current tile value
	 * 
	 * @return current tile value
	 */
	public int getCurrentValue()
	{
		return currentValue;
	}
}
