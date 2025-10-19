Automation Testing Framework – Web dan API

Project ini merupakan tugas untuk membuat framework automation testing berbasis Web dan API menggunakan Java, Gradle, Selenium WebDriver, Cucumber (BDD), RestAssured, dan JUnit 5.  
Website yang diuji adalah SauceDemo, dan API yang diuji menggunakan DummyAPI.io.

Tujuan Pengerjaan

Web Automation
1. Menerapkan konsep BDD menggunakan Cucumber.
2. Melakukan otomatisasi pengujian login pada website SauceDemo.
3. Menggunakan Page Object Model (POM) agar struktur kode lebih teratur.
4. Menjalankan automation test pada beberapa browser (Chrome, Firefox).
5. Menghasilkan laporan hasil test otomatis dalam format HTML.

API Automation
1. Menguji endpoint publik DummyAPI.io menggunakan RestAssured.
2. Menulis skenario pengujian dalam format Gherkin.
3. Memvalidasi status code, struktur, dan isi response API.
4. Menghasilkan laporan hasil test otomatis dalam format HTML.
5. Dapat dijalankan bersamaan dengan pengujian web melalui GitHub Actions.

Menjalankan Test

Web Automation
./gradlew testChrome     # Default
./gradlew testFirefox
./gradlew testWeb -Dheadless=true

API Automation
./gradlew testApi

Laporan Hasil Test
build/reports/cucumber/


