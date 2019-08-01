package screens;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import asciiPanel.AsciiPanel;
import entities.Entity;
import entities.EntityFactory;
import entities.FieldOfView;
import entities.Statistics;
import items.Item;
import items.ItemFactory;
import structures.TileEngine;
import wolrdbuilding.PlanetPrinter;
import wolrdbuilding.Projectile;
import wolrdbuilding.Tile;
import wolrdbuilding.World;

public class PlayScreen implements Screen 
{
	private World world;
	private int centerX;
    private int centerY;
    private int screenWidth;
    private int screenHeight;
    private int screenTicks = 0;

    private int leftOffset,  topOffset;
    
    private Entity player;
    private Entity trader;
    private FieldOfView fov;
    
    private Screen subScreen;
    private Screen tradeScreen;
    
    static boolean inventoryMode = false;
    private static boolean exitGame = false;
    
    private List<String> introMessages;
    private List<String> messages;
    private List<String> mLog;
    private List<Projectile> projectiles;
    
    private Random r = new Random();

    private transient AsciiPanel terminal;
    /**
     * This screen is responsible for keeping track of all objects related to playing the game. 
     * The PlayScreen is the only objects which needs to be saved and loaded, making the saving
     * system easier to implement than expected.
     */
    public PlayScreen(World world, Entity player, FieldOfView fov)
    {
        screenWidth = 85;
        screenHeight = 50;
        
        introMessages = new ArrayList<String>();
        introMessages.add("You wake from a deep slumber.");
        introMessages.add("The ground is wet, and the air smells of metal. You hear a sound and are startled.");
        messages = new ArrayList<String>();
        
        this.terminal = new AsciiPanel(100, 100);
        
    	this.player = player;
    	this.world = world;
    	this.fov = fov;
    	
        EntityFactory entityFactory = new EntityFactory(this.world, this.fov);
        ItemFactory itemFactory = new ItemFactory(this.world);
        
        createEntities(entityFactory, itemFactory);
        createItems(itemFactory); 
    }
    public PlayScreen(Statistics stats)
    {
        screenWidth = 85;
        screenHeight = 50;
        
        introMessages = new ArrayList<String>();
        introMessages.add("You wake from a deep slumber.");
        introMessages.add("The ground is wet, and the air smells of metal. You hear a sound and are startled by it.");
        messages = new ArrayList<String>();
       
        this.terminal = new AsciiPanel(100, 100);
        terminal.setDefaultForegroundColor(Color.WHITE);
      
        createWorld();
    
        fov = new FieldOfView(world);
        EntityFactory entityFactory = new EntityFactory(world, fov);
        ItemFactory itemFactory = new ItemFactory(world);
        player = entityFactory.newPlayer(messages, stats);
        world.addPlayer(player);

        createEntities(entityFactory, itemFactory);
        createItems(itemFactory);     
    }
    public PlayScreen()
    {
        screenWidth = 85;
        screenHeight = 50;
        
        introMessages = new ArrayList<String>();
        introMessages.add("You wake from a deep slumber.");
        introMessages.add("The ground is wet, and the air smells of metal. You hear a sound and are startled.");
        messages = new ArrayList<String>();
       
        this.terminal = new AsciiPanel(100, 100);
        terminal.setDefaultForegroundColor(Color.WHITE);
      
        createWorld();
    
        fov = new FieldOfView(world);
        EntityFactory entityFactory = new EntityFactory(world, fov);
        ItemFactory itemFactory = new ItemFactory(world);
        player = entityFactory.newPlayer(messages, null);
        world.addPlayer(player);

        createEntities(entityFactory, itemFactory);
        createItems(itemFactory);     
    }
    private void createItems(ItemFactory itemFactory) 
    {
        for (int z = 0; z < world.depth(); z++)
        {
            for (int i = 0; i < world.width() * world.height() / 20000; i++)
            {
            	itemFactory.newPlasmaPod();
            	world.addAtEmptyLocation(0 , itemFactory.getRandomItem());
            }
        }
        itemFactory.newVictoryItem(1);
    }
	private void createEntities(EntityFactory entityFactory, ItemFactory itemFactory)
	{
		ItemFactory nullFactory = new ItemFactory();

		for (int z = 0; z < world.depth(); z++)
		{
			for (int i = 0; i < 4; i++)
			{
				/*
				entityFactory.newFungus(z);
				entityFactory.newRogue();
				entityFactory.newDroid();
				entityFactory.newMutant();
				/// entityFactory.newMech();
				*/
				entityFactory.newPlasmaJunkie(0, player);
			}
		}
	}
    private void createWorld()
    {
    	// Sets World size
    	// Makes caves, castles, loot and enemies
        world = new PlanetPrinter(200 ,200 , 5, player)
        			.makeDungeons()
        			.build();
    }
	private void displayTiles(AsciiPanel terminal, int left, int top)
	{
		fov.update(player.x, player.y, player.z, player.visionRadius());
		
		for(int x = 0; x < screenWidth; x++)
		{
			for (int y = 0; y < screenHeight; y++)
			{

				int wx = x + left;
				int wy = y + top;

	            if(player.canSee(wx, wy, player.z))
	            {
	                terminal.write(world.glyph(wx, wy, player.z), x, y,
	                		world.color(wx, wy, player.z), world.backColor(wx, wy, player.z));
	            }
	            else
	                terminal.write(fov.tile(wx, wy, player.z).glyph(), x, y, Color.darkGray);
			}
		}
	}
    @Override
	public void displayOutput(AsciiPanel terminal) 
	{
	     leftOffset = getScrollX();
	     topOffset = getScrollY();

	     displayTiles(terminal, leftOffset, topOffset);
	     TileEngine.renderStats(terminal, screenWidth, screenHeight, 0, 0, player);
	   
	     if(subScreen instanceof CraftingScreen)
	    	 ((CraftingScreen) subScreen).write(terminal);
	     
	     if(subScreen instanceof TradeScreen)
	     {
	    	 ((TradeScreen) subScreen).displayOutput(terminal);
	    	 ((TradeScreen) subScreen).write(terminal); 
	     }
	     if(subScreen instanceof EscapeScreen)
	     {
	    	 ((EscapeScreen) subScreen).displayOutput(terminal);
	    	 ((EscapeScreen) subScreen).write(terminal); 
	     }
	     if (subScreen instanceof InventoryScreen)
	     {
	    	 ((InventoryScreen) subScreen).write(terminal);
	    	 ((InventoryScreen) subScreen).displayOutput(terminal);
	     }
	     if(subScreen instanceof InspectScreen)
	    	 ((InspectScreen) subScreen).displayOutput(terminal);
	     if(subScreen instanceof KeyInputScreen)
	    	 ((KeyInputScreen) subScreen).displayOutput(terminal);
	     if(subScreen instanceof CharacterSheet)
	    	 ((CharacterSheet) subScreen).displayOutput(terminal);
	     if(subScreen instanceof TargetingScreen)
	    	 ((TargetingScreen) subScreen).displayOutput(terminal);
	     if(subScreen instanceof AnimationScreen)
	    	 ((AnimationScreen) subScreen).displayOutput(terminal);
	     if(subScreen instanceof InteractScreen)
	    	 ((InteractScreen) subScreen).displayOutput(terminal);
	     
	     TileEngine.displayMessages(terminal, messages, screenWidth, screenHeight);
	}
	public int getLeftOffset() { return leftOffset;}
	public int getTopOffset() { return topOffset;}
    public int getScrollX() 
    {
        return Math.max(0, Math.min(player.x - screenWidth / 2, world.width() - screenWidth));
    }
    public int getScrollY() { return Math.max(0, Math.min(player.y - screenHeight / 2, world.height() - screenHeight)); }
    private boolean userIsTryingToExit()
    {
        return player.z == 0 && world.tile(player.x, player.y, player.z).getTile() == Tile.STAIRS_EXIT;
    }
    private Screen userExits()
    {
        for (Item item : player.inventory().getItems())
        {
            if (item != null && item.name().equals("Teddy Bear"))
                return new WinScreen(terminal);
        }
        return new LoseScreen(terminal);
    }
    public void saveGame()
    {
    	try 
    	{
    		FileOutputStream fos = new FileOutputStream(new File("D:\\06 SOURCE\\saveFile"));
    		ObjectOutputStream oos = new ObjectOutputStream(fos);
    		
    		oos.writeObject(this);
    		oos.close();
    		fos.close();
    		
    		messages.add("Save successful ;)");
        } 
    	catch (IOException e) 
    	{
        	e.printStackTrace();
        	messages.add("Save failed");
        }
    	subScreen = null;
    }
    public void setSubScreen(Screen screen)
	{
		this.subScreen = screen;

	}
    public void updateWorld() 			{ world.update();}
    public void returnStartScreen()     { exitGame = true; 	}
	@Override
	public Screen respondToUserInput(KeyEvent key) 
	{	
		if(screenTicks < introMessages.size())
		{
			player.notify("The level is " + player.z);
		
			messages.add(introMessages.get(screenTicks++));
			return this;
		}
		else 
		{
			if(subScreen != null)
			{
				subScreen = subScreen.respondToUserInput(key);
				if(subScreen instanceof EscapeScreen)
				{
					if(((EscapeScreen)subScreen).exit == true)
					{
						subScreen = null;
						((EscapeScreen)subScreen).exit = false;
					}
				}
			}
			else
			{	switch (key.getKeyCode())
				{
				// Special Keys
				case KeyEvent.VK_SHIFT: subScreen = new CharacterSheet(player); break;
				case KeyEvent.VK_T:
				{
					if(player.fov().getEntities().size() > 0)
						subScreen = new TargetingScreen(player, this);
					break;
				}case KeyEvent.VK_L: subScreen = new KeyInputScreen(terminal,this, 20, 15, 8); break;
        		case KeyEvent.VK_ESCAPE: subScreen = new EscapeScreen(player,terminal, this); break;
        		case KeyEvent.VK_ENTER: return new WinScreen(terminal);
        		case KeyEvent.VK_F: 
        		{
        			if(world.tile(player.x, player.y, player.z).getTile() == Tile.TERMINAL_ACESS)
        				subScreen = new InteractScreen(player, this, null);
        			else
        				player.pickup(); break;
        		}
        		case KeyEvent.VK_E: player.rotateClockwise(); break;
        		case KeyEvent.VK_Q: player.rotateCounterClockwise(); break;
        		case KeyEvent.VK_SPACE: player.useWeapon(); world.update();	 break;
        		case KeyEvent.VK_G: player.useDevice(); break;
        		case KeyEvent.VK_C: // Inspect item`
        		{	
        			if(world.item(player.x, player.y, player.z) != null)
        				subScreen = new InspectScreen(world.item(player.x, player.y, player.z)); break;
        		}
        		case KeyEvent.VK_R: // Inventory toggle
        		{	
        			if(player.tradeMode())
        			{
        				Entity other = world.entity(player.tradersPosition().x
        						,player.tradersPosition().y
        						,player.tradersPosition().z);
        				subScreen = new TradeScreen(player, other, terminal);
        			}	
        			else
        				subScreen = new InventoryScreen(player, terminal, true); break;
        		}
        		case KeyEvent.VK_1: ; break;
        		// Planar Scrolling
        		case KeyEvent.VK_NUMPAD4:
        		case KeyEvent.VK_LEFT:
        		case KeyEvent.VK_A: player.moveBy(-1, 0, 0);world.update(); break;
        		case KeyEvent.VK_NUMPAD6:
        		case KeyEvent.VK_RIGHT:
        		case KeyEvent.VK_D: player.moveBy( 1, 0, 0);world.update(); break;
        		case KeyEvent.VK_NUMPAD8:
        		case KeyEvent.VK_UP:
        		case KeyEvent.VK_W: player.moveBy( 0,-1, 0);world.update(); break;
        		case KeyEvent.VK_DOWN:
        		case KeyEvent.VK_S: player.moveBy( 0, 1, 0);world.update(); break;
        			
        		// Diagonal scrolling
        		case KeyEvent.VK_NUMPAD2:
        		case KeyEvent.VK_J: player.moveBy( 0, 1, 0);world.update(); break;
        		case KeyEvent.VK_NUMPAD7:
        		case KeyEvent.VK_Y: player.moveBy(-1,-1, 0);world.update(); break;
        		case KeyEvent.VK_NUMPAD9:
        		case KeyEvent.VK_U: player.moveBy( 1,-1, 0);world.update(); break;
        		case KeyEvent.VK_NUMPAD1:
        		case KeyEvent.VK_B: player.moveBy(-1, 1, 0);world.update(); break;
        		case KeyEvent.VK_NUMPAD3:
        		case KeyEvent.VK_N: player.moveBy( 1, 1, 0);world.update(); break;
        		

				}
				switch (key.getKeyChar())
				{
				case ',': player.pickup(); break;
				case '<':
			        if (userIsTryingToExit())
			        	return userExits();
			        else
			            player.moveBy( 0, 0, -1); 
			        break;
			    case '>': player.moveBy( 0, 0, 1); break;
				}
			}
		}
		if (player.stats.getVitals() < 1)
		    return new LoseScreen(terminal);
		
		if(exitGame)
		{
			exitGame = false;
			return new StartScreen(terminal);
		}
		return this;	
	}
	@Override
	public Screen returnScreen(Screen screen)
	{
		return null;
	}
	@Override
	public void animate()
	{
		world.animate();
		
	}
}
