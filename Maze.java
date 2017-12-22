// Name:Julius Yee
// USC loginid: juliusye
// CS 455 PA3
// Fall 2017

import java.util.LinkedList;


/**
   Maze class

   Stores information about a maze and can find a path through the maze
   (if there is one).
   
   Assumptions about structure of the maze, as given in mazeData, startLoc, and endLoc
   (parameters to constructor), and the path:
     -- no outer walls given in mazeData -- search assumes there is a virtual 
        border around the maze (i.e., the maze path can't go outside of the maze
        boundaries) via phantom walls
     -- start location for a path is maze coordinate startLoc
     -- exit location is maze coordinate exitLoc
     -- mazeData input is a 2D array of booleans, where true means there is a wall
        at that location, and false means there isn't (see public FREE / WALL 
        constants below) 
     -- in mazeData the first index indicates the row. e.g., mazeData[row][col]
     -- only travel in 4 compass directions (no diagonal paths)
     -- can't travel through walls
 */

public class Maze 
{
   //CONSTANTS
   public static final boolean FREE = false;
   public static final boolean WALL = true;
   
   //INSTANCE VARIABLES
   private char[][] mazeBoard;
   private MazeCoord start;
   private MazeCoord end;
   private LinkedList<MazeCoord> path;
   
   /**
   Representation invariant:
   - The maze is represented within this class with a border of phantom walls to prevent out of bounds searching
   - The phantom walls are at the first and last columns: mazeBoard[0,i] and maze[mazeBoard.length,i] where 
     0 < i < mazeBoard[0].length
   - The phantom walls are at the first and last rows: mazeBoard[i,0] and mazeBoard[i,mazeBoard[0].length] where
     0 < i < mazeBoard.length
   */
   
   /**
      Constructs a maze.
      @param mazeData the maze to search.  See general Maze comments above for what
      goes in this array.
      @param startLoc the location in maze to start the search (not necessarily on an edge)
      @param exitLoc the "exit" location of the maze (not necessarily on an edge)
      PRE: 0 <= startLoc.getRow() < mazeData.length and 0 <= startLoc.getCol() < mazeData[0].length
         and 0 <= endLoc.getRow() < mazeData.length and 0 <= endLoc.getCol() < mazeData[0].length

    */
   public Maze(boolean[][] mazeData, MazeCoord startLoc, MazeCoord exitLoc) 
   {
	   //Create the maze and fill out where the walls are
	   mazeBoard = new char [mazeData.length + 2][mazeData[0].length + 2];
	   for (int row = 1; row < mazeBoard.length - 1; row ++)
	   {
		   for (int col = 1; col < mazeBoard[0].length - 1; col++ )
		   {
			   //if there is a wall, place a wall
			   if (mazeData[row-1][col-1] == WALL)
			   {
				   mazeBoard[row][col] = '#';
			   }
			   else 
			   {
				   mazeBoard[row][col] = ' ';
			   }
		   }
	   }
	   
	   //Create phantom wall to border the maze to prevent out of bounds checking
	   for (int i = 0; i < mazeBoard.length; i++)
	   {
		   mazeBoard[i][0] = '#';
		   mazeBoard[i][mazeBoard[0].length - 1] = '#';
	   }
	   for (int j = 1; j < mazeBoard[0].length - 1; j++)
	   {
		   mazeBoard[0][j] = '#';
		   mazeBoard[mazeBoard.length - 1][j] = '#';
	   }
	   
	   //the start and end maze coordinates are represented with a  +1 in row and column due to phantom walls
	   start = new MazeCoord(startLoc.getRow() + 1, startLoc.getCol() + 1);
	   end = new MazeCoord(exitLoc.getRow() + 1, exitLoc.getCol() + 1);
	   
	   path = new LinkedList<MazeCoord>();
   }

   /**
      Returns the number of rows in the maze
      @return number of rows
   */
   public int numRows()
   {
      return (mazeBoard.length - 2);
   }

   
   /**
      Returns the number of columns in the maze
      @return number of columns
   */   
   public int numCols() 
   {
      return (mazeBoard[0].length - 2);
   } 
 
   
   /**
      Returns true iff there is a wall at this location
      @param loc the location in maze coordinates
      @return whether there is a wall here
      PRE: 0 <= loc.getRow() < numRows() and 0 <= loc.getCol() < numCols()
   */
   public boolean hasWallAt(MazeCoord loc) 
   {
      if (mazeBoard[loc.getRow() + 1][loc.getCol() + 1] == '#')
      {
    	  return true;
      }
      else
      {
    	  return false;
      }
   }
   
   /**
      Returns the entry location of this maze.
    */
   public MazeCoord getEntryLoc() 
   {
      return (new MazeCoord(start.getRow() - 1, start.getCol() - 1));
   }
   
   
   /**
     Returns the exit location of this maze.
   */
   public MazeCoord getExitLoc() 
   {
	   return (new MazeCoord(end.getRow() - 1, end.getCol() - 1));
   }

   
   /**
      Returns the path through the maze. First element is start location, and
      last element is exit location.  If there was not path, or if this is called
      before a call to search, returns empty list.

      @return the maze path
    */
   public LinkedList<MazeCoord> getPath() 
   {
      return path;
   }


   /**
      Find a path from start location to the exit location (see Maze
      constructor parameters, startLoc and exitLoc) if there is one.
      Client can access the path found via getPath method.

      @return whether a path was found.
    */
   public boolean search()  
   {  
	  //Make sure the mazeBoard is cleared of "discovered" markers for a repeat search
	   for (int i = 0; i < mazeBoard.length; i++)
	   {
		   for (int j = 0; j < mazeBoard[0].length; j++)
		   {
			   if (mazeBoard[i][j] == '.')
			   {
				   mazeBoard[i][j] = ' ';
			   }
		   }
	   }
	   
	   //Clear the path LinkedList as we are to fill it again
	   path.clear();
	   
      return (pathExists(mazeBoard,start.getRow(),start.getCol(),end.getRow(),end.getCol()));
   }
   
   private boolean pathExists(char[][] maze, int startRow, int startCol, int endRow, int endCol)
   {
	   //Entry/Exit point is  a wall
	   if (maze[startRow][startCol] == '#' || maze[endRow][endCol] == '#')
	   {
		   return false;
	   }
	   
	   //Found the exit location!
	   if (startRow == endRow && startCol == endCol)
	   {
		   path.add(new MazeCoord(startRow,startCol));
		   return true;
	   }
	   
	   //Otherwise mark as part of path
	   mazeBoard[startRow][startCol] = '.';
	   path.add(new MazeCoord(startRow,startCol));
	   
	   //if north spot is open
	   if (maze[startRow - 1][startCol] == ' ')
	   {
		   boolean check = pathExists(maze, startRow - 1, startCol, endRow, endCol);
		   if (check)
		   {
			   return true;
		   }
	   }
		
	   //if south spot is open
	   if (maze[startRow + 1][startCol] == ' ')
	   {
		   boolean check = pathExists(maze, startRow + 1, startCol, endRow, endCol);
		   if (check)
		   {
			   return true;
		   }
	   }
		
	   //if west spot is open
	   if (maze[startRow][startCol - 1] == ' ')
	   {
		   boolean check = pathExists(maze, startRow, startCol - 1, endRow, endCol);
		   if (check)
		   {
			   return true;
		   }
	   }
		
	   //if east spot is open
	   if (maze[startRow][startCol + 1] == ' ')
	   {
		   boolean check = pathExists(maze, startRow, startCol + 1, endRow, endCol);
		   if (check)
		   {
			   return true;
		   }
	   }
	   
	   //Remove as part of path
	   path.removeLast();
	   
	   return false;
   }
}