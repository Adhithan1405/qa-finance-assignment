package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;

/**
 * DriverManager — singleton-style thread-safe WebDriver provider.
 * Reads browser config from config.properties.
 */
public class DriverManager {

    private static final ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();
    private static Properties config;

    static {
        config = new Properties();
        try (InputStream is = DriverManager.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (is != null) {
                config.load(is);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not load config.properties", e);
        }
    }

    private DriverManager() { }

    public static WebDriver getDriver() {
        if (driverThread.get() == null) {
            initDriver();
        }
        return driverThread.get();
    }

    private static void initDriver() {
        String browser = config.getProperty("browser", "chrome").toLowerCase();
        boolean headless = Boolean.parseBoolean(config.getProperty("headless", "false"));
        int implicitWait = Integer.parseInt(config.getProperty("implicit.wait", "10"));

        if ("chrome".equals(browser)) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            if (headless) {
                options.addArguments("--headless=new");
            }
            options.addArguments("--no-sandbox", "--disable-dev-shm-usage", "--window-size=1920,1080");
            driverThread.set(new ChromeDriver(options));
        } else {
            throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        driverThread.get().manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(implicitWait));
        driverThread.get().manage().window().maximize();
    }

    public static void quitDriver() {
        if (driverThread.get() != null) {
            driverThread.get().quit();
            driverThread.remove();
        }
    }

    public static String getBaseUrl() {
        return config.getProperty("base.url");
    }

    public static int getExplicitWait() {
        return Integer.parseInt(config.getProperty("explicit.wait", "15"));
    }
}
