package com.sigitwahyudi.web.steps;

import com.sigitwahyudi.web.pages.LoginPage;
import com.sigitwahyudi.web.hooks.WebHooks;
import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;

public class LoginSteps {

    private WebDriver driver;
    private LoginPage loginPage;

    @Given("user membuka halaman login Demoblaze")
    public void user_membuka_halaman_login_demoblaze() {
        driver = WebHooks.getDriver();
        driver.get("https://www.demoblaze.com/index.html");
        loginPage = new LoginPage(driver);
    }

    @When("user login dengan username {string} dan password {string}")
    public void user_login_dengan_username_dan_password(String username, String password) {
        loginPage.loginAction(username, password);
    }

    @Then("user berhasil masuk dan button menu logout terlihat")
    public void user_berhasil_masuk_dan_menu_logout_terlihat() {
        Assertions.assertTrue(loginPage.isLogoutVisible(), "Menu Logout tidak terlihat setelah login!");
    }

    @Then("teks {string} tampil di halaman utama")
    public void teks_tampil_di_halaman_utama(String expectedText) {
        String actualText = loginPage.getWelcomeText();
        Assertions.assertEquals(expectedText, actualText, "Teks welcome tidak sesuai!");
    }
}
