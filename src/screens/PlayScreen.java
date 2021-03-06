package screens;

import java.awt.*;
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
import items.Type;
import structures.Console;
import structures.MainFrame;
import structures.Theme;
import structures.TileEngine;
import wolrdbuilding.*;
import wolrdbuilding.Point;

import javax.swing.*;

public class PlayScreen implements Screen 
{
	private JFrame main;

	private Color fore = Theme.PASTEL.getFore();
	private Color back = Theme.PASTEL.getBack();

	private Console console;

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
    public PlayScreen(Statistics stats, JFrame main)
    {
        screenWidth = 85;
        screenHeight = 50;

        this.main = main;
        
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

        console = new Console(world, this);

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
    public void writeToConsole(String cmd)
	{
		String response = console.writeAndRespond(cmd);

		if(response != null)
		{
			messages.add(response);
		}
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
				entityFactory.newPlasmaJunkie(0, player);
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
	            	if(player.inventory().getTypeDuration(Type.HELMET) > 0)
	                	terminal.write(world.glyph(wx, wy, player.z), x, y,world.color(wx, wy, player.z));
	            	else
						terminal.write(world.glyph(wx, wy, player.z), x, y,fore, back);

	                //world.color(wx, wy, player.z)
	            }
	            else
	                terminal.write(fov.tile(wx, wy, player.z).glyph(), x, y, fore);
			}
		}
	}
    @Override
	public void displayOutput(AsciiPanel terminal) 
	{
	     leftOffset = getScrollX();
	     topOffset = getScrollY();

	     displayTiles(terminal, leftOffset, topOffset);
	     TileEngine.renderStats(terminal, screenWidth, screenHeight, 0, 0, world);
		 TileEngine.displayMessages(terminal, messages, screenWidth, screenHeight);

	   
	     if(subScreen instanceof CraftingScreen)
	    	 ((CraftingScreen) subScreen).write(terminal);
	     /*
	     if(subScreen instanceof TradeScreen)
	     {
	    	 ((TradeScreen) subScreen).displayOutput(terminal);
	    	 ((TradeScreen) subScreen).write(terminal); 
	     }
	     */
	     if(subScreen instanceof EscapeScreen)
	     {
	    	 ((EscapeScreen) subScreen).displayOutput(terminal);
	    	 ((EscapeScreen) subScreen).write(terminal); 
	     }
	     if (subScreen instanceof NInventoryScreen)
	     {
	    	 ((NInventoryScreen) subScreen).write(terminal);
	    	 ((NInventoryScreen) subScreen).displayOutput(terminal);
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
		if(subScreen instanceof MapScreen)
			((MapScreen) subScreen).displayOutput(terminal);
		if(subScreen instanceof DoorScreen)
			((DoorScreen) subScreen).displayOutput(terminal);
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
                return new WinScreen(terminal, main);
        }
        return new LoseScreen(terminal, world, main);
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
					case KeyEvent.VK_M: subScreen = new MapScreen(terminal, world); break;
					case KeyEvent.VK_0: subScreen = new KeyInputScreen(terminal, this, 10, 2, 1, console);	break;
				case KeyEvent.VK_SHIFT: subScreen = new CharacterSheet(world, main
				); break;
				case KeyEvent.VK_T:
				{
					if(player.fov().getEntities().size() > 0)
						subScreen = new TargetingScreen(player, this, main);
					break;
				}case KeyEvent.VK_L: subScreen = new KeyInputScreen(terminal,this, 20, 15, 8, null); break;
        		case KeyEvent.VK_ESCAPE: subScreen = new EscapeScreen(player,terminal, this); break;
        		case KeyEvent.VK_ENTER: break;
        		case KeyEvent.VK_F: 
        		{
        			if(world.tile(player.x, player.y, player.z).getTile() == Tile.TERMINAL_ACESS)
        				subScreen = new InteractScreen(player, this, null, main);
        			else if(world.item(player.x, player.y, player.z) != null)
        				player.pickup();

        			// this point is the one in front of the player
					// in the direction they are looking
					Point np = Point.transform(player.getCardinal(), player.point());
        			if(world.getTile(np.x, np.y, np.z).equals(Tile.OPEN_DOOR)
							|| world.getTile(np.x, np.y, np.z).equals(Tile.CLOSED_DOOR))
					{
						player.setDoorPoint(np);

						int doorX  = 0;
						if(player.x - leftOffset + 1 + 10 < screenWidth)
							doorX = player.x - leftOffset + 1;
						else
							doorX = player.x - leftOffset - 7;

						subScreen = new DoorScreen(world, 7, 4, doorX, player.y - topOffset -1, this, main);
					}
					break;
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
        				//subScreen = new TradeScreen(player, other, terminal);
        			}	
        			else
        				subScreen = new NInventoryScreen(player, terminal, true);

        			break;
        		}
        		case KeyEvent.VK_1: ; break;
        		// Planar Scrolling
					case KeyEvent.VK_NUMPAD5:
						world.update();
						break;
        		case KeyEvent.VK_NUMPAD4:
        		case KeyEvent.VK_LEFT:
        		case KeyEvent.VK_A: player.moveBy(-1, 0, 0);
							world.update();
							player.setDirection(6);
							break;
        		case KeyEvent.VK_NUMPAD6:
        		case KeyEvent.VK_RIGHT:
        		case KeyEvent.VK_D: player.moveBy( 1, 0, 0);
        					world.update();
							player.setDirection(2);
							break;
        		case KeyEvent.VK_NUMPAD8:
        		case KeyEvent.VK_UP:
        		case KeyEvent.VK_W: player.moveBy( 0,-1, 0);
        					world.update();
							player.setDirection(0);
							break;
        		case KeyEvent.VK_DOWN:
        		case KeyEvent.VK_S: player.moveBy( 0, 1, 0);
        					world.update();
							player.setDirection(4);
        					break;
        			
        		// Diagonal scrolling
        		case KeyEvent.VK_NUMPAD2:
        		case KeyEvent.VK_J: player.moveBy( 0, 1, 0);
        					world.update();
							player.setDirection(4);
        					break;
        		case KeyEvent.VK_NUMPAD7:
        		case KeyEvent.VK_Y: player.moveBy(-1,-1, 0);
        					world.update();
        					player.setDirection(7);
        					break;
        		case KeyEvent.VK_NUMPAD9:
        		case KeyEvent.VK_U: player.moveBy( 1,-1, 0);
        					world.update();
							player.setDirection(1);
        					break;
        		case KeyEvent.VK_NUMPAD1:
        		case KeyEvent.VK_B: player.moveBy(-1, 1, 0);
        					world.update();
							player.setDirection(5);
        					break;
        		case KeyEvent.VK_NUMPAD3:
        		case KeyEvent.VK_N: player.moveBy( 1, 1, 0);
        					world.update();
							player.setDirection(3);
        					break;
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
		if(player.isDead())
			return new LoseScreen(terminal, world, main);

		
		if(exitGame)
		{
			exitGame = false;
			return new StartScreen(terminal, main);
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

	@Override
	public Color getForeColor()
	{
		return fore;
	}

	@Override
	public Color getBackColor()
	{
		return back;
	}

	public void setNewTheme()
	{
		this.fore = Palette.paperPink;
		this.back = Palette.theNewMagenta;
	}

	public void changeTheme(Theme theme)
	{
		this.fore = theme.getFore();
		this.back = theme.getBack();
	}
	public void changeUiTheme(Theme t)
	{
		((MainFrame)main).setTheme(t);
	}
}
