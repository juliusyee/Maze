// Name: Julius Yee
// USC loginid: juliusye
// CS 455 PA3
// Fall 2017

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.LinkedList;

import javax.swing.JComponent;

/**
   MazeComponent class
   
   A component that displays the maze and path through it if one has been found.
*/
public class MazeComponent extends JComponent
{
   //CONSTANTS
   private static final int START_X = 10; // top left of corner of maze in frame
   private static final int START_Y = 10;
   private static final int BOX_WIDTH = 20;  // width and height of one maze "location"
   private static final int BOX_HEIGHT = 20;
   private static final int INSET = 2;   // how much smaller on each side to make entry/exit inner box
   
   //INSTANCE VARIABLES
   private int mazeFrameY;
   private int mazeFrameX;
   private MazeCoord startCoord;
   private MazeCoord endCoord;
   private char [][] mazeBoard;
   private LinkedList<MazeCoord> pathToPrint;

   /**
   Representation invariant:
   - 
   */
   
   /**
      Constructs the component.
      @param maze   the maze to display
   */
   public MazeComponent(Maze maze) 
   {   
      mazeFrameY = maze.numRows();
      mazeFrameX = maze.numCols();
      startCoord = maze.getEntryLoc();
      endCoord = maze.getExitLoc();
      pathToPrint = maze.getPath();
      
      //Store what the maze looks like
      mazeBoard = new char[mazeFrameY][mazeFrameX];
      for (int i = 0; i < mazeBoard.length; i++)
      {
    	  for (int j = 0; j < mazeBoard[0].length; j++)
    	  {
    		  MazeCoord temp = new MazeCoord(i, j);
    		  if (maze.hasWallAt(temp))
    		  {
    			  mazeBoard[i][j] = '#';
    		  }
    		  else
    		  {
    			  mazeBoard[i][j] = ' ';
    		  }
    	  }
       }
   }

   
   /**
     Draws the current state of maze including the path through it if one has
     been found.
     @param g the graphics context
   */
   public void paintComponent(Graphics g)
   {
	   Graphics2D g2 = (Graphics2D) g;
	   
	   //Draw the outer border of the maze
	   g.drawRect(START_X, START_Y, mazeFrameX*BOX_WIDTH, mazeFrameY*BOX_HEIGHT);
	   
	   //Paint the maze with white and black rectangles
	   int row = 0;
	   int col = 0;
	   for (int i = START_X; i < mazeFrameY*BOX_WIDTH + START_X; i = i + BOX_WIDTH)
	   {
		   for (int j = START_Y; j < mazeFrameX*BOX_HEIGHT +START_Y; j = j + BOX_HEIGHT)
		   {
			  if(mazeBoard[row][col] == '#')
			  {
				   g2.setColor(Color.BLACK);
			  }
			  else
			  {
				  g2.setColor(Color.WHITE);
			  }
			  g2.drawRect(j, i, BOX_WIDTH, BOX_HEIGHT);
			  g2.fillRect(j, i, BOX_WIDTH, BOX_HEIGHT);
			  col++;
		   }
		   row++;
		   col = 0;
	   }
	   
	   //paint the starting coordinate and exit coordinate
	   int newWidth = BOX_WIDTH - (2*INSET);
	   int newHeight = BOX_HEIGHT - (2*INSET);
	   int startX = START_Y +(startCoord.getCol() * BOX_HEIGHT) + INSET;
	   int startY = START_X + (startCoord.getRow() * BOX_WIDTH) + INSET;
	   int endX = START_Y +(endCoord.getCol() * BOX_HEIGHT) + INSET;
	   int endY = START_X +(endCoord.getRow() * BOX_WIDTH) + INSET;
	   
	   //draw the starting point
	   g2.setColor(Color.YELLOW);
	   g2.drawRect( startX, startY, newWidth, newHeight);
	   g2.fillRect(startX, startY, newWidth, newHeight);
	   
	   //draw exit point
	   g2.setColor(Color.GREEN);
	   g2.drawRect( endX, endY, newWidth, newHeight);
	   g2.fillRect(endX, endY, newWidth, newHeight);
	   
	   //paint the path
	   drawPath(g2);
   }
   
   /**
   private helper method that draws the path from start to end
   @param g the Graphics2D object for drawing
 */
   private void drawPath(Graphics2D g)
   {
	   //Loop through
	   for (int i = 0; i < pathToPrint.size() - 1; i++)
	   {
		   int x1 = START_Y + ((pathToPrint.get(i).getCol() - 1)* BOX_HEIGHT) + (BOX_WIDTH/2);
		   int y1 = START_X + ((pathToPrint.get(i).getRow() - 1)* BOX_WIDTH) + (BOX_HEIGHT/2);
		   int x2 = START_Y + ((pathToPrint.get(i+1).getCol() - 1)* BOX_HEIGHT) + (BOX_WIDTH/2);
		   int y2 = START_X + ((pathToPrint.get(i+1).getRow() - 1)* BOX_WIDTH) + (BOX_HEIGHT/2);
		   g.setColor(Color.BLUE);
		   g.drawLine(x1, y1, x2, y2);
	   }
   }
}


