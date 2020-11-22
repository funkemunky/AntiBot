package dev.brighten.bot.listener;

import cc.funkemunky.api.utils.Init;
import dev.brighten.bot.Antibot;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@Init
public class LoginListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Antibot.INSTANCE.userHandler.createUser(event.getPlayer().getUniqueId());

        Antibot.INSTANCE.userHandler.getUser(event.getPlayer().getUniqueId()).ifPresent(user -> {
            if(event.getPlayer().hasPermission("antibot.bypass")) {
                user.confirmed = true;
                Antibot.INSTANCE.userHandler.confirm(event.getPlayer().getUniqueId());
            } else if(!Antibot.INSTANCE.userHandler.isConfirmed(event.getPlayer().getUniqueId())) {
                user.startConfirmation();
            }
        });
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Antibot.INSTANCE.userHandler.getUser(event.getPlayer().getUniqueId()).ifPresent(user -> {
            if(!user.confirmed) user.cancelConfirmation();
        });
        Antibot.INSTANCE.userHandler.removeUser(event.getPlayer().getUniqueId());
    }
}
