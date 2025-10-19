package com.sigitwahyudi.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
    private WebDriver driver;

    //======================================== Locator Object ========================================//
    private By usernameField = By.id("user-name");
    private By passwordField = By.id("password");
    private By loginButton = By.id("login-button");
    private By errorMessage = By.xpath("//h3[@data-test='error']");

    //======================================== Constructor ========================================//
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    //======================================== Actions ========================================//
    public void enterUsername(String username) {
        WebElement element = driver.findElement(usernameField);
        element.clear();
        element.sendKeys(username);
    }

    public void enterPassword(String password) {
        WebElement element = driver.findElement(passwordField);
        element.clear();
        element.sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    public void loginAction(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    public boolean isErrorDisplayed() {
        return driver.findElements(errorMessage).size() > 0;
    }

    public String getErrorMessage() {
        if (isErrorDisplayed()) {
            return driver.findElement(errorMessage).getText();
        } else {
            return null;
        }
    }

    public boolean isLoginButtonDisplayed() {
        try {
            WebElement button = driver.findElement(loginButton);
            return button.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
