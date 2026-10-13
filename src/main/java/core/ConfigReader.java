package core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Reads configuration values with the following priority (highest first):
 * 1. JVM system property   -> mvn test -Dweb.headless=false
 * 2. Environment variable  -> WEB_HEADLESS=false (dots become underscores, upper case)
 * 3. config.properties     -> default values committed to the repo
 *
 * Secrets (tokens, passwords for real systems) must NEVER be committed:
 * pass them through (1) or (2), e.g. GitHub Actions secrets.
 */
public final class ConfigReader {

    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input != null) {
                PROPERTIES.load(input);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load config.properties", e);
        }
    }

    private ConfigReader() {
    }

    public static String get(String key) {
        String value = System.getProperty(key);
        if (value == null) {
            value = System.getenv(key.replace('.', '_').toUpperCase());
        }
        if (value == null) {
            value = PROPERTIES.getProperty(key);
        }
        if (value == null) {
            throw new IllegalArgumentException("Missing configuration key: " + key);
        }
        return value.trim();
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }
}
