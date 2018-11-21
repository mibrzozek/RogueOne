package _Structures;

import java.util.TimerTask;

public class Reminder extends TimerTask
{
	private MainFrame mf;
	
	public Reminder(MainFrame mf)
	{
		this.mf = mf;
	}
	@Override
	public void run()
	{
		mf.repaint();
	}

}
