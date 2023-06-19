package jh.test;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){

        if(event.getBlockPlaced().getType() == Material.GLOWSTONE){
            Location blockLoc = event.getBlockPlaced().getLocation();
            World world = blockLoc.getWorld();

                if(world.getBlockAt(blockLoc.subtract(0,1,0)).getType() == Material.OBSIDIAN){

                    world.getBlockAt(blockLoc.add(0, 1, 0)).setType(Material.AIR);
                    world.getBlockAt(blockLoc.subtract(0,1,0)).setType(Material.AIR);
                    Enderman enderman = (Enderman) world.spawnEntity(blockLoc.add(0, 1, 0), EntityType.ENDERMAN);

                    if(world.getBlockAt(blockLoc.subtract(1,1,0)).getType() == Material.CHEST || world.getBlockAt(blockLoc.subtract(0,1,1)).getType() == Material.CHEST || world.getBlockAt(blockLoc.subtract(0,1,0).add(0,0,1)).getType() == Material.CHEST || world.getBlockAt(blockLoc.subtract(0,1,0).add(1,0,0)).getType() == Material.CHEST  ) {
                        Chest chest = (Chest)world.getBlockAt(blockLoc).getState();
                        Inventory chestInv = chest.getBlockInventory();

                        ItemStack firstItem = chestInv.getItem(0);

                        if(firstItem != null)
                        {
                            enderman.setCarriedBlock(firstItem.getType().createBlockData());
                        }

                        else if(firstItem == null)
                        {
                            enderman.setCarriedBlock(Material.EMERALD_BLOCK.createBlockData());
                        }
                        chestInv.clear();
                    }

                    Player player = event.getPlayer();
                    event.getBlock().setType(Material.AIR);
                    world.getBlockAt(blockLoc.subtract(0, 1, 0)).setType(Material.AIR);
                    world.getBlockAt(blockLoc.add(1,1,0)).setType(Material.AIR);

                    enderman.setInvulnerable(true);

                    PotionEffectType potionEffectType = PotionEffectType.WEAKNESS;
                    PotionEffect potionEffect = new PotionEffect(potionEffectType, Integer.MAX_VALUE, 99999999);

                    enderman.setGlowing(true);
                    enderman.setAware(false);
                    enderman.setTarget(player);
                    enderman.addPotionEffect(potionEffect);
                    enderman.setAware(true);
                    enderman.setLeashHolder(player);
                    enderman.setCustomName(player.getName() + "'s Pet");
                    enderman.setCustomNameVisible(true);
                    enderman.setCanPickupItems(false);


                    player.sendMessage("Your Pet was spawned! :)");
                }

            }
        }
}
