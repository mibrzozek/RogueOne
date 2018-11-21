package screens;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MessageManager implements Serializable
{
	List<String>  messages;
	
	public MessageManager(List<String> messages)
	{
		this.messages =  messages;
	}
	
	public ArrayList<String> updateToDisplay(List<String> msg, int maxLength)
	{
		
		ArrayList <String> toUpdate = new ArrayList<>(10);
		int lastIndex = msg.size() -1;
		
		
		for(int i = 0; i < 10; i++)
		{
			if(msg.get(lastIndex).length() > maxLength)
				splitIntoMoreLines(msg.get(lastIndex--), 0, maxLength);
			else
				toUpdate.add(msg.get(lastIndex));
			
		}
		
		
		return toUpdate;
	}
	private void splitIntoMoreLines(String s, int i, int ml)
	{
		double numLines = s.length()/ml;
		String[] split = s.split(" ");
		List<String> choppedMsg = new ArrayList<>();
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
				if(charCount > ml -2 || wordCount+1 == split.length)
				{
					System.out.println(line);
					choppedMsg.add(line);
				}
			}
		}
		
		ArrayList<String> newMsg = new ArrayList<>();
		
		for(int y = 0; y < messages.size(); y++)
		{
			if(y != i)
				newMsg.add(messages.get(y));
			else
			{
				for(int o = 0; o < numLines; o++)
				{
					newMsg.add(choppedMsg.get(o));
				}
			}
		}
	
		this.messages = newMsg;
	
	}
	public ArrayList<String> returnUpdatedMessages()
	{
		return (ArrayList<String>) messages;
	}
}
