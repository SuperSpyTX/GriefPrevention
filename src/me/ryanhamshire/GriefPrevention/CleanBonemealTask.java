package me.ryanhamshire.GriefPrevention;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class CleanBonemealTask implements Runnable
{
    private Location location;
    private List<Location> suspected;
    private PlayerData player;
    private Player pa;

    public CleanBonemealTask(Location l, PlayerData pl)
    {
        location = l;
        player = pl;
    }

    public void test(Player pl)
    {
        pa = pl;
    }

    public void run()
    {
        Claim cache = null;
        for (Location location : this.suspected)
        {
            Block lo = location.getBlock();

            if (lo == null)
                continue;

            if (lo.getTypeId() == 0)
                continue;

            if (lo.getTypeId() == 31 || lo.getTypeId() == 37 || lo.getTypeId() == 38)
            {
                Claim claim = GriefPrevention.instance.dataStore.getClaimAt(lo.getLocation(), true, cache);

                if (claim == null)
                    lo.setTypeId(0);
                else if (claim.isAdminClaim())
                    lo.setTypeId(0);
                else if (!claim.ownerName.equalsIgnoreCase(player.playerName))
                    lo.setTypeId(0);

                // this way people can't grief with bonemeal and delete other people's grass.

                cache = claim; // save to cache, so we can save some time :)

            }
        }

        player.isProcessing = false;
    }

    public void evaluateLocation()
    {
        suspected = new ArrayList<Location>();
        int tx = location.getBlockX() + 10;
        int lx = location.getBlockX() - 10;
        int tz = location.getBlockZ() + 10;
        int lz = location.getBlockZ() - 10;

        for (int z = lz; z < tz; z++)
        {
            for (int x = lx; x < tx; x++)
            {
                Block lo = new Location(location.getWorld(), x, location.getBlockY() + 1, z).getBlock();

                if (lo == null)
                    continue;

                if (lo.getTypeId() == 31 || lo.getTypeId() == 37 || lo.getTypeId() == 38)
                {
                    continue;
                }
                else
                {
                    // only add if there is no 31,37, and 38 there.
                   // pa.sendBlockChange(lo.getLocation(), Material.WEB, (byte) 0); // debug
                    suspected.add(lo.getLocation());
                }
            }

        }
    }

}
