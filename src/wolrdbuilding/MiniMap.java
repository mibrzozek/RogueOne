package wolrdbuilding;

import entities.Entity;
import entities.Line;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MiniMap implements Serializable
{
    private TileV[][][] miniMap;
    private TileV[][][] tiles;

    Point circlePoint;

    private World w;

    private static final int MINI_CELL  = 3;

    private int width;
    private int height;
    private int depth;

    private int miniW;
    private int miniH;

    private List<Point> insideCircle;

    private Entity player;
    private Point playerCell;

    public MiniMap(World w, int displayZ)
    {
        this.w = w;

        this.width = w.width();
        this.height = w.height();
        this.depth = w.depth();

        this.miniW = width/MINI_CELL;
        this.miniH = height/MINI_CELL;

        this.miniMap = new TileV[miniW][miniH][depth];
        this.tiles = w.getTileMap();
        this.player = w.getPlayer();
        this.playerCell = new Point(0, 0, 0);
        this.insideCircle = new ArrayList<>();

        makeMiniMap(tiles, displayZ);
    }

    public MiniMap(World w)
    {
        this.w = w;

        this.width = w.width();
        this.height = w.height();
        this.depth = w.depth();

        this.miniW = width/MINI_CELL;
        this.miniH = height/MINI_CELL;

        this.miniMap = new TileV[miniW][miniH][depth];
        this.tiles = w.getTileMap();
        this.player = w.getPlayer();
        this.playerCell = new Point(0, 0, 0);
        this.insideCircle = new ArrayList<>();

        makeMiniMap(tiles, player.z);
        //makeCircle(w.getCircleRadius(), w.getCirclePoint());
    }

    public TileV[][][] getTileMiniMap()
    {
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
        //makeCircle(w.getCircleRadius(), w.getCirclePoint());

        int px = (player.x/3) ;
        int py = (player.y/3) ;
        if(px < 0)
        {
            px = 0;
        }
        else if(py < 0)
        {
            py = 0;
        }

        System.out.println(px + " " + py + "  these are the players x and y" );
        this.playerCell = new Point(px, py, 0);
        TileV t = new TileV(Tile.PLAYER);
        //t.setBackColor(Palette.purple);
        miniMap[px][py][player.z] = t;
    }

    public void makeCircle(int r, Point wps)
    {
        int wx = 30, wy = 30;

        Point  ps = wps;

        wx = ps.x;
        wy = ps.y;

        insideCircle.clear();

        for (int x = -r; x < r; x++)
        {
            for (int y = -r; y < r; y++)
            {
                if (x*x + y*y > r*r)
                    continue;

                if (wx + x < 0 || wx + x >= miniW
                        || wy + y < 0 || wy + y >= miniH)
                    continue;

                for (Point  p : new Line(wx, wy, wx + x, wy + y))
                {
                    TileV tile = miniMap[p.x][ p.y][0];

                    if(!tile.getTile().equals(Tile.PLAYER))
                        tile.setBackColor(Palette.white);

                    //tile.setTile(Tile.getFire());
                    miniMap[p.x][p.y][0] = tile;


                    insideCircle.add(p);
                }
            }
        }
        System.out.println("Done with circle  "  + insideCircle.size());
    }
    public void makeMiniMap(TileV[][][] tiles, int playerZ)
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
        majority = 1;

        for(int z = 0; z < depth; z++)
        {
            for (int y = 0; y < height; y += MINI_CELL)
            {
                for (int x = 0; x < width; x += MINI_CELL)
                {

                    Boolean isPlayer = false;
                    Point p = new Point(x, y, playerZ);
                    int floorCount = 0;
                    List<Point> cell = p.gridXbyX(p, boxWidth, boxWidth);
                    //System.out.println("This is a point " + p.toString());
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

                            if (tiles[p.x][p.y][p.z].getTile().isGround() || tiles[p.x][p.y][p.z].getTile().isDoor()
                                || tiles[p.x][p.y][p.z].getTile().isRoom())
                                floorCount++;

                            if (player != null)
                            {
                                if (cp.equals(new Point(player.x, player.y, player.z)))
                                {
                                    isPlayer = true;
                                }
                            }
                        }
                        if (floorCount > majority)
                        {
                            miniMap[miniX][miniY][playerZ] = new TileV(Tile.FLOOR);
                        } else
                        {
                            miniMap[miniX][miniY][playerZ] = new TileV(Tile.RED_WALL);
                        }

                        if (isPlayer)
                        {
                            miniMap[miniX][miniY][z] = new TileV(Tile.PLAYER);
                            playerCell = new Point(miniX, miniY, playerZ);
                        }
                    }
                    //System.out.println(playerCell.toString() + " this is  player cell");
                    if (miniX < 66)
                        miniX++;
                    else
                        miniX = 0;

                    if (miniX == 65)
                    {
                        miniY++;
                    }
                }
            }
        }
        int px = (player.x/3) - 1;
        int py = (player.y/3) - 1;
        if(px < 0)
        {
            px = 0;
        }
        else if(py < 0)
        {
            py = 0;
        }

        System.out.println(px + " " + py + "  these are the players x and y" );
        //this.playerCell = new Point(px, py, 0);
        //TileV t = new TileV(Tile.PLAYER);
        //t.setBackColor(Palette.purple);
        //miniMap[px][py][playerZ] = t;
    }



    /*
            **************** STATIC METHODS ******************
            *
            *   getNewMiniMap() used for dungeon creation in smooth()
     */
    public static TileV[][][] getNewMiniMap(TileV[][][] tiles, int width, int height, int depth, int cellSize)
    {
        int miniW = width/cellSize + 1;
        int miniH = height/cellSize + 1;

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
            miniX = 0;
            miniY = 0;
            for (int y = 0; y < height; y += MINI_CELL)
            {
                for (int x = 0; x < width; x += MINI_CELL)
                {
                    Boolean isPlayer = false;
                    Point p = new Point(x, y, z);
                    int floorCount = 0;
                    List<Point> cell = p.gridXbyX(p, boxWidth, boxWidth);
                    //System.out.println("This is a point " + p.toString());

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
                }
                if(miniY + 1 < miniH)
                    y++;
            }
        }


        return newMiniMap;
    }

    public MiniMap getTilesFromZ(int newZ)
    {
        return new MiniMap(w, newZ);
    }
}
