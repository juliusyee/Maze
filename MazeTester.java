import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

import javax.activation.MailcapCommandMap;

public class MazeTester
{
	public static void main(String[] args)
	{
		/*
		boolean[][] array = 
			{
				{false,true,true,true,true,true,true,true,true,true},
				{false,true,true,false,false,false,false,false,false,false},
				{false,false,false,false,true,true,true,true,true,false},
				{true,false,true,true,true,false,true,false,true,false},
				{true,false,false,false,false,false,true,false,false,false},
				{true,false,true,true,false,true,true,true,false,true},
				{true,false,true,false,false,false,true,true,true,true},
				{false,false,false,false,true,false,false,false,false,false},
				{true,true,true,true,true,true,true,true,true,false},
			};
		MazeCoord start = new MazeCoord(5,8);
		MazeCoord end = new MazeCoord(8,9);
		
		Maze m = new Maze(array,start,end);
		System.out.println(m.numRows() + ", " + m.numCols());
		System.out.println(m.getEntryLoc().getRow() + ", " + m.getEntryLoc().getCol());
		System.out.println(m.getExitLoc().getRow() + ", " + m.getExitLoc().getCol());
		*/
		String[] n = {"the", "big", "dog"};
		System.out.println(makeStringHelper(n,0));
		
	}
	
	public static String makeStringHelper(String [] words, int index)
	{
		if(index == words.length - 1)
		{
			return words[index];
		}
		return(words[index] + " " + makeStringHelper(words,index+1));
	}
}