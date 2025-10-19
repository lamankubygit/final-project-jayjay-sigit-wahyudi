package com.sigitwahyudi.web.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class WebHooks {

    // Gunakan ThreadLocal supaya aman jika nanti test dijalankan paralel
    private static final ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();

    @Before("@web")
    public void setUp(Scenario scenario) {
        String browser = System.getProperty("browser", "chrome").toLowerCase();
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));

        System.out.println("\n=== Menjalankan Scenario Web: " + scenario.getName() + " ===");
        System.out.println("Browser: " + browser + " | Headless: " + headless);

        WebDriver driver;

        switch (browser) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;

            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();

                // 🔹 Konfigurasi anti-popup & kestabilan browser
                options.addArguments("--disable-save-password-bubble");
                options.addArguments("--disable-infobars");
                options.addArguments("--disable-notifications");
                options.addArguments("--disable-popup-blocking");
                options.addArguments("--disable-extensions");
                options.addArguments("--disable-default-apps");
                options.addArguments("--disable-translate");
                options.addArguments("--no-default-browser-check");
                options.addArguments("--incognito");

                if (headless) {
                    options.addArguments("--headless=new");
                    options.addArguments("--no-sandbox");
                    options.addArguments("--disable-dev-shm-usage");
                }

                driver = new ChromeDriver(options);
                break;
        }

        driver.manage().window().maximize();
        driverThread.set(driver);
    }

    @After("@closeBrowser")
    public void tearDown(Scenario scenario) {
        System.out.println("=== Menyelesaikan Scenario Web: " + scenario.getName() + " ===");
        WebDriver driver = driverThread.get();

        if (driver != null) {
            driver.quit();
            driverThread.remove(); // pastikan tidak ada memory leak
        }
    }

    public static WebDriver getDriver() {
        return driverThread.get();
    }
}
