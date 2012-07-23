package me.ryanhamshire.GriefPrevention;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class RestoreGriefTask implements Runnable
{
    private Location loc1 = null;
    private Location loc2 = null;
    private World world = null;
    private Claim claim = null;
    private Player player = null;

    public RestoreGriefTask(World w, Location l1, Location l2, Claim cl, Player pl)
    {
        world = w;
        loc1 = l1;
        loc2 = l2;
        claim = cl;
        player = pl;
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
                        block.setTypeId(0);
                    }
                }
            }
        }

        // clear any entities

        //clean up entities in the player's location
        List<Entity> entities3 = player.getNearbyEntities(50, 50, 50);
        for (Entity entity : entities3)
        {
            if (!(entity instanceof Player))
            {
                boolean removal = false;
                boolean inownedclaim = false;

                if (GriefPrevention.instance.dataStore.getClaimAt(entity.getLocation(), true, claim) != null)
                {
                    Claim thi = GriefPrevention.instance.dataStore.getClaimAt(entity.getLocation(), true, claim);
                    if (thi.getID() == claim.getID())
                        removal = true;

                    if (!removal)
                        inownedclaim = true;
                }

                if (claim.isNear(entity.getLocation(), 30) && !removal && !inownedclaim)
                    removal = true;

                if (removal)
                    entity.remove();
            }
        }
    }

}
