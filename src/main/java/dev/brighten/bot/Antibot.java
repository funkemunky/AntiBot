package dev.brighten.bot;

import cc.funkemunky.api.Atlas;
import cc.funkemunky.api.commands.ancmd.CommandManager;
import cc.funkemunky.api.utils.MathUtils;
import cc.funkemunky.api.utils.MiscUtils;
import dev.brighten.bot.handler.User;
import dev.brighten.bot.handler.UserHandler;
import dev.brighten.db.utils.security.hash.Hash;
import dev.brighten.db.utils.security.hash.HashType;
import dev.brighten.db.utils.security.hash.impl.SHA1;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class Antibot extends JavaPlugin {

    public static Antibot INSTANCE;

    public SHA1 sha1;
    public UserHandler userHandler;

    public void onEnable() {
        enable("plugin instance", () -> INSTANCE = Antibot.this);
        enable("Atlas scanner", () -> Atlas.getInstance()
                .initializeScanner(INSTANCE, true, true));
        enable("SHA-1 API", () -> {
            Hash.loadHashes();
            sha1 = Hash.getHashByType(HashType.SHA1);
        });
        enable("UserHandler", () -> userHandler = new UserHandler());
    }

    public void onDisable() {
        disable("User checking", () -> {
            userHandler.getUserMap().values().stream().filter(user -> !user.confirmed)
                    .forEach(User::cancelConfirmation);
            userHandler.task.cancel();
            userHandler = null;
        });
        disable("Listeners", () -> HandlerList.unregisterAll(INSTANCE));
        disable("Tasks", () -> Bukkit.getScheduler().cancelTasks(INSTANCE));
    }

    private void enable(String string, Runnable runnable) {
        MiscUtils.printToConsole("&7Enabling &e" + string + "&7...");
        long start = System.nanoTime();
        runnable.run();
        long complete = System.nanoTime() - start;
        MiscUtils.printToConsole("&aEnabled &7(took "
                + MathUtils.round(complete / 1E6D, 3) + "ms)&a!");
    }

    private void disable(String string, Runnable runnable) {
        MiscUtils.printToConsole("&7Disabling &e" + string + "&7...");
        long start = System.nanoTime();
        runnable.run();
        long complete = System.nanoTime() - start;
        MiscUtils.printToConsole("&cDisabled &7(took "
                + MathUtils.round(complete / 1E6D, 3) + "ms)&c!");
    }

    public CommandManager getCommandManager() {
        return Atlas.getInstance().getCommandManager(this);
    }
}
