package com.sigitwahyudi.web.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;

    //======================================== Locator Object ========================================//
    private By menuLoginButton = By.id("login2");
    private By usernameField = By.id("loginusername");
    private By passwordField = By.id("loginpassword");
    private By loginButton = By.xpath("//button[text()='Log in']");
    private By logoutMenu = By.id("logout2");
    private By welcomeText = By.id("nameofuser"); // teks: "Welcome testuser"

    //======================================== Constructor ========================================//
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    //======================================== Actions ========================================//
    public void openHomePage() {
        driver.get("https://www.demoblaze.com/index.html");
    }

    public void clickMenuLogin() {
        driver.findElement(menuLoginButton).click();
    }

    public void enterUsername(String username) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(this.usernameField));
        usernameInput.clear();
        usernameInput.sendKeys(username);
    }

    public void enterPassword(String password) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(this.passwordField));
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    public void clickLogin() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(this.loginButton));
        loginBtn.click();
    }

    public void loginAction(String username, String password) {
        clickMenuLogin();
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    public boolean isLogoutVisible() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement logout = wait.until(ExpectedConditions.visibilityOfElementLocated(logoutMenu));
        String text = logout.getText();
        return text.equalsIgnoreCase("Log out");
    }

    public String getWelcomeText() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            WebElement welcome = wait.until(ExpectedConditions.visibilityOfElementLocated(welcomeText));
            return welcome.getText(); // contoh: "Welcome testuser"
        } catch (TimeoutException e) {
            return "";
        }
    }
}
