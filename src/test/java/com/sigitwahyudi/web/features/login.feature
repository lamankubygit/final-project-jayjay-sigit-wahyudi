@web
Feature: Login ke website Demoblaze

  Background:
    Given user membuka halaman login Demoblaze
    And user login dengan username "standard_user" dan password "secret_sauce"
    And user berhasil masuk dan button menu logout terlihat

  @positive @closeBrowser
  Scenario: Menambahkan produk Apple monitor 24 ke cart
    When user klik menu "Monitors"
    And user pilih produk "Apple monitor 24"
    And user klik tombol Add to cart
    Then muncul pop up notifikasi "Product added."

  @positive @closeBrowser
  Scenario: Melihat produk di cart dan verifikasi harga
    Given produk "Apple monitor 24" sudah ada di cart
    When user membuka menu Cart
    Then produk "Apple monitor 24" tampil di daftar cart
    And harga produk sama dengan harga saat di halaman produk
    When user menghapus produk dari cart
    Then produk tidak tampil lagi di daftar cart
