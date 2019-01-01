package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import wolrdbuilding.Point;
import wolrdbuilding.Tile;
import wolrdbuilding.World;

public class FieldOfView implements Serializable
{
    private World world;
    private int depth;

    private boolean[][] visible;
    private Tile[][][] tiles;
    
    private List<Entity> visibleEntities;

    public FieldOfView(World world)
    {
        this.world = world;
        this.visible = new boolean[world.width()][world.height()];
        this.tiles = new Tile[world.width()][world.height()][world.depth()];
        this.visibleEntities = new ArrayList<Entity>();
        
        for (int x = 0; x < world.width(); x++)
        {
            for (int y = 0; y < world.height(); y++)
            {
                for (int z = 0; z < world.depth(); z++)
                {
                    tiles[x][y][z] = Tile.UNKNOWN;
                }
            }
        }
    }
    public Tile tile(int x, int y, int z)
    {
        return tiles[x][y][z];
    }
    public boolean isVisible(int x, int y, int z)
    {
        return z == depth && x >= 0 && y >= 0 && x < visible.length && y < visible[0].length && visible[x][y];
    }
    public ArrayList getEntites()
    {
    	return (ArrayList) visibleEntities;
    }
    public void update(int wx, int wy, int wz, int r)
    {
        depth = wz;
        visible = new boolean[world.width()][world.height()];
        visibleEntities.clear();
       
        for (int x = -r; x < r; x++)
        {
            for (int y = -r; y < r; y++)
            {
                if (x*x + y*y > r*r)
                    continue;
         
                if (wx + x < 0 || wx + x >= world.width() 
                 || wy + y < 0 || wy + y >= world.height())
                    continue;
                
                for (Point p : new Line(wx, wy, wx + x, wy + y))
                {
                    Tile tile = world.tile(p.x, p.y, wz);
                    visible[p.x][p.y] = true;
                    tiles[p.x][p.y][wz] = tile;
                    
                    if(world.entity(p.x, p.y, wz) != null)
                    {
                    	Entity inFOV = world.entity(p.x, p.y, wz);
                    	if(!visibleEntities.contains(inFOV))
                    		visibleEntities.add(inFOV);
                    }
                    if (!tile.isGround())
                        break;            
        
                }
            }
        }
    }
}