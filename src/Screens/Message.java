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
		
		Math.round(nl);
		
		System.out.println(numLines + " This is the num lines \n");
		
		// This loops fills up a line with words until it's at max length
		for(int j = 0; j < numLines; j++)
		{
			String line = "";
			int charCount = 0;
			//This loops adds words to a line one by one until the next word will exceed the maxlnegth
			while(charCount < ml && wordCount < split.length)
			{
				String word = split[wordCount++];
				word += " ";
				if(charCount + word.length() < ml)
				{
					charCount += word.length();
					line += word;
				} // checks next word for length and if exceeds adds line
				else if(wordCount < split.length && charCount + split[wordCount].length() > ml ) 
				{
					choppedMsg.add(line);
					charCount = ml;
					--wordCount;
				}
				if(split.length == wordCount) // no more words
					choppedMsg.add(line);
			}
			System.out.println(j);
		}
		
		return choppedMsg;
	}
	public String getFullMessage()
	{
		return msg;
	}
	public int maxLength()
	{
		return ml;
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
	@Override
	public String toString()
	{
		return msg;
		
	}
}
