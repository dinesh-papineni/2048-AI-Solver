package algorithm;

/**
 * Direction status of preferred move
 * 
 * @author Sayantani Ghosh, Piyush Mantri, Dinesh Papineni
 */
public enum DirectionStatus
{
	/**
	 * Move Up
	 */
	UP(0, "Up"),

	/**
	 * Move Right
	 */
	RIGHT(1, "Right"),

	/**
	 * Move Down
	 */
	DOWN(2, "Down"),

	/**
	 * Move Left
	 */
	LEFT(3, "Left");

	/**
	 * The numeric code of the status
	 */
	private final int		code;

	/**
	 * The description of the status
	 */
	private final String	description;

	/**
	 * Constructor
	 * 
	 * @param code
	 *            code of status
	 * @param description
	 *            description of status
	 */
	private DirectionStatus(final int code, final String description)
	{
		this.code = code;
		this.description = description;
	}

	/**
	 * Returns the status code.
	 * 
	 * @return code
	 */
	public int getCode()
	{
		return code;
	}

	/**
	 * Returns the status description.
	 * 
	 * @return description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Overloads the toString and returns the description of the move.
	 * 
	 * @return description of move
	 */
	@Override
	public String toString()
	{
		return description;
	}
}
