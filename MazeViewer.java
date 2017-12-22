// Name: Julius Yee
// USC loginid: juliusye
// CS 455 PA3
// Fall 2017


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFrame;


/**
 * MazeViewer class
 * 
 * Program to read in and display a maze and a path through the maze. At user
 * command displays a path through the maze if there is one.
 * 
 * How to call it from the command line:
 * 
 *      java MazeViewer mazeFile
 * 
 * where mazeFile is a text file of the maze. The format is the number of rows
 * and number of columns, followed by one line per row, followed by the start location, 
 * and ending with the exit location. Each maze location is
 * either a wall (1) or free (0). Here is an example of contents of a file for
 * a 3x4 maze, with start location as the top left, and exit location as the bottom right
 * (we count locations from 0, similar to Java arrays):
 * 
 * 3 4 
 * 0111
 * 0000
 * 1110
 * 0 0
 * 2 3
 * 
 */

public class MazeViewer
{
   //CONSTANTS
   private static final char WALL_CHAR = '1';
   private static final char FREE_CHAR = '0';
   public static final boolean NO_WALL = false;
   public static final boolean INSERT_WALL = true;

   public static void main(String[] args)  
   {

      String fileName = "";
      
      try 
      {
         if (args.length < 1) 
         {
            System.out.println("ERROR: missing file name command line argument");
         }
         else
         {
            fileName = args[0];
            
            JFrame frame = readMazeFile(fileName);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.setVisible(true);
         }
      }
      
      catch (FileNotFoundException exc) 
      {
         System.out.println("ERROR: File not found: " + fileName);
      }
      catch (IOException exc) 
      {
         exc.printStackTrace();
      }
   }

   /**
    readMazeFile reads in maze from the file whose name is given and 
    returns a MazeFrame created from it.
   
   @param fileName
             the name of a file to read from (file format shown in class comments, above)
   @returns a MazeFrame containing the data from the file.
        
   @throws FileNotFoundException
              if there's no such file (subclass of IOException)
   @throws IOException
              (hook given in case you want to do more error-checking --
               that would also involve changing main to catch other exceptions)
   */
   private static MazeFrame readMazeFile(String fileName) throws IOException 
   {
	   //Scanner object for the file
	   File inputFile = new File(fileName);
	   Scanner in = new Scanner(inputFile);
	   
	   //Store the number of rows and columns for the maze
	   int rows = in.nextInt();
	   int columns = in.nextInt();
	   in.nextLine(); //consume new line character
	   
	   boolean[][] board = new boolean[rows][columns];
	   
	   //For each line (a row), iterate through each character to determine if it's a wall or not
	   //and store it in the 2D array representation of the maze, board
	   for (int i = 0; i < rows; i++)
	   {
		   String row = in.nextLine();
		   for (int j = 0; j < row.length(); j++)
		   {
			   if (row.charAt(j) == WALL_CHAR)
			   {
				   board[i][j] = INSERT_WALL;
			   }
			   else
			   {
				   board[i][j] = NO_WALL;
			   }
		   }
	   }
	   
	   MazeCoord start = new MazeCoord(in.nextInt(),in.nextInt());
	   MazeCoord end = new MazeCoord(in.nextInt(),in.nextInt());
	   in.close();
	   
	   return new MazeFrame(board,start,end);
   }
}