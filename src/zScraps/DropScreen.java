package zScraps;

import Character.Entity;
import Screens.Screen;
import asciiPanel.AsciiPanel;
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
}