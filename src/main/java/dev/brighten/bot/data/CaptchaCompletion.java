package dev.brighten.bot.data;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class CaptchaCompletion {
    public final UUID uuid;
    public final String ip;
    public final long timestamp;
}
