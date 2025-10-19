@web
Feature: Login ke website SauceDemo

  @positive
  Scenario: Login sukses dengan kredensial valid
    Given user membuka halaman login SauceDemo
    When user login dengan username "standard_user" dan password "secret_sauce"
    Then user berhasil masuk ke halaman inventory

  @negative
  Scenario: Login gagal dengan kredensial salah
    Given user membuka halaman login SauceDemo
    When user login dengan username "locked_out_user" dan password "wrong_password"
    Then muncul pesan error "Epic sadface: Username and password do not match any user in this service"

  @boundary
  Scenario: Login gagal karena username kosong
    Given user membuka halaman login SauceDemo
    When user login dengan username "" dan password "secret_sauce"
    Then muncul pesan error "Epic sadface: Username is required"
