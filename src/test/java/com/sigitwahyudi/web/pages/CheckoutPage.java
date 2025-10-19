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
            // tunggu maksimal 5 detik sampai alert muncul
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            shortWait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            alert.accept();
            System.out.println("✅ Alert diterima: " + alert.getText());
        } catch (TimeoutException | NoAlertPresentException e) {
            System.out.println("⚠️ Tidak ada alert yang muncul (mungkin sudah auto-dismiss atau terlalu cepat)");
        }
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
        return price.replaceAll("[^0-9]", ""); // ambil hanya angka
    }

    public void deleteProductFromCart() {
        wait.until(ExpectedConditions.elementToBeClickable(deleteButton)).click();
        try {
            Thread.sleep(2000); // beri jeda supaya table terupdate
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean isCartEmpty() {
        try {
            driver.findElement(cartItemName);
            return false; // masih ada produk
        } catch (NoSuchElementException e) {
            return true; // cart kosong
        }
    }
}
