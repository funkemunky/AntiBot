package dev.brighten.bot.data.impl;

import dev.brighten.bot.data.CaptchaCompletion;
import dev.brighten.bot.data.DataStructure;
import dev.brighten.bot.data.config.MySQLConfig;
import dev.brighten.bot.data.sql.MySQL;
import dev.brighten.bot.data.sql.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

public class MySQLStructure implements DataStructure {

    @Override
    public void getAllCompletions(UUID uuid, Consumer<List<CaptchaCompletion>> result) {

    }

    @Override
    public void getLatestCompletion(UUID uuid, Consumer<Optional<CaptchaCompletion>> result) {

    }

    @Override
    public void catpchaCompletion(UUID uuid, String ip, long timestamp) {

    }

    @Override
    public void startup() {
        if(MySQLConfig.enabled) {
            System.out.println("Starting connection to MySQL...");
            MySQL.init();
        } else {
            System.out.println("Initializing SQLLite database...");
            MySQL.initSqlLite();
        }

        System.out.println("Setting up MySQL tables...");
        Query.prepare("create table if not exists `completions` (`uuid` varchar(36) not null," +
                " `ip` varchar(64), `time` timestamp)").execute();
        Query.prepare("create index if not exists `uuid_1` on `completions`")
    }

    @Override
    public void shutdown() {

    }
}
