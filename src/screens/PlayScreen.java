package screens;

import asciiPanel.AsciiPanel;
import entities.*;
import entities.entityAI.DroidAi;
import items.Item;
import items.ItemFactory;
import items.Stash;
import items.Type;
import structures.*;
import wolrdbuilding.Point;
import wolrdbuilding.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayScreen implements Screen
{
	private static final String WINNING_ITEM = "Rifle";
	private MainFrame main;

	private Color fore = Theme.PASTEL.getFore();
	private Color back = Theme.PASTEL.getBack();

	private Console console;

	private World world;
	private int centerX;
    private int centerY;
    private int screenWidth;
    private int screenHeight;
	private int screenTicks = 0;

	private int playAreaOffset = 0;

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
    private World.Map map;

    private transient AsciiPanel terminal;
	private int displayHeight, displayWidth;

	/**
     * This screen is responsible for keeping track of all objects related to playing the game. 
     * The PlayScreen is the only objects which needs to be saved and loaded, making the saving
     * system easier to implement than expected.
     */

    public PlayScreen(Statistics stats, MainFrame main, World.Map m)
    {
        screenWidth = main.getScreenWidth();
        screenHeight = main.getScreenHeight();
		displayHeight = main.getDisplayHeight();
		displayWidth = main.getDisplayWidth();

		playAreaOffset = 0;

        this.main = main;
        this.map = m;
        
        introMessages = new ArrayList<String>();
        introMessages.add("You wake from a deep slumber.");
        introMessages.add("The ground is wet, and the air smells of metal. You hear a sound and are startled by it.");
        messages = new ArrayList<String>();

        createWorld(m);
    
        fov = new FieldOfView(world);
        EntityFactory entityFactory = new EntityFactory(world, fov);
        ItemFactory itemFactory = new ItemFactory(null);
        player = entityFactory.newPlayer(messages, stats);
        world.addPlayer(player);
		world.addEntityAt(Point.transform(Direction.SOUTH, player.point()), entityFactory.newDroidSkeleton(0, player));
        console = new Console(world, this);

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
		world.generateLockedRoomLoot();
    }
	private void createEntities(EntityFactory entityFactory, ItemFactory itemFactory)
	{
		world.spawnEnemies();
		world.dealEnemiesLoot();
		world.spawnRedRoomEnemies();
		world.spawnRogues();
	}
    private void createWorld(World.Map m)
    {
    	// Sets World size
    	// Makes caves, castles, loot and enemies
        world = new PlanetPrinter(200 ,200 , 5
				, player)
        			.makeDungeons(m)
        			.build();

    }
	private void displayTiles(AsciiPanel terminal, int left, int top)
	{
		fov.update(player.x, player.y, player.z, player.visionRadius());
		
		for(int x = playAreaOffset; x < main.getScreenWidth(); x++)
		{
			for (int y = 0; y < main.getDisplayHeight(); y++)
			{
				int wx = x + left - playAreaOffset;
				int wy = y + top;
				//System.out.println(wx + " " + wy + " " + " in display tiles");
				TileV t = world.tile(wx, wy, player.z);
				Entity e = world.entity(wx, wy, player.z);
				Item i =  world.item(wx, wy, player.z);

				char glyph = 'X';

				glyph = world.glyph(wx, wy, player.z);

				if(i != null)
					glyph = i.glyph();
				if(e != null) {
					glyph = e.tile().glyph();
				}

	            if(player.canSee(wx, wy, player.z))
	            {
	            	//if(player.inventory().getTypeDuration(Type.HELMET) > 0)
	            	//{
						if(e != null)
							terminal.write(glyph, x, y, e.tile().color(), t.getBackColor());
						else if(i!=null)
							terminal.write(glyph, x, y, i.color(), t.getBackColor());
						else
							terminal.write(world.glyph(wx, wy, player.z), x, y, t.getColorF(), t.getBackColor());
					//}
	            	//else
					//	terminal.write(glyph, x, y, fore, back);

	                //world.color(wx, wy, player.z)
	            }
	            else
				{
					if(fov.tile(wx, wy, player.z) != null)
						terminal.write(fov.tile(wx, wy, player.z).glyph(), x, y, fore);
					else
						terminal.write(' ', x, y);
				}

			}
		}
		terminal.write(player.tile().glyph(), player.x-left+playAreaOffset, player.y-top, Tile.PLAYER.color());
	}
    @Override
	public void displayOutput(AsciiPanel terminal) 
	{
	     leftOffset = getScrollX();
	     topOffset = getScrollY();

	     displayTiles(terminal, leftOffset , topOffset);
	     TileEngine.displayStatsUI(terminal, main.getDisplayWidth(), main.getDisplayHeight(), 0, 0, world);
		 TileEngine.displayMessagesUI(terminal, messages, main.getScreenWidth(), main.getDisplayHeight());
		 TileEngine.displayDynamicEnemyPopUp(terminal, world, main);

	     if(subScreen instanceof CraftingScreen)
	    	 ((CraftingScreen) subScreen).write(terminal);
	     /*
	     if(subScreen instanceof TradeScreen)
	     {
	    	 ((TradeScreen) subScreen).displayOutput(terminal);
	    	 ((TradeScreen) subScreen).write(terminal); 
	     }
	     */
	     if(subScreen instanceof NEscapeScreen)
	     {
	    	 ((NEscapeScreen) subScreen).displayOutput(terminal);
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
	     if(subScreen instanceof ThrowableScreen)
	    	 ((ThrowableScreen) subScreen).displayOutput(terminal);
	     if(subScreen instanceof AnimationScreen)
	    	 ((AnimationScreen) subScreen).displayOutput(terminal);
	     if(subScreen instanceof InteractScreen)
	    	 ((InteractScreen) subScreen).displayOutput(terminal);
		 if(subScreen instanceof MapScreen)
			((MapScreen) subScreen).displayOutput(terminal);
		 if(subScreen instanceof DoorScreen)
			((DoorScreen) subScreen).displayOutput(terminal);
		if(subScreen instanceof EntityInteractScreen)
			((EntityInteractScreen) subScreen).displayOutput(terminal);
		if(subScreen instanceof AttackBox)
			((AttackBox) subScreen).displayOutput(terminal);
		if(subScreen instanceof StashScreen)
			((StashScreen) subScreen).displayOutput(terminal);
	}
	public int getLeftOffset() { return leftOffset;}
	public int getTopOffset() { return topOffset;}
    public int getScrollX() 
    {
        return Math.max(0, Math.min(player.x - main.getDisplayWidth() / 2, world.width() - main.getDisplayWidth()));
    }
    public int getScrollY()
	{
		return Math.max(0, Math.min(player.y - main.getDisplayHeight() / 2, world.height() - main.getDisplayHeight()));
	}
    private boolean userIsTryingToExit()
    {
    	return true;

        //return world.tile(player.x, player.y, player.z).getTile() == Tile.STAIRS_EXIT;
    }
    private Screen userExits()
    {
		System.out.println("User is exiting");
        for (Item item : player.inventory().getItems())
        {
            if (item != null && item.name().equals(WINNING_ITEM))
                return new WinScreen(terminal, main, player, world);
        }
		for (Item item : player.inventory().getEquipped())
		{
			if (item != null && item.name().equals(WINNING_ITEM))
				return new WinScreen(terminal, main, player, world);
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
					case KeyEvent.VK_M:
						if(player.inventory().isItemEquiped(new ItemFactory().newTerrainMapper()))
						{
							subScreen = new MapScreen(terminal, this.world, main.getScreenWidth(), main.getScreenHeight());
							break;
						}
						else
						{
							messages.add("You need a terrain mapper!");
							break;
						}
					case KeyEvent.VK_SHIFT: subScreen = new CharacterSheet(world, main); 	break;
				case KeyEvent.VK_T:
				{
					if(player.inventory().getTypeDuration(Type.UTILITY) > 0)
						subScreen = new ThrowableScreen(player, this, main);
					break;
				}
				case KeyEvent.VK_L:
					subScreen = new KeyInputScreen(terminal,this, 20, 15, 8, null); break;
        		case KeyEvent.VK_ESCAPE:
					subScreen = new NEscapeScreen(player,this, main); break;
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
							|| world.getTile(np.x, np.y, np.z).equals(Tile.CLOSED_DOOR)) // DOOR INTERACTION
					{
						player.setDoorPoint(np);

						int doorX  = 0;

						if(player.x - leftOffset + 1 + 10 < screenWidth) // checks to see if enough room for door screen
							doorX = player.x - leftOffset + 1;
						else
							doorX = player.x - leftOffset - 7;

						subScreen = new DoorScreen(world, 7, 4, doorX, player.y - topOffset -1, this, main);
					}
					else if(world.entity(np.x, np.y, np.z) != null) // ENTITY INTERACTION
					{
						Entity e  = world.entity(np.x, np.y, np.z);
						if(e.getEntityAi() instanceof DroidAi)
						{
							subScreen = new EntityInteractScreen(world, this, main, e);
						}
					}
					else if(world.getTile(np.x, np.y, np.z).equals(Tile.STASH)
							|| world.getTile(np.x, np.y, np.z).equals(Tile.MED_STASH)
							|| world.getTile(np.x, np.y, np.z).equals(Tile.RED_STASH)) // STASH INTERACTION
					{
						if(world.getStashes().contains(new Stash(np)))
						{
							player.notify("You're looking at a stash, would would you like to open it?");
							subScreen = new StashScreen(world, this, main, world.getStashes().get(world.getStashes().indexOf(new Stash(np))));
						}
					}
					else if(world.getTile(np.x, np.y, np.z).equals(Tile.ROCK_0)) // ROCK MINING INTERACTION
					{
						if(player.inventory().getTypeDuration(Type.MINING) > 30)
						{
							world.changeTile(np, Tile.GRASS_0, false);
						}
					}
                    else if(world.getTile(np.x, np.y, np.z).equals(Tile.TREE_0)) // WOOD CHOPPING INTERACTION
                    {
                        if(player.inventory().getTypeDuration(Type.AXE) > 30)
                        {
                            world.changeTile(np, Tile.GRASS_0, false);
                            player.inventory().add(new ItemFactory().newTimber());
                        }
                    }

					break;
        		}
        		case KeyEvent.VK_E: player.rotateClockwise(); break;
        		case KeyEvent.VK_Q: player.rotateCounterClockwise(); break;
        		case KeyEvent.VK_SPACE: player.useWeapon(null); world.update();	 break;
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
        				subScreen = new NInventoryScreen(player, terminal, true, main.getDisplayHeight());
        			break;
        		}
        		case KeyEvent.VK_0: subScreen = new KeyInputScreen(terminal, this, 15, 2, 1, console);	break;
        		/*
        			Quick Attacking Enemies
        		 */
        		case KeyEvent.VK_1:
        		case KeyEvent.VK_2:
        		case KeyEvent.VK_3:
        		case KeyEvent.VK_4:
        		case KeyEvent.VK_5:
        		case KeyEvent.VK_6:
        		case KeyEvent.VK_7:
        		case KeyEvent.VK_8:
        		case KeyEvent.VK_9:
					char ascii = key.getKeyChar();
					String as = new String(String.valueOf(ascii));
					int i = Integer.parseInt(as) - 1;
					if(i >= player.fov().getEntities().size())
						break;
					subScreen = new AttackBox(player, 31, 5, 31, main.getDisplayHeight() - 5, i, this, main);
					break;
					/*
				 Player movement
				*/
					case KeyEvent.VK_NUMPAD5:// stand in place
						world.update();
						break;
        		case KeyEvent.VK_NUMPAD4:
        		case KeyEvent.VK_LEFT:
        		case KeyEvent.VK_A:
							player.setDirection(6);
        					if(player.x - 1 < 0)
							{

							}
							else
        						player.moveBy(-1, 0, 0);
							world.update();

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
				case '<': case '`':
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
	public void newDungeon()
	{
		world = new PlanetPrinter(150 ,150 , 5, player)
				.makeDungeons(map)
				.build();

		world.addAtEmptyLocation(0, player);
	}

	public int getPlayAreaOffset()
	{
		return playAreaOffset;
	}

	public int getScreenHeight()
	{
		return screenHeight;
	}

	public int getDisplayHeight()
	{
		return displayHeight;
	}
}
