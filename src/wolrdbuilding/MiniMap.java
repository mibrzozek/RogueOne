package wolrdbuilding;

import entities.Entity;

import java.util.List;

public class MiniMap
{
    private TileV[][][] miniMap;
    private TileV[][][] tiles;

    private static final int MINI_CELL  = 3;

    private int width;
    private int height;
    private int depth;

    private int miniW;
    private int miniH;


    private Entity player;
    private Point playerCell;

    public MiniMap(World w)
    {
        this.width = w.width();
        this.height = w.height();
        this.depth = w.depth();

        this.miniW = width/MINI_CELL;
        this.miniH = height/MINI_CELL;

        this.miniMap = new TileV[miniW][miniH][depth];
        this.tiles = w.getTileMap();
        makeMiniMap(tiles);

        this.player = w.getPlayer();
        this.playerCell = new Point(0, 0, 0);
    }

    public TileV[][][] getTileMiniMap() {
        return miniMap;
    }
    public Point getPlayerPoint()
    {
        return playerCell;
    }
    public int miniW()  {   return  miniW;  }
    public int miniH()  {   return  miniH;  }
    public void update()
    {

    }
    public static TileV[][][] getNewMiniMap(TileV[][][] tiles, int width, int height, int depth, int cellSize)
    {

        int miniW = width/cellSize;
        int miniH = height/cellSize;

        TileV[][][] newMiniMap = new TileV[miniW][miniH][depth];

        for(int x1 = 0; x1 < width/MINI_CELL; x1++)
        {
            for (int y1 = 0; y1 < height / MINI_CELL; y1++)
            {
                for (int z = 0; z < depth; z++)
                {
                    newMiniMap[x1][y1][z] = new TileV(Tile.BOUNDS);
                }
            }
        }

        int miniX = 0, miniY = 0;
        int boxWidth = MINI_CELL;
        int majority = ((boxWidth*boxWidth)/2) ;

        for(int z = 0; z < depth; z++)
        {
            for (int y = 0; y < height; y += MINI_CELL)
            {
                for (int x = 0; x < width; x += MINI_CELL)
                {
                    Boolean isPlayer = false;
                    Point p = new Point(x, y, z);
                    int floorCount = 0;
                    List<Point> cell = p.gridXbyX(p, boxWidth);
                    System.out.println("This is a point " + p.toString());

                    //System.out.println("x : " + x + " y : " + y );
                    //System.out.println("mini x : " + miniX + " mini y : " + miniY );
                    //System.out.println("w : " + width + " height : " + height );
                    //System.out.println("w : " + width/MINI_CELL + " height : " + height/MINI_CELL );

                    if (miniX < miniW && miniY < miniH)
                    {
                        for (Point cp : cell)
                        {
                            //System.out.println("\n" + x + " " + y + " " + z);
                            //System.out.println(cell.toString());
                            if (tiles[p.x][p.y][p.z].getTile().isGround())
                                floorCount++;

                        }
                        if (floorCount > majority) {
                            newMiniMap[miniX][miniY][z] = new TileV(Tile.FLOOR);
                        } else {
                            newMiniMap[miniX][miniY][z] = new TileV(Tile.RED_WALL);
                        }
                    }
                    //System.out.println(playerCell.toString() + " this is  player cell");
                    if (miniX < miniW)
                        miniX++;
                    else
                        miniX = 0;

                    if (miniX == miniH) {
                        miniY++;
                    }
                }
            }}
        return newMiniMap;
    }
    public void makeMiniMap(TileV[][][] tiles)
    {
        for(int x1 = 0; x1 < width/MINI_CELL; x1++)
        {
            for (int y1 = 0; y1 < height / MINI_CELL; y1++)
            {
                for (int z = 0; z < depth; z++)
                {
                    miniMap[x1][y1][z] = new TileV(Tile.BOUNDS);
                }
            }
        }

        int miniX = 0, miniY = 0;
        int boxWidth = MINI_CELL;
        int majority = ((boxWidth*boxWidth)/2) ;

        for(int z = 0; z < depth; z++)
        {
            for (int y = 0; y < height; y += MINI_CELL)
            {
                for (int x = 0; x < width; x += MINI_CELL)
                {

                    Boolean isPlayer = false;
                    Point p = new Point(x, y, z);
                    int floorCount = 0;
                    List<Point> cell = p.gridXbyX(p, boxWidth);
                    System.out.println("This is a point " + p.toString());

                    //System.out.println("x : " + x + " y : " + y );
                    //System.out.println("mini x : " + miniX + " mini y : " + miniY );
                    //System.out.println("w : " + width + " height : " + height );
                    //System.out.println("w : " + width/MINI_CELL + " height : " + height/MINI_CELL );

                    if (miniX < miniW && miniY < miniH)
                    {
                        for (Point cp : cell)
                        {
                            //System.out.println("\n" + x + " " + y + " " + z);
                            //System.out.println(cell.toString());


                            if (tiles[p.x][p.y][p.z].getTile().isGround())
                                floorCount++;

                            if (player != null) {
                                if (cp.equals(new Point(player.x, player.y, player.z)))
                                {
                                    isPlayer = true;
                                }
                            }
                        }
                        if (floorCount > majority) {
                            miniMap[miniX][miniY][z] = new TileV(Tile.FLOOR);
                        } else {
                            miniMap[miniX][miniY][z] = new TileV(Tile.RED_WALL);
                        }
                        if (isPlayer) {
                            miniMap[miniX][miniY][z] = new TileV(Tile.PLAYER);
                            playerCell = new Point(miniX, miniY, z);
                        }
                    }
                    //System.out.println(playerCell.toString() + " this is  player cell");
                    if (miniX < 66)
                        miniX++;
                    else
                        miniX = 0;

                    if (miniX == 65) {
                        miniY++;
                    }
                }
            }
        /*
        for(int x1 = 0; x1 < width/MINI_CELL; x1++)
        {
            for (int y1 = 0; y1 < height / MINI_CELL; y1++)
            {
                for (int z = 0; z < depth; z++)
                {
                    miniMap[x1][y1][z] = new TileV(Tile.BOUNDS);
                }
            }
        }
        int miniX = 0, miniY = 0;

        for(int z = 0; z < depth; z++)
        {
            for(int y = 1; y < height; y += MINI_CELL)
            {
                for(int x = 1; x < width; x += MINI_CELL)
                {
                    Boolean isPlayer = false;
                    Point p = new Point(x, y, z);
                    int floorCount = 0;
                    List<Point> cell = p.neighbors8();
                    //System.out.println("This is a point " + p.toString());


                    //System.out.println("x : " + x + " y : " + y );
                    //System.out.println("mini x : " + miniX + " mini y : " + miniY );
                    //System.out.println("w : " + width + " height : " + height );
                    //System.out.println("w : " + width/MINI_CELL + " height : " + height/MINI_CELL );

                    if(miniX < width/MINI_CELL && miniY < height/MINI_CELL)
                    {
                        for (Point cp : cell)
                        {
                            //System.out.println("\n" + x + " " + y + " " + z);
                            //System.out.println(cell.toString());


                            if (tiles[p.x][p.y][p.z].getTile().isGround())
                                floorCount++;

                            if(player != null)
                            {
                                if (cp.equals(new Point(player.x, player.y, player.z)))
                                {
                                    isPlayer = true;
                                }
                            }
                        }
                        if (floorCount > 6)
                        {
                            miniMap[miniX][miniY][z] = new TileV(Tile.FLOOR);
                        }
                        else
                        {
                            miniMap[miniX][miniY][z] = new TileV(Tile.RED_WALL);
                        }
                        if(isPlayer)
                        {
                            miniMap[miniX][miniY][z] = new TileV(Tile.PLAYER);
                            playerCell = new Point(miniX, miniY, z);

                        }
                    }
                    //System.out.println(playerCell.toString() + " this is  player cell");
                    if(miniX < 66)
                        miniX++;
                    else
                        miniX = 0;

                    if(miniX == 65)
                    {
                        miniY++;
                    }
                }
            }
        }*/
        }
    }
}
