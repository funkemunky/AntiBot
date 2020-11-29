package dev.brighten.bot.handler;

import cc.funkemunky.api.utils.RunUtils;
import lombok.Getter;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class UserHandler implements Runnable {

    @Getter
    private final Map<UUID, User> userMap = Collections.synchronizedMap(new HashMap<>());
    private final List<UUID> confirmedUUIDs = Collections.synchronizedList(new ArrayList<>());

    public UserHandler() {
        task = RunUtils.taskTimerAsync(this, 40L, 60L);
    }

    public BukkitTask task;

    public Optional<User> getUser(UUID uuid) {
        return Optional.ofNullable(userMap.get(uuid));
    }

    public void createUser(UUID uuid) {
        synchronized (userMap) {
            userMap.put(uuid, new User(uuid));
        }
    }

    public void removeUser(UUID uuid) {
        synchronized (userMap) {
            userMap.remove(uuid);
        }
    }

    public void confirm(UUID uuid) {
        synchronized (confirmedUUIDs) {
            confirmedUUIDs.add(uuid);
        }
    }

    public boolean isConfirmed(UUID uuid) {
        return confirmedUUIDs.contains(uuid);
    }

    @Override
    public void run() {
        for (User value : userMap.values()) {
            if(value.confirmed) continue;

            RunUtils.task(() -> {
                value.player.teleport(value.player.getLocation());
            });
            value.checkIfConfirmed();
        }
    }
}
