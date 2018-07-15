package Screens;

import java.util.ArrayList;
import java.util.List;

public class Message
{
	String msg;
	double numLines;
	int ml;
	
	ArrayList<String> lineList;
	
	public Message(String msg, int lineLength)
	{
		this.msg = msg;
		this.ml = lineLength;
		
		this.numLines = (msg.length() / lineLength) + 1;
		this.lineList = makeLines();
	}
	
	public ArrayList<String> makeLines()
	{
		Math.round(numLines);
		
		double nl = (msg.length()/ml);
		String[] split = msg.split(" ");
		ArrayList<String> choppedMsg = new ArrayList<>();
		int wordCount = 0;
		
		Math.round(numLines);
		
		// This loops fills up a string with words until it's at max length
		for(int j = 0; j < numLines; j++)
		{
			String line = "";
			int charCount = 0;
			//This loops adds words to a string one by one until the next word wil exced the maxlnegth
			while(charCount < ml -2 && wordCount < split.length)
			{
				String word = split[wordCount++];
				word += " ";
				charCount += word.length();
				
				line += word;
				// This will add the line to a list once line is full or end of msg
				if(charCount > ml - 2 || wordCount == split.length)
				{
					System.out.println(line);
					choppedMsg.add(line);
					line = ""; // redundant clearing
				}
			}
		}
		
		return choppedMsg;
	}
	public String getFullMessage()
	{
		return msg;
	}
	public int charCount()
	{
		return msg.length();
	}
	public double numLines()
	{
		return numLines;
	}
	public ArrayList<String> getLines()
	{
		return lineList;
	}
}
