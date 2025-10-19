@web
Feature: Login ke website Demoblaze

  Background:
    Given user membuka halaman login Demoblaze
    And user login dengan username "standard_user" dan password "secret_sauce"
    And user berhasil masuk dan button menu logout terlihat

  @positive @closeBrowser
  Scenario: Login berhasil menampilkan teks Welcome
    Then teks "Welcome standard_user" tampil di halaman utama

  @positive @closeBrowser
  Scenario: Melihat produk di cart dan verifikasi harga
    Given produk "Apple monitor 24" sudah ada di cart
    When user membuka menu Cart
    Then produk "Apple monitor 24" tampil di daftar cart
    And harga produk sama dengan harga saat di halaman produk
    When user menghapus produk dari cart
    Then produk tidak tampil lagi di daftar cart

  @positive @closeBrowser
  Scenario: Menambahkan produk Apple monitor 24 ke cart
    When user klik menu "Monitors"
    And user pilih produk "Apple monitor 24"
    And user klik tombol Add to cart
    Then muncul pop up notifikasi "Product added."

  @positive @closeBrowser
  Scenario: End to end checkout produk Apple monitor 24
    When user klik menu "Monitors"
    And user pilih produk "Apple monitor 24"
    And user klik tombol Add to cart
    Then muncul pop up notifikasi "Product added."
    When user membuka menu Cart
    And user klik tombol Place Order
    And user mengisi form pemesanan:
      | name      | testuser          |
      | country   | Indonesia         |
      | city      | Bandung           |
      | card      | 1234567890123456  |
      | month     | December          |
      | year      | 2025              |
    And user klik tombol Purchase
    Then muncul pop up konfirmasi "Thank you for your purchase!"
    When user klik tombol OK
    And user klik tombol Logout
