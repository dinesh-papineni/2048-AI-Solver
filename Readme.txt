=============================
2048Solver
=============================
contact p.dineshchowdary@gmail.com for further information or any help.

Description
=============================
An artificial intelligent agent that solves the 2048 game by using MinMax algorith.The project consists of three different components :
	
	Part I: Problem Generator. 
			Generates the 2048 problem randomly.
	Part II: Algorithm
			 Attempts to solve the 2048 problem using artificial intelligence. 
	Part III: Manager
			  Acts as the interface between Solver and Generator. Solvers sends a query for a specific location via Manager to the Generator, which in turn responds according to the rules of the game.
			
Setting the Expectation
=============================
This is an intelligent agent which tries to solve the 2048 game with atmost accuracy. We are successful at implementing the solver with an accuracy of 90%. We still encounter a few issues which respect to the speed of the solver and updation of the GUI for each move.  See @section on deliverables on running it in different modes

Deliverables
=============================
2048.jar : Standalone runnable jar file. 
	Running the JAR
	----------------
 	The 4 arrow keys move the tiles in the 4 directions. The 'A' key runs the algorithms, 
	calculates the best move and applies one move. 
	Note that, it applies only one move at one key press. Hold down 'A' key for continuous moves.

Project source files along with Java Docs
	Running the code
	----------------
	The 'ui' package contains a class called 'GameMain'. Run that to execute the ui version of the 
	game.
	There is also a class in 'ui' called 'ConsoleGame', which can be used to play the game in the 
	console mode
