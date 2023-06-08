package com.pichincha.automationtest.glue.demo;

import com.mongodb.client.MongoCollection;
import com.pichincha.automationtest.abilities.DataBaseInteraction;
import com.pichincha.automationtest.abilities.NOSQLBaseInteraction;
import com.pichincha.automationtest.model.demo.Catalogo;
import com.pichincha.automationtest.util.ConfigurationParamUtils;
import com.pichincha.automationtest.util.dbconection.DataBaseUtils;
import com.pichincha.automationtest.util.dbconection.MongoUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.ensure.Ensure;

import java.util.Map;

import static org.junit.Assert.assertNotNull;

public class SerenityBDDDataBaseGlue {
    private DataBaseUtils dataBaseUtils;
    private Map<String, Object> querySelectResult;
    private Integer queryUpdateResult;

    private MongoUtils mongoUtils;
    private MongoCollection commandResult;


    @Given("I am connected to the {string} database")
    public void iAmConnectedToTheDatabase(String dbType) {
        Map<String, Object> configMap = ConfigurationParamUtils.loadEnviromentalValues(dbType);
        Actor victor = Actor.named("victor");
        victor.can(DataBaseInteraction.using(configMap));
        dataBaseUtils = DataBaseInteraction.as(victor).getDBUtils();
    }

    @When("I execute the following query {string}")
    public void iExecuteTheFollowingQuery(String query) {
        querySelectResult = dataBaseUtils.readRow(query);

    }

    @Then("I expect the result value should be {string}")
    public void iExpectTheResultValueShouldBe(String string) {
        Ensure.that((String) querySelectResult.get("nombre")).equals(string);
    }

    @When("I execute the following modifying query {string}")
    public void iExecuteTheFollowingInsertQuery(String string) {
        queryUpdateResult = dataBaseUtils.update(string);
    }

    @Then("I expect the result value should be {int}")
    public void iExpectTheResultValueShouldBe(Integer result) {
        Ensure.that(queryUpdateResult).equals(result);
    }


    @Given("I am connected to the {string} nosql database")
    public void iAmConnectedToTheNosqlDatabase(String dbType) {
        Map<String, Object> configMap = ConfigurationParamUtils.loadEnviromentalValues(dbType);
        this.mongoUtils = new MongoUtils(configMap);
        Actor victor = Actor.named("victor");
        victor.can(NOSQLBaseInteraction.using(configMap));
        mongoUtils = NOSQLBaseInteraction.as(victor).getMongoUtils();
    }

    @When("I fetch a collection called {string} from the server")
    public void iFetchACollectionCalledFromTheServer(String collectionName) {
        commandResult = mongoUtils.getCollection(collectionName, Catalogo.class);
    }

    @Then("I expect the command's result should not be null")
    public void iExpectTheCommandSResultShouldNotBeNull() {
        assertNotNull(commandResult.getNamespace());
    }
}
