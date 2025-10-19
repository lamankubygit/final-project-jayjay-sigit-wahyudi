package com.sigitwahyudi.saucedemo.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class WebHooks {

    private static WebDriver driver;

    @Before("@web")
    public void setUp(Scenario scenario) {
        String browser = System.getProperty("browser", "chrome").toLowerCase();
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
        System.out.println("=== Menjalankan Scenario Web: " + scenario.getName() + " ===");
        System.out.println("Browser: " + browser + " | Headless: " + headless);

        switch (browser) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;

            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                if (headless) {
                    options.addArguments("--headless=new");
                    options.addArguments("--no-sandbox");
                    options.addArguments("--disable-dev-shm-usage");
                }
                driver = new ChromeDriver(options);
                break;
        }

        try {
            driver.manage().window().maximize();
        } catch (Exception ignored) {
        }
    }

    @After("@web")
    public void tearDown(Scenario scenario) {
        System.out.println("=== Menyelesaikan Scenario Web: " + scenario.getName() + " ===");
        if (driver != null) {
            driver.quit();
        }
    }

    public static WebDriver getDriver() {
        return driver;
    }
}
