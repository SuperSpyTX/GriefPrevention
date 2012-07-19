package me.ryanhamshire.GriefPrevention;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class RestoreGriefTask implements Runnable
{
    private Location loc1 = null;
    private Location loc2 = null;
    private World world = null;

    public RestoreGriefTask(World w, Location l1, Location l2)
    {
        world = w;
        loc1 = l1;
        loc2 = l2;
    }

    @Override
    public void run()
    {
        int iz = loc1.getBlockZ();
        int tz = loc2.getBlockZ() + 1;
        int ix = loc1.getBlockX();
        int tx = loc2.getBlockX() + 1;
        // TODO Auto-generated method stub
        for (int y = 0; y < 256; y++)
        {
            for (int z = iz; z < tz; z++)
            {
                for (int x = ix; x < tx; x++)
                {
                    Block block = (new Location(world, x, y, z)).getBlock();
                    // perfect.
                    if (y == 0)
                    {
                        block.setType(Material.BEDROCK);
                    }
                    else if (y == 1 || y == 2)
                    {
                        block.setType(Material.DIRT);
                    }
                    else if (y == 3)
                    {
                        block.setType(Material.GRASS);
                    }
                    else if (y > 3)
                    {
                        if (block.getTypeId() != 0)
                            block.setTypeId(0);
                    }
                }
            }
        }
    }

}
