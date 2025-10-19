package com.sigitwahyudi.api.steps;

import io.cucumber.java.en.*;
import io.restassured.response.Response;
import com.sigitwahyudi.api.requests.DummyApiRequest;
import java.util.Map;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ApiUsersSteps {
    private Response response;
    private static String selectedUserId; // simpan ID user dari GET list

    // ========================= GET =========================

    @When("sistem mengirim request GET users dengan limit {int}")
    public void getUsers(int limit) {
        response = DummyApiRequest.getUsers(limit);
        response.prettyPrint();

        List<String> ids = response.jsonPath().getList("data.id");
        if (ids != null && !ids.isEmpty()) {
            selectedUserId = ids.get(0); // ambil user pertama
            System.out.println("✅ ID user tersimpan untuk skenario berikut: " + selectedUserId);
        }
    }

    @When("sistem mengirim request GET user dengan ID {string}")
    public void getUserById(String id) {
        String targetId = id.equalsIgnoreCase("fromList") ? selectedUserId : id;
        response = DummyApiRequest.getUserById(targetId);
        System.out.println("🔍 Request detail user ID: " + targetId);
        response.prettyPrint();
    }

    // ========================= POST =========================

    @When("sistem mengirim request POST untuk membuat user baru dengan data:")
    public void createUser(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> data = dataTable.asMap(String.class, String.class);
        String body = String.format("""
            {
              "firstName": "%s",
              "lastName": "%s",
              "email": "%s"
            }
            """, data.get("firstName"), data.get("lastName"), data.get("email"));

        response = DummyApiRequest.createUser(body);
        response.prettyPrint();
    }

    // ========================= DELETE =========================

    @When("sistem mengirim request DELETE user dengan ID {string}")
    public void deleteUser(String id) {
        String targetId = id.equalsIgnoreCase("fromList") ? selectedUserId : id;
        response = DummyApiRequest.deleteUser(targetId);
        System.out.println("🗑️ Menghapus user ID: " + targetId);
        response.prettyPrint();
    }

    // ========================= ASSERTIONS =========================

    @Then("status code yang diterima adalah {int}")
    public void verifyStatus(int status) {
        assertThat(response.statusCode(), equalTo(status));
    }

    @Then("status code yang diterima adalah {int} atau {int}")
    public void verifyStatusEither(int code1, int code2) {
        assertThat(response.statusCode(), anyOf(equalTo(code1), equalTo(code2)));
    }

    @Then("jumlah data user pada response adalah {int}")
    public void verifyUserCount(int count) {
        assertThat(response.jsonPath().getList("data"), hasSize(count));
    }

    @Then("setiap data memiliki ID yang valid")
    public void verifyIds() {
        assertThat(response.jsonPath().getString("data[0].id"), notNullValue());
    }

    @Then("response berisi ID yang sama {string}")
    public void verifyIdMatch(String id) {
        String expectedId = id.equalsIgnoreCase("fromList") ? selectedUserId : id;
        assertThat(response.jsonPath().getString("id"), equalTo(expectedId));
    }

    @Then("response berisi ID yang sama seperti user yang dihapus {string}")
    public void verifyDeletedUserId(String id) {
        String expectedId = id.equalsIgnoreCase("fromList") ? selectedUserId : id;
        assertThat(response.jsonPath().getString("id"), equalTo(expectedId));
    }

    @Then("response body berisi pesan error {string}")
    public void verifyError(String keyword) {
        assertThat(response.jsonPath().getString("error"), containsString(keyword));
    }
}
