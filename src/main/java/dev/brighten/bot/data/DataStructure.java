package dev.brighten.bot.data;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

public interface DataStructure {

    void getAllCompletions(UUID uuid, Consumer<List<CaptchaCompletion>> result);

    void getLatestCompletion(UUID uuid, Consumer<Optional<CaptchaCompletion>> result);

    void catpchaCompletion(UUID uuid, String ip, long timestamp);

    void startup();

    void shutdown();
}
