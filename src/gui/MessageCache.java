package gui;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

public class MessageCache {
    public static final Map<String, MessageFormat> messageCache = new HashMap<>();

    public static String getFormatted(ResourceBundle rb, String key, Object... args) {
        String pattern = rb.getString(key);
        MessageFormat formatter = messageCache.computeIfAbsent(pattern, MessageFormat::new);

        return formatter.format(args);
    }
}
