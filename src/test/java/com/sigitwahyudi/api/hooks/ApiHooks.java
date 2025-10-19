package com.sigitwahyudi.api.hooks;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class ApiHooks {
    @Before
    public void beforeScenario(Scenario scenario) {
        System.out.println("Menjalankan Scenario API: " + scenario.getName());
    }
}
