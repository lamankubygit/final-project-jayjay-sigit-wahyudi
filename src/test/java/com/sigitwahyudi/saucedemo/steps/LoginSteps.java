package com.sigitwahyudi.saucedemo.steps;

import com.sigitwahyudi.saucedemo.pages.LoginPage;
import com.sigitwahyudi.saucedemo.hooks.WebHooks;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import static org.junit.jupiter.api.Assertions.*;

public class LoginSteps {

    private WebDriver driver;
    private LoginPage loginPage;

    @Given("user membuka halaman login SauceDemo")
    public void user_membuka_halaman_login_saucedemo() {
        driver = WebHooks.getDriver();
        driver.get("https://www.saucedemo.com/v1/");
        loginPage = new LoginPage(driver);
    }

    @When("user login dengan username {string} dan password {string}")
    public void user_login_dengan_username_dan_password(String username, String password) {
        loginPage.loginAction(username, password);
    }

    @Then("user berhasil masuk ke halaman inventory")
    public void user_berhasil_masuk_ke_halaman_inventory() {
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("inventory.html"),
                "URL saat ini: " + currentUrl);
    }

    @Then("muncul pesan error {string}")
    public void muncul_pesan_error(String expectedMessage) {
        assertTrue(loginPage.isErrorDisplayed(), "Pesan error tidak muncul di halaman login.");
        String actualMessage = loginPage.getErrorMessage();
        assertEquals(expectedMessage, actualMessage, "Isi pesan error tidak sesuai.");
        assertTrue(loginPage.isLoginButtonDisplayed(), "Tombol login tidak terlihat setelah error muncul.");
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("saucedemo.com"), "User seharusnya masih di halaman login.");
    }
}
