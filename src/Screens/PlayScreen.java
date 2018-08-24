package Screens;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import Character.Entity;
import Character.EntityFactory;
import Character.FieldOfView;
import WorldBuilding.Point;
import WorldBuilding.Projectile;
import WorldBuilding.Tile;
import WorldBuilding.World;
import WorldBuilding.WorldBuilder;
import asciiPanel.AsciiPanel;
import items.Item;
import items.ItemFactory;
import javafx.scene.shape.Line;

public class PlayScreen implements Screen 
{
	private World world;
	
    private int centerX;
    private int centerY;
    
    private int screenWidth;
    private int screenHeight;
    
    private int screenTicks = 0;;
    
    private Entity player;
    private Entity trader;
    
    private FieldOfView fov;
    
    private Screen subScreen;
    private Screen tradeScreen;
    
    static boolean inventoryMode = false;
    
    
    private List<String> introMessages;
    private List<String> messages;
    private List<String> mLog;
    private List<Projectile> projectiles;
    
    private MessageManager msgMan;
    
    private transient AsciiPanel terminal;
    
    /**
     * This screen is responsible for keeping track of all objects related to playing the game. 
     * The PlayScreen is the only objects which needs to be saved and loaded, making the saving
     * system easier to implement than expected.
     */
    public PlayScreen()
    {
        screenWidth = 85;
        screenHeight = 50;
        
        introMessages = new ArrayList<String>();
        introMessages.add("The ground is wet, and the air smells of metal.");
        introMessages.add("Your lungs burn from the fumes giving you a reason to wake.");
        introMessages.add("They were likely the reason you were out in the first place.");
        introMessages.add("You recall last being tossed here, stolen of most of your possesions"
        		+ "and forced to start again. 'Not like that again', you think to yourself.");
        introMessages.add("'Hurry now', you think to yourself. 'Before they get me again'...");
        
        messages = new ArrayList<String>();
        if(messages.size() == 0)
        {
        	messages.add("You wake from a deep slumber.");
        }
        
        this.terminal = new AsciiPanel(100, 100);
        
        msgMan = new MessageManager(messages);
        
        createWorld();
        
        fov = new FieldOfView(world);
        EntityFactory entityFactory = new EntityFactory(world, fov);
        ItemFactory itemFactory = new ItemFactory(world);
        player = entityFactory.newPlayer(messages);
        world.addPlayer(player);

        createEntities(entityFactory, itemFactory);
        createItems(itemFactory);
        
        
        
    }
    public PlayScreen(AsciiPanel terminal)
    {
    	super();
    	this.terminal = terminal;
    }

    public PlayScreen(World world)
    {
    	 new PlayScreen();
    }
    private void createItems(ItemFactory itemFactory) 
    {
        for (int z = 0; z < world.depth(); z++)
        {
            for (int i = 0; i < world.width() * world.height() / 200; i++)
            {
            	itemFactory.newPlasmaPod();
            }
        }
        
        itemFactory.newVictoryItem(1);
    }
	private void createEntities(EntityFactory entityFactory, ItemFactory itemFactory)
	{
		ItemFactory nullFactory = new ItemFactory();

		for (int z = 0; z < world.depth(); z++)
		{
			for (int i = 0; i < 8; i++)
			{
				entityFactory.newFungus(z);
				entityFactory.newRogue();
				entityFactory.newDroid();
				entityFactory.newMutant();
				/// entityFactory.newMech();
				entityFactory.newHitman(0, player);
			}
		}
	}
    private void createWorld()
    {
    	// Sets World size
    	// Makes caves, castles, loot and enemies
        world = new WorldBuilder(200 ,200 , 5, player)
        			.makeCaves()
        			.makeRandoRooms()
        			.build();
       
        			//.makeCastle
        			//.makeVillage
        			//.spawnTreasure
        			
    }
	private void displayMessages(AsciiPanel terminal, List<String> messages) 
	{
		int top = screenHeight - messages.size();
		int msgSpace = 10;
		int xo = 31;
		
		ArrayList<String> toDisplay = new ArrayList<>(10);
		ArrayList<Message> msgList = new ArrayList<>();
		
		for(String s : messages)
		{
			msgList.add(new Message(s, screenWidth - xo));
		}
		// Loads toDisplay w/ lines to display
		// lastIndex is the message counter
		// j in the loop is incremented if multi line message
		int lastIndex = msgList.size()-1;
		
		for (int i = 0; i < 10; i++)
		{
			if(lastIndex > -1)
			{
				if(i == 0)
				{	
					if(msgList.get(lastIndex).numLines() == 1)
						terminal.write(msgList.get(lastIndex).toString(), xo, screenHeight+i+1, Color.WHITE);	
					else
					{
						ArrayList<String> lines = msgList.get(lastIndex).getLines();

						for(int q = 0; q < lines.size(); q++)
						{
							terminal.write(lines.get(q), xo, screenHeight + (i) + 1, Color.WHITE);
							if(q != lines.size()-1)
								i += 1;
						}
					}
					lastIndex--;
				}
				else
				{
					if(msgList.get(lastIndex).numLines() == 1)
						terminal.write(msgList.get(lastIndex).toString(), xo, screenHeight+i+1, Color.DARK_GRAY);	
					else
					{
						ArrayList<String> lines = msgList.get(lastIndex).getLines();

						for(int q = 0; q < lines.size(); q++)
						{
							terminal.write(lines.get(q), xo, screenHeight + (i) + 1, Color.DARK_GRAY);
							if(q != lines.size()-1)
								i += 1;
						}
					}
					lastIndex--;
				}
			}
		}
	}
	public void splitIntoMoreLines(String s, int j, int xo)
	{
		String[] split = s.split(" ");
		int count = 0;
	
		for (int y = screenHeight+1; y < 100; y++)
		{	
			int charCount= 0;
			int cx = 31;
			String line = "";
		
			while(charCount < 30  && count < split.length)
			{
				String word = split[count++];
				
				word += " ";
				charCount += word.length();
				//terminal.write(word, cx, y);
				cx += word.length();
				line += word;
				
				System.out.println(line.length() + " size");
				if(charCount + xo > screenWidth || count + 1 == split.length)
				{
					System.out.println(word.length() + "adding line");
					messages.add(j,  line);
				}
			}
		}	
	}
	private void displayTiles(AsciiPanel terminal, int left, int top)
	{
		fov.update(player.x, player.y, player.z, player.visionRadius());
		
		for (int x = 0; x < screenWidth; x++)
		{
			for (int y = 0; y < screenHeight; y++)
			{
				int wx = x + left;
				int wy = y + top;
				
	            if (player.canSee(wx, wy, player.z))
	                terminal.write(world.glyph(wx, wy, player.z), x, y, world.color(wx, wy, player.z));
	            else
	                terminal.write(fov.tile(wx, wy, player.z).glyph(), x, y, Color.darkGray);	
			}
		}
	}
    @Override
	public void displayOutput(AsciiPanel terminal) 
	{
	     int left = getScrollX();
	     int top = getScrollY();	
	     
	     
	     displayTiles(terminal, left, top);
	     renderStats(terminal);
	     renderTarget(terminal);
	   
	     if(subScreen instanceof CraftingScreen)
	    	 ((CraftingScreen) subScreen).write(terminal);
	     
	     if(subScreen instanceof TradeScreen)
	     {
	    	 ((TradeScreen) subScreen).displayOutput(terminal);
	    	 ((TradeScreen) subScreen).write(terminal);
	    	 
	     }
	     if (subScreen instanceof InventoryScreen)
	     {
	    	 ((InventoryScreen) subScreen).write(terminal);
	    	 ((InventoryScreen) subScreen).displayOutput(terminal);
	     }
	     if(subScreen instanceof InspectScreen)
	    	 ((InspectScreen) subScreen).displayOutput(terminal);
	    	    
	     
	     displayMessages(terminal, messages);
	}
    public void renderStats(AsciiPanel terminal)
    {
    	int statsW = screenWidth;
    	int statsH = 12;
    	
    	for(int x = 0; x < statsW; x++)
    	{
    		for(int y = 0; y < statsH; y++)
    		{
    			
    			if(x == 0 || x == statsW-1)
    				terminal.write(Tile.lrWall.glyph(), x, y+screenHeight, Color.DARK_GRAY);
				else if (y == 0 || y == statsH-1)
					terminal.write(Tile.tbWall.glyph(), x, y+screenHeight,  Color.DARK_GRAY);
				else
					terminal.write(' ', x, y+screenHeight,  Color.WHITE);
    		}
    	}
    	terminal.write(Tile.tlCorner.glyph(), 0, screenHeight);
    	terminal.write(Tile.trCorner.glyph(), screenWidth-1, screenHeight);
    	terminal.write(Tile.brCorner.glyph(), screenWidth-1, statsH+screenHeight-1);
    	terminal.write(Tile.blCorner.glyph(), 0, statsH+screenHeight-1);
    	
    	terminal.write("Shield [", 1, screenHeight+ 1);
    		renderPercentBlocks(terminal, Color.GREEN, 9, screenHeight+1, player.shield(), 100);
    	terminal.write("Vitals [", 1, screenHeight+ 2);
    		renderPercentBlocks(terminal, Color.PINK, 9, screenHeight+2, player.hp(), 100);
    	terminal.write("Plasma [", 1, screenHeight+ 3);
    		renderPercentBlocks(terminal, Color.CYAN, 9, screenHeight+3, player.plasma(), 10000);
    	terminal.write("Crypto [" + player.crypto(), 1, screenHeight+ 5);

    	
    	renderLogScreenArea(terminal);
    	
    }
    public void renderLogScreenArea(AsciiPanel terminal)
    {
    	// Produces divider
    	int ox = 30;
    	
    	for (int i = 0; i < 12; i++)
    		terminal.write((char)186, ox, screenHeight + i, Color.DARK_GRAY);

    	terminal.write((char)203, ox, screenHeight , Color.DARK_GRAY);
    	terminal.write((char)202, ox, screenHeight+11, Color.DARK_GRAY );
    	// Updates display array

    		
    }
    public void renderPercentBlocks(AsciiPanel terminal, Color color, int x, int y, int value, int max)
    {
    	double percent = 10 * ((double)value/max);
    	double xi;

    	
    	for(int i = 0; i < percent; i++)
    	{
    		terminal.write((char) 223, x+i, y, color);
    		xi=i;
    	}
    	terminal.write(" "+ String.format("%.2f",percent*10) + " %", color);
    }
    public void renderTarget(AsciiPanel terminal)
    {
    	if(player.lastTargetedEnemy != null && player.lastTargetedEnemy.hp() > 0)
    	{
        	for(int x = 0; x < 25; x++)
        	{
        		for(int y = screenHeight-5; y < screenHeight-2; y++)
        		{
        			
        			if(x == 0 || x == 25-1)
        				terminal.write(Tile.lrWall.glyph(), x, y, Color.DARK_GRAY);
    				else if (y == screenHeight-5 || y == screenHeight-2-1)
    					terminal.write(Tile.tbWall.glyph(), x, y,  Color.DARK_GRAY);
    				else
    					terminal.write(' ', x, y,  Color.WHITE);
        		}
        	}
        	
        	terminal.write("Target /", 1, screenHeight-4);
        	terminal.write(player.lastTargetedEnemy.name(), player.lastTargetedEnemy.color());
        	terminal.write("/ " +player.lastTargetedEnemy.hp() + " hp");
        	
        	
        	terminal.write(Tile.tlCorner.glyph(), 0, screenHeight-5);
        	terminal.write(Tile.trCorner.glyph(), 24, screenHeight-5);
        	terminal.write(Tile.brCorner.glyph(), 24, -2+screenHeight-1);
        	terminal.write(Tile.blCorner.glyph(), 0, -2+screenHeight-1);
    	}
    }
    public int getScrollX() 
    {
        return Math.max(0, Math.min(player.x - screenWidth / 2, world.width() - screenWidth));
    }
    public int getScrollY() 
    {
        return Math.max(0, Math.min(player.y - screenHeight / 2, world.height() - screenHeight));
    }
    private boolean userIsTryingToExit()
    {
        return player.z == 0 && world.tile(player.x, player.y, player.z) == Tile.STAIRS_EXIT;
    }
    private Screen userExits()
    {
        for (Item item : player.inventory().getItems())
        {
            if (item != null && item.name().equals("Teddy Bear"))
                return new WinScreen();
        }
        return new LoseScreen();
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
    		
    		messages.add("Save succesfull");
        } catch (IOException e) 
    	{
        	e.printStackTrace();
        	messages.add("Save failed");
        }
       
    }
	@Override
	public Screen respondToUserInput(KeyEvent key) 
	{	
		if(screenTicks < introMessages.size())
		{
			messages.add(introMessages.get(screenTicks++));
			return this;
		}
		else 
		{
			if(subScreen!= null)
			{
				subScreen = subScreen.respondToUserInput(key);
			}
			else
			{
				switch (key.getKeyCode())
				{
				// Special Keys
        		case KeyEvent.VK_ESCAPE: saveGame(); break;
        		case KeyEvent.VK_ENTER: return new WinScreen();
        		case KeyEvent.VK_F: player.pickup(); break;
        		case KeyEvent.VK_E: player.rotateClockwise(); break;
        		case KeyEvent.VK_Q: player.rotateCounterClockwise(); break;
        		case KeyEvent.VK_SPACE: player.useWeapon(); break;
        		case KeyEvent.VK_T: subScreen = new CraftingScreen(player, terminal, true); break;
        		case KeyEvent.VK_C: // Inspect item
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
        		// Planar Scrolling
        		case KeyEvent.VK_LEFT:
        		case KeyEvent.VK_A: player.moveBy(-1, 0, 0); break;
        		case KeyEvent.VK_RIGHT:
        		case KeyEvent.VK_D: player.moveBy( 1, 0, 0); break;
        		case KeyEvent.VK_UP:
        		case KeyEvent.VK_W: player.moveBy( 0,-1, 0); break;
        		case KeyEvent.VK_DOWN:
        		case KeyEvent.VK_S: player.moveBy( 0, 1, 0); break;
        			
        		// Diagonal scrolling
        		case KeyEvent.VK_J: player.moveBy( 0, 1, 0); break;
        		case KeyEvent.VK_Y: player.moveBy(-1,-1, 0); break;
        		case KeyEvent.VK_U: player.moveBy( 1,-1, 0); break;
        		case KeyEvent.VK_B: player.moveBy(-1, 1, 0); break;
        		case KeyEvent.VK_N: player.moveBy( 1, 1, 0); break;
        		

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
		if(subScreen == null)
			world.update();
	
		if (player.hp() < 1)
		    return new LoseScreen();
		
		return this;	
	}
}
