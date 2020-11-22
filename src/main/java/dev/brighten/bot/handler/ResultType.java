package dev.brighten.bot.handler;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ResultType {

    SUCCESS(false),
    INVALID_ID(true),
    JSON_ERROR(true),
    AWAITING_CONFIRMATION(true),
    INVALID_UUID(true);

    public final boolean error;
}
