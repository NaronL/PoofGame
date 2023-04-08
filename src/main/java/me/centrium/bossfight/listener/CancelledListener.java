package me.centrium.bossfight.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.CraftItemEvent;

public class CancelledListener implements Listener {

    @EventHandler
    public void onExplode(BlockExplodeEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void onCraftItem(CraftItemEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockPhysics(BlockPhysicsEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBurnBlock(BlockBurnEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event){
        switch (event.getCause()){
            case FALL:
                event.setCancelled(true);
                break;
            case VOID:
                event.setDamage(1000000);
                break;
        }
    }
}
