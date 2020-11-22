package dev.brighten.bot.listener;

import cc.funkemunky.api.utils.Init;
import dev.brighten.bot.Antibot;
import lombok.val;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

@Init
public class BukkitCancelListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        val user = Antibot.INSTANCE.userHandler.getUser(event.getPlayer().getUniqueId());

        if(user.isPresent() && !user.get().confirmed) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEvent(InventoryClickEvent event) {
        val user = Antibot.INSTANCE.userHandler.getUser(event.getWhoClicked().getUniqueId());

        if(user.isPresent() && !user.get().confirmed) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEvent(PlayerPickupItemEvent event) {
        val user = Antibot.INSTANCE.userHandler.getUser(event.getPlayer().getUniqueId());

        if(user.isPresent() && !user.get().confirmed) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEvent(PlayerDropItemEvent event) {
        val user = Antibot.INSTANCE.userHandler.getUser(event.getPlayer().getUniqueId());

        if(user.isPresent() && !user.get().confirmed) {
            event.setCancelled(true);
        }
    }
}
