@api
Feature: Pengujian API DummyAPI.io - Endpoint Users

  @positive
  Scenario: Mendapatkan daftar user (limit 5)
    When sistem mengirim request GET users dengan limit 5
    Then status code yang diterima adalah 200
    And jumlah data user pada response adalah 5
    And setiap data memiliki ID yang valid

  @positive
  Scenario: Mendapatkan detail user berdasarkan ID valid
    When sistem mengirim request GET user dengan ID "60d0fe4f5311236168a109df"
    Then status code yang diterima adalah 200
    And response berisi ID yang sama "60d0fe4f5311236168a109df"

  @negative
  Scenario: Mendapatkan detail user dengan ID tidak valid
    When sistem mengirim request GET user dengan ID "ngasaltest12345678910"
    Then status code yang diterima adalah 400 atau 404
    And response body berisi pesan error "PARAMS_NOT_VALID"

#  @positive
#  Scenario: Menghapus user berdasarkan ID valid
#    When sistem mengirim request DELETE user dengan ID "60d0fe4f5311236168a109f9"
#    Then status code yang diterima adalah 200
#    And response berisi ID yang sama seperti user yang dihapus "60d0fe4f5311236168a109f9"

  @negative
  Scenario: Menghapus user yang sudah tidak ada atau sudah terhapus
    When sistem mengirim request DELETE user dengan ID "60d0fe4f5311236168a109e8"
    Then status code yang diterima adalah 404
    And response body berisi pesan error "RESOURCE_NOT_FOUND"

  @negative
  Scenario: Membuat user baru dengan data tidak lengkap (tanpa email)
    When sistem mengirim request POST untuk membuat user baru dengan data:
      | firstName | Jane |
      | lastName  | Doe  |
      | email     |      |
    Then status code yang diterima adalah 400 atau 422
    And response body berisi pesan error "BODY_NOT_VALID"

  @negative
  Scenario: Menghapus user dengan ID yang tidak valid
    When sistem mengirim request DELETE user dengan ID "invalidUser123"
    Then status code yang diterima adalah 400 atau 404
    And response body berisi pesan error "PARAMS_NOT_VALID"