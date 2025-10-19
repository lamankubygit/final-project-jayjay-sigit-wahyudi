package com.sigitwahyudi.web.steps;

import com.sigitwahyudi.web.hooks.WebHooks;
import com.sigitwahyudi.web.pages.CheckoutPage;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import java.util.Map;

public class CheckoutSteps {

    private WebDriver driver;
    private CheckoutPage checkoutPage;
    private String selectedProductPrice;

    @When("user klik menu {string}")
    public void user_klik_menu(String menuName) {
        driver = WebHooks.getDriver();
        checkoutPage = new CheckoutPage(driver);
        checkoutPage.clickCategory(menuName);
    }

    @When("user pilih produk {string}")
    public void user_pilih_produk(String productName) {
        checkoutPage.selectProduct(productName);
        selectedProductPrice = checkoutPage.getProductPrice(); // simpan harga dari halaman produk
        System.out.println("Harga produk di halaman produk: " + selectedProductPrice);
    }

    @When("user klik tombol Add to cart")
    public void user_klik_tombol_add_to_cart() {
        checkoutPage.clickAddToCart();
    }

    @Then("muncul pop up notifikasi {string}")
    public void muncul_pop_up_notifikasi(String expectedAlertText) {
        String alertText = checkoutPage.getAlertText();
        Assertions.assertEquals(expectedAlertText, alertText, "Teks alert tidak sesuai!");
        checkoutPage.acceptAlert();
    }

    @Given("produk {string} sudah ada di cart")
    public void produk_sudah_ada_di_cart(String productName) {
        driver = WebHooks.getDriver();
        checkoutPage = new CheckoutPage(driver);
        checkoutPage.ensureProductInCart(productName);
        selectedProductPrice = checkoutPage.getProductPrice();
        System.out.println("Produk sudah ada di cart, harga: " + selectedProductPrice);
    }

    @When("user membuka menu Cart")
    public void user_membuka_menu_cart() {
        checkoutPage.openCart();
    }

    @Then("produk {string} tampil di daftar cart")
    public void produk_tampil_di_daftar_cart(String expectedProductName) {
        String actualProductName = checkoutPage.getProductNameInCart();
        Assertions.assertEquals(expectedProductName, actualProductName, "Produk di cart tidak sesuai!");
    }

    @Then("harga produk sama dengan harga saat di halaman produk")
    public void harga_produk_sama_dengan_harga_saat_di_halaman_produk() {
        String priceInCart = checkoutPage.getProductPriceInCart();
        Assertions.assertEquals(selectedProductPrice, priceInCart, "Harga produk tidak sama!");
    }

    @When("user menghapus produk dari cart")
    public void user_menghapus_produk_dari_cart() {
        checkoutPage.deleteProductFromCart();
    }

    @Then("produk tidak tampil lagi di daftar cart")
    public void produk_tidak_tampil_lagi_di_daftar_cart() {
        Assertions.assertTrue(checkoutPage.isCartEmpty(), "Produk masih ada di cart!");
    }

    // ===== Tambahan untuk End-to-End Checkout =====
    @When("user klik tombol Place Order")
    public void user_klik_tombol_place_order() {
        checkoutPage.clickPlaceOrder();
    }

    @When("user mengisi form pemesanan:")
    public void user_mengisi_form_pemesanan(DataTable dataTable) {
        Map<String, String> data = dataTable.asMap(String.class, String.class);
        checkoutPage.fillOrderForm(
                data.get("name"),
                data.get("country"),
                data.get("city"),
                data.get("card"),
                data.get("month"),
                data.get("year")
        );
    }

    @When("user klik tombol Purchase")
    public void user_klik_tombol_purchase() {
        checkoutPage.clickPurchase();
    }

    @Then("muncul pop up konfirmasi {string}")
    public void muncul_pop_up_konfirmasi(String expectedText) {
        String actualText = checkoutPage.getPurchaseConfirmationText();
        Assertions.assertTrue(actualText.contains(expectedText),
                "Pesan konfirmasi tidak sesuai! Ditemukan: " + actualText);
    }

    @When("user klik tombol OK")
    public void user_klik_tombol_ok() {
        checkoutPage.clickOkAfterPurchase();
    }

    @When("user klik tombol Logout")
    public void user_klik_tombol_logout() {
        checkoutPage.logout();
    }
}
