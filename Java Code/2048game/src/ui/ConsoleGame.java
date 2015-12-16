package ui;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import algorithm.*;

/**
 * For playing the game in the console.
 * 
 * @author Sayantani Ghosh, Piyush Mantri, Dinesh Papineni
 */
public class ConsoleGame
{
	/**
	 * Main function of the game.
	 * 
	 * @param args
	 *            args
	 * @throws CloneNotSupportedException
	 *             clone not supported
	 */
	public static void main(String[] args) throws CloneNotSupportedException
	{
		while (true)
		{
			printMenu();
			int choice;
			try
			{
				Scanner sc = new Scanner(System.in);
				choice = sc.nextInt();
				switch (choice)
				{
					case 1:
						calculateAccuracy();
						break;
					case 2:
						return;
					default:
						throw new Exception();
				}
			}
			catch (Exception e)
			{
				System.out.println("Wrong choice");
			}
		}
	}

	/**
	 * Prints main menu
	 */
	public static void printMenu()
	{
		System.out.println("1. Calculate accuracy");
		System.out.println("2. Quit");
		System.out.println("Enter a number from 1-2:");
	}

	/**
	 * Estimates the accuracy of the AI solver by running multiple games.
	 * 
	 * @throws CloneNotSupportedException
	 *             clone not supported
	 */
	public static void calculateAccuracy() throws CloneNotSupportedException
	{
		int wins = 0;
		int total = 10;
		System.out.println("Running " + total + " games to estimate the accuracy:");
		for (int i = 0; i < total; ++i)
		{
			int hintDepth = 7;
			Board theGame = new Board();
			DirectionStatus hint = NextMove.findBestMove(theGame, hintDepth);
			ActionStatus result = ActionStatus.CONTINUE;
			while (result == ActionStatus.CONTINUE || result == ActionStatus.INVALID_MOVE)
			{
				result = theGame.action(hint);
				if (result == ActionStatus.CONTINUE || result == ActionStatus.INVALID_MOVE)
					hint = NextMove.findBestMove(theGame, hintDepth);
			}
			if (result == ActionStatus.WIN)
			{
				++wins;
				System.out.println("Game " + (i + 1) + " - won");
			}
			else
				System.out.println("Game " + (i + 1) + " - lost");
		}
		System.out.println(wins + " wins out of " + total + " games.");
	}

	/**
	 * Prints the Board
	 * 
	 * @param boardArray
	 *            board array
	 * @param score
	 *            score
	 * @param dir
	 *            preferable direction based on the algorithms
	 */
	public static void printBoard(int[][] boardArray, int score, DirectionStatus dir)
	{
		System.out.println("-------------------------");
		System.out.println("Score: " + String.valueOf(score));
		System.out.println("Favorable direction: " + dir);
		for (int i = 0; i < boardArray.length; ++i)
		{
			for (int j = 0; j < boardArray[i].length; ++j)
				System.out.print(boardArray[i][j] + "\t");
			System.out.println();
		}
		System.out.println("-------------------------");
	}
}
