package com.pichincha.automationtest.glue;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.interactions.Post;
import net.thucydides.core.util.EnvironmentVariables;

import java.util.List;
import java.util.Map;

import static net.serenitybdd.screenplay.GivenWhenThen.then;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.Matchers.*;


public class DemoblazeApiLoginGlue {



    private EnvironmentVariables environmentVariables;

    @Given("que el usuario {actor} ingresa a la Api de Demoblaze e ingrese las credenciales")
    public void queElActorIngresaALaPaginaSauceDemo(Actor actor, DataTable table) {
        String theRestApiBaseUrl;

        theRestApiBaseUrl = environmentVariables.optionalProperty("restapi.baseurl")
                .orElse("https://api.demoblaze.com");

        actor.whoCan(CallAnApi.at(theRestApiBaseUrl));

        List<Map<String, String>> rows = table.asMaps(String.class, String.class);

        for (Map<String, String> columns : rows) {

            actor.attemptsTo(
                    Post.to("/login")
                            .with(request -> request.header("Content-Type", "application/json")
                                    .body("{\n" +
                                            "\"username\": \"" + columns.get("user") + "\",\n" +
                                            " \"password\": \"" + columns.get("pass") + "\"\n" +
                                            "}")
                            )
            );
        }

    }


    @Then("el recibe el codigo de autenticacion")
    public void elRecibeElCodigoDeAutenticacion() {
        then(theActorInTheSpotlight()).should(
                seeThatResponse("Recibe código de Autenticacion",
                        response -> response.statusCode(200)
                                .body(containsString("Auth_token:"))


                )
        );
    }

    @Then("el recibe el codigo error")
    public void elRecibeElCodigodeError(){
        then(theActorInTheSpotlight()).should(
                seeThatResponse("Usuario no encontrado",
                        response -> response.statusCode(200)
                                .body("errorMessage",equalTo("User does not exist."))

                )
        );
    }
}