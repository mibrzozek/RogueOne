package structures;

import java.util.ArrayList;

import allscreen.Message;

public class MessageTester
{
	public static void main(String ... args)
	{
		Message m1 = new Message("Hello, this is one the the new messages. This is one long one!", 85- 31);
		Message m2 = new Message("With this message tester we will see how these messages actually behave, and rid all of these evil bugs and it stiln doesn't seem to be working", 85- 31);
		Message m3 = new Message("I am doing this because i have been ravaged by these bugs wayy too long.", 85- 31);
		Message m4 = new Message("It doesn't seem to be prinintg the correct nummber of lines", 85- 31);
		Message m5 = new Message("Let us test this now.", 85- 31);
		
		ArrayList<Message> msgList = new ArrayList<>();
		msgList.add(m1);
		msgList.add(m2);
		msgList.add(m3);
		msgList.add(m4);
		msgList.add(m5);
		
		for(Message  m : msgList)
		{
			ArrayList<String> lines = m.getLines();
			/*
			System.out.println(m.getFullMessage() + "\n" 
					+ m.maxLength() + " ml\n" 
					+ m.numLines() + "numLines");
			*/
			System.out.println("Lines : " +lines.size() + " Max Length : " + m.maxLength()+ " char count : " + m.charCount());
			
			for(int i = 0; i < lines.size(); i++)
				System.out.println(lines.get(i) + lines.get(i).length() +" " + i);
			System.out.println("\n");
			
		}
		
		
	}
}
