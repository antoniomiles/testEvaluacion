package com.pichincha.automationtest.glue.database;

import com.mongodb.client.MongoCollection;
import com.pichincha.automationtest.abilities.NOSQLBaseInteraction;
import com.pichincha.automationtest.model.Catalogo;
import com.pichincha.automationtest.util.ConfigurationParamUtils;
import com.pichincha.automationtest.util.dbconection.MongoUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;

import java.util.Map;

import static org.junit.Assert.assertNotNull;

public class MongoInteractionsGlue {

    private MongoUtils mongoUtils;
    private MongoCollection commandResult;

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
