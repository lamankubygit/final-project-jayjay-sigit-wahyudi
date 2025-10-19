package com.sigitwahyudi.web.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CheckoutPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // =================== Locators =================== //
    private String productLinkXPath = "//a[contains(text(),'%s')]";
    private By addToCartButton = By.xpath("//a[@class='btn btn-success btn-lg']");
    private By priceOnProductPage = By.cssSelector(".price-container");
    private By cartMenu = By.id("cartur");
    private By cartItemName = By.xpath("//tbody/tr/td[2]");
    private By cartItemPrice = By.xpath("//tbody/tr/td[3]");
    private By deleteButton = By.xpath("//a[text()='Delete']");

    // checkout flow
    private By placeOrderButton = By.xpath("//button[text()='Place Order']");
    private By nameField = By.id("name");
    private By countryField = By.id("country");
    private By cityField = By.id("city");
    private By cardField = By.id("card");
    private By monthField = By.id("month");
    private By yearField = By.id("year");
    private By purchaseButton = By.xpath("//button[text()='Purchase']");
    private By confirmationModal = By.xpath("//div[@class='sweet-alert  showSweetAlert visible']");
    private By okButton = By.xpath("//button[text()='OK']");
    private By logoutButton = By.id("logout2");

    // =================== Constructor =================== //
    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // =================== Actions =================== //
    public void clickCategory(String categoryName) {
        By categoryLocator = By.xpath("//a[@class='list-group-item' and text()='" + categoryName + "']");
        wait.until(ExpectedConditions.elementToBeClickable(categoryLocator)).click();
    }

    public void selectProduct(String productName) {
        By productLocator = By.xpath(String.format(productLinkXPath, productName));
        wait.until(ExpectedConditions.elementToBeClickable(productLocator)).click();
    }

    public void clickAddToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton)).click();
    }

    public String getAlertText() {
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        return alert.getText();
    }

    public void acceptAlert() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            shortWait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            alert.accept();
        } catch (TimeoutException | NoAlertPresentException ignored) {}
    }

    public String getProductPrice() {
        String text = wait.until(ExpectedConditions.visibilityOfElementLocated(priceOnProductPage)).getText();
        return text.replaceAll("[^0-9]", ""); // ambil hanya angka
    }

    public void ensureProductInCart(String productName) {
        driver.get("https://www.demoblaze.com/index.html");
        clickCategory("Monitors");
        selectProduct(productName);
        clickAddToCart();
        acceptAlert();
    }

    public void openCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartMenu)).click();
    }

    public String getProductNameInCart() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartItemName));
        return driver.findElement(cartItemName).getText();
    }

    public String getProductPriceInCart() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartItemPrice));
        String price = driver.findElement(cartItemPrice).getText();
        return price.replaceAll("[^0-9]", "");
    }

    public void deleteProductFromCart() {
        wait.until(ExpectedConditions.elementToBeClickable(deleteButton)).click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean isCartEmpty() {
        try {
            driver.findElement(cartItemName);
            return false;
        } catch (NoSuchElementException e) {
            return true;
        }
    }

    // =================== Tambahan untuk End-to-End Checkout =================== //
    public void clickPlaceOrder() {
        wait.until(ExpectedConditions.elementToBeClickable(placeOrderButton)).click();
    }

    public void fillOrderForm(String name, String country, String city, String card, String month, String year) {
        // Pastikan popup sudah tampil penuh
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameField));

        // Scroll ke popup agar field terlihat
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(nameField));

        // Fokus ke elemen dulu
        WebElement nameInput = wait.until(ExpectedConditions.elementToBeClickable(nameField));
        nameInput.click();
        nameInput.clear();
        nameInput.sendKeys(name);

        // Isi field lainnya
        driver.findElement(countryField).sendKeys(country);
        driver.findElement(cityField).sendKeys(city);
        driver.findElement(cardField).sendKeys(card);
        driver.findElement(monthField).sendKeys(month);
        driver.findElement(yearField).sendKeys(year);
    }


    public void clickPurchase() {
        wait.until(ExpectedConditions.elementToBeClickable(purchaseButton)).click();
    }

    public String getPurchaseConfirmationText() {
        WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(confirmationModal));
        return modal.getText();
    }

    public void clickOkAfterPurchase() {
        wait.until(ExpectedConditions.elementToBeClickable(okButton)).click();
    }

    public void logout() {
        wait.until(ExpectedConditions.elementToBeClickable(logoutButton)).click();
    }
}
