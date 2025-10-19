package com.sigitwahyudi.api.steps;

import io.cucumber.java.en.*;
import io.restassured.response.Response;
import com.sigitwahyudi.api.requests.DummyApiRequest;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ApiUsersSteps {
    private Response response;

    // ========================= GET =========================

    @When("sistem mengirim request GET users dengan limit {int}")
    public void getUsers(int limit) {
        response = DummyApiRequest.getUsers(limit);
        response.prettyPrint();
    }

    @When("sistem mengirim request GET user dengan ID {string}")
    public void getUserById(String id) {
        response = DummyApiRequest.getUserById(id);
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
        response = DummyApiRequest.deleteUser(id);
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
        assertThat(response.jsonPath().getString("id"), equalTo(id));
    }

    @Then("response berisi field {string} yang tidak null")
    public void verifyFieldNotNull(String field) {
        assertThat(response.jsonPath().getString(field), notNullValue());
    }

    @Then("response berisi ID yang sama seperti user yang dihapus {string}")
    public void verifyDeletedUserId(String id) {
        assertThat(response.jsonPath().getString("id"), equalTo(id));
    }

    @Then("response body berisi pesan error {string}")
    public void verifyError(String keyword) {
        assertThat(response.jsonPath().getString("error"), containsString(keyword));
    }
}
