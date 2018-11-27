package zunused;

import allscreen.Screen;
import asciiPanel.AsciiPanel;
import entities.Entity;
import items.Item;

public class DropScreen extends InventoryBasedScreen 
{
	AsciiPanel terminal;
	
    public DropScreen(Entity player) 
    {
        super(player);
    }
    public DropScreen(Entity player, AsciiPanel terminal) 
    {
        super(player);
        this.terminal = terminal;
    }

	@Override
	protected String getVerb()
	{
		return "drop";
	}

	@Override
	protected boolean isAcceptable(Item item)
	{
	     return true;
	}

	@Override
	protected Screen use(Item item)
	{
        // player.drop(item, );
        return null;
	}
	@Override
	public Screen returnScreen(Screen screen)
	{
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void animate()
	{
		// TODO Auto-generated method stub
		
	}
}