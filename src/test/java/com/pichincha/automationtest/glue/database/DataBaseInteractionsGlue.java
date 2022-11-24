package com.pichincha.automationtest.glue.database;

import com.pichincha.automationtest.abilities.DataBaseInteraction;
import com.pichincha.automationtest.util.ConfigurationParamUtils;
import com.pichincha.automationtest.util.dbconection.DataBaseUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.ensure.Ensure;


import java.util.Map;

public class DataBaseInteractionsGlue {
    private DataBaseUtils dataBaseUtils;
    private Map<String, Object> querySelectResult;
    private Integer queryUpdateResult;

    @Given("I am connected to the database")
    public void i_am_connected_to_the_database() {
        Map<String, Object> configMap = ConfigurationParamUtils.loadEnviromentalValues();
        Actor victor = Actor.named("victor");
        victor.can(DataBaseInteraction.using(configMap));
        dataBaseUtils = DataBaseInteraction.as(victor).getDBUtils();
    }

    @When("I execute the following query {string}")
    public void i_execute_the_following_query(String query) {
        querySelectResult = dataBaseUtils.readRow(query);

    }
    @Then("I expect the result value should be {string}")
    public void i_expect_the_result_value_should_be(String string) {
        Ensure.that((String) querySelectResult.get("nombre")).equals(string);
    }

    @When("I execute the following modifying query {string}")
    public void i_execute_the_following_insert_query(String string) {
        queryUpdateResult = dataBaseUtils.update(string);
    }
    @Then("I expect the result value should be {int}")
    public void i_expect_the_result_value_should_be(Integer result) {
        Ensure.that(queryUpdateResult).equals(result);
    }
}
