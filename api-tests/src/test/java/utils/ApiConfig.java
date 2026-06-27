package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * ApiConfig — loads api-config.properties so no URL is hardcoded in tests.
 */
public class ApiConfig {

    private static final Properties props = new Properties();

    static {
        try (InputStream is = ApiConfig.class
                .getClassLoader()
                .getResourceAsStream("api-config.properties")) {
            if (is != null) {
                props.load(is);
            } else {
                throw new RuntimeException("api-config.properties not found on classpath");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load api-config.properties", e);
        }
    }

    private ApiConfig() { }

    public static String getBaseUrl() {
        return props.getProperty("base.url");
    }

    public static String getApiKey() {
        return props.getProperty("api.key");
    }
}
