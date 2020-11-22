package dev.brighten.bot.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class StringUtil {

    public static String formatUrl(String url, Object... objects) {
        String[] formattedObjects = Arrays.stream(objects).map(obj -> {
            try {
                return URLEncoder.encode(String.valueOf(obj),
                        "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return "oopsie";
            }
        }).toArray(String[]::new);

        return String.format(url, formattedObjects);
    }
}
