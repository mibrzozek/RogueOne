package structures;

import java.util.ArrayList;

import screens.Message;
import wolrdbuilding.Door;
import wolrdbuilding.Point;

public class MessageTester
{
	public static void main(String ... args)
	{
		Message m1 = new Message("Hello, this is one the the new messages. This is one long one!", 85- 31);
		Message m2 = new Message("With this message tester we will see how these messages actually behave, and rid all of these evil bugs and it still doesn't seem to be working.", 85- 31);
		Message m3 = new Message("I am doing this because i have been ravaged by these bugs way too long.", 85- 31);
		Message m4 = new Message("It doesn't seem to be printing the correct number of lines.", 85- 31);
		Message m5 = new Message("Hello interloper. My name is Laura and I will be your guide. I'm sure you have" +
				" a lot of questions and the answers will come soon enough but first you must feed me plasma" +
				" or we both won't be able to make it. You see, i manage this underground sector, and I do all the heavy lifting when it comes" +
				" to keeping this facilities running, and all I need from you is to bring me some plasma. It is the fuel which lets you breathe" +
				" and lets me compute. Go now, the time is running out!" , 43);
		
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

		System.out.println(" Testing doors ");

		Door d = new Door(new Point(0,0,0),new Point(1,0,0) , Door.Clearance.PURPLE);
		Door s = new Door(new Point(0,0,0),new Point(1,0,0) , Door.Clearance.PURPLE);

		if(d.equals(new Point(0,0,0)))
			System.out.print("These doors are the same");
		
	}
}
