package dev.brighten.bot.handler;

import cc.funkemunky.api.utils.Color;
import dev.brighten.bot.Antibot;
import dev.brighten.bot.util.StringUtil;
import dev.brighten.db.utils.json.JSONException;
import dev.brighten.db.utils.json.JSONObject;
import dev.brighten.db.utils.json.JsonReader;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;
import java.util.UUID;

public class User {
    public final UUID uuid;
    public final Player player;
    public final String id;
    public boolean confirmed;

    private static String startUrl = "https://funkemunky.cc/captcha/start?uuid=%s&ip=%s",
            checkUrl = "https://funkemunky.cc//captcha/check?id=%s",
            cancelUrl = "https://funkemunky.cc//captcha/check?id=%s&cancel=true";

    public User(UUID uuid) {
        this.uuid = uuid;
        this.player = Bukkit.getPlayer(uuid);
        this.id = Antibot.INSTANCE.sha1.hash(uuid.toString());
    }

    public Optional<ResultType> checkIfConfirmed() {
        if(confirmed) return Optional.empty();

        try {
            URL url = new URL(StringUtil.formatUrl(checkUrl, id));

            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("User-Agent", "Java/1.8");

            Integer response = connection.getResponseCode();

            if(response == HttpsURLConnection.HTTP_OK) {
                JSONObject object = new JSONObject(JsonReader
                        .readAll(new BufferedReader(new InputStreamReader(connection.getInputStream()))));

                if (!object.getBoolean("error")) {
                    if(object.getBoolean("confirmed")) {
                        player.spigot().sendMessage(TextComponent
                                .fromLegacyText(Color
                                        .translate("&aYour account was confirmed.")));
                        confirmed = true;
                        Antibot.INSTANCE.userHandler.confirm(uuid);
                    }
                } else return Optional.of(ResultType.valueOf(object.getString("errorReason")));
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            confirmed = true;
            return Optional.of(ResultType.JSON_ERROR);
        }

        return Optional.empty();
    }

    public Optional<ResultType> startConfirmation() {
        if(confirmed)  return Optional.empty();

        try {
            URL url = new URL(StringUtil.formatUrl(startUrl, uuid.toString(),
                    player.getAddress().getAddress().getHostAddress()));

            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("User-Agent", "Java/1.8");

            Integer response = connection.getResponseCode();

            if(response == HttpsURLConnection.HTTP_OK) {
                JSONObject object = new JSONObject(JsonReader
                        .readAll(new BufferedReader(new InputStreamReader(connection.getInputStream()))));

                if (!object.getBoolean("error")) {
                    player.spigot().sendMessage(TextComponent
                            .fromLegacyText(Color
                                    .translate("&cYou must confirm your account before you can do anything: &f"
                                            + StringUtil.formatUrl("https://funkemunky.cc/captcha?id=%s",
                                            object.getString("id")))));
                } else return Optional.of(ResultType.valueOf(object.getString("errorReason")));
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            confirmed = true;
            return Optional.of(ResultType.JSON_ERROR);
        }
        return Optional.empty();
    }

    public Optional<ResultType> cancelConfirmation() {
        if(confirmed)  return Optional.empty();

        try {
            URL url = new URL(StringUtil.formatUrl(cancelUrl, id));

            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("User-Agent", "Java/1.8");

            Integer response = connection.getResponseCode();

            if(response == HttpsURLConnection.HTTP_OK) {
                JSONObject object = new JSONObject(JsonReader
                        .readAll(new BufferedReader(new InputStreamReader(connection.getInputStream()))));

                if (!object.getBoolean("error") && object.getBoolean("confirmed")) {
                    player.spigot().sendMessage(TextComponent
                            .fromLegacyText(Color
                                    .translate("&aYour account was confirmed.")));
                    confirmed = true;
                    Antibot.INSTANCE.userHandler.confirm(uuid);
                } else return Optional.of(ResultType.valueOf(object.getString("errorReason")));
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return Optional.of(ResultType.JSON_ERROR);
        }
        return Optional.empty();
    }
}
