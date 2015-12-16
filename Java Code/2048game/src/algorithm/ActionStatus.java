package algorithm;

/**
 * Action Status.
 * 
 * @author Sayantani Ghosh, Piyush Mantri, Dinesh Papineni
 */
public enum ActionStatus
{
	/**
	 * Successful move, the game continues.
	 */
	CONTINUE(0, "Successful move, the game continues."),

	/**
	 * Game completed successfully.
	 */
	WIN(1, "You won, the game ended!"),

	/**
	 * No more moves, end of game.
	 */
	NO_MORE_MOVES(2, "No more moves, the game ended!"),

	/**
	 * Invalid move, move can't be performed.
	 */
	INVALID_MOVE(3, "Invalid move!");

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
	 *            code
	 * @param description
	 *            description
	 */
	private ActionStatus(final int code, final String description)
	{
		this.code = code;
		this.description = description;
	}

	/**
	 * Returns the code.
	 * 
	 * @return code
	 */
	public int getCode()
	{
		return code;
	}

	/**
	 * Returns the description.
	 * 
	 * @return description
	 */
	public String getDescription()
	{
		return description;
	}
}
