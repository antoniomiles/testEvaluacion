package com.pichincha.automationtest.glue.demo;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.interactions.Post;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static net.serenitybdd.screenplay.GivenWhenThen.*;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.Matchers.*;

public class SerenityBDDApisGlue {

    public static final String CONTENT_TYPE = "Content-Type";
    public static final String APPLICATION_JSON = "application/json";
    private Response response;

    @Given("que {actor} se conecta a la API {string}")
    public void queActorSeConectaALaApi(Actor actor, String urlBase) {
        givenThat(actor).whoCan(
                CallAnApi.at(urlBase)
        );
    }

    @When("inicia sesion en el endpoint {string} con las credenciales")
    public void iniciaSesionEnElEndpointConLasCredenciales(String endPoint, DataTable table) {
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);
        String user = rows.get(0).get("user");
        String pass = rows.get(0).get("pass");
        String bodyEntry = "{\"username\": \"" + user + "\","
                + " \"password\": \"" + pass + "\"}";

        when(theActorInTheSpotlight()).attemptsTo(
                Post.to(endPoint).with(requestSpecification -> requestSpecification
                        .header(CONTENT_TYPE, APPLICATION_JSON)
                        .body(bodyEntry))
        );
    }

    @Then("visualiza el codigo de autenticacion y el status code {int}")
    public void visualizaElCodigoDeAutenticacionYElStatusCode(int statusCode) {
        then(theActorInTheSpotlight()).should(
                seeThatResponse("Response con statusCode " + statusCode, responseSpecification -> responseSpecification.statusCode(statusCode)),
                seeThatResponse("Tienen Auth_token en el Body ", responseSpecification -> responseSpecification.body(containsString("Auth_token:")))
        );
    }

    @Then("visualiza el statusCode {int} y el mensaje de error {string}")
    public void visualizaElStatusCodeYElMensajeDeError(int statusCode, String messageError) {
        then(theActorInTheSpotlight()).should(
                seeThatResponse("Response con statusCode " + statusCode, responseSpecification -> responseSpecification.statusCode(statusCode)),
                seeThatResponse("Mensaje de error es " + messageError, responseSpecification -> responseSpecification.body("errorMessage", equalTo(messageError)))
        );
    }

    @Given("que creo al nuevo usuario {string} con el trabajo {string} en el endPoint {string}")
    public void queCreoAlNuevoUsuarioConElTrabajo(String userName, String job, String endPoint) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Creando al nuevo usuario \"{0}\" con el trabajo \"{1}\"", new Object[]{userName, job});

        String bodyEntry = "{\n" +
                "    \"name\": \"" + userName + "\",\n" +
                "    \"job\": \"" + job + "\"\n" +
                "}";

        response = SerenityRest.
                given()
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .body(bodyEntry)
                .when().log().all() //retorna log total en consola de la peticion
                .post(endPoint);

        response.then().statusCode(201);
        response.prettyPeek(); //metodo que imprime la respuesta de manera ordenada
    }


    @When("actualizo el trabajo a {string}, en el endPoint {string}")
    public void actualizoElTrabajoEnElEndPoint(String newJob, String endPoint) {
        String userId = JsonPath.from(response.asString()).get("id");
        String userCreated = JsonPath.from(response.asString()).get("name");
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Id del nuevo usuario creado: {0}", userId);

        String bodyRequest = "{\n" +
                "    \"name\": \"" + userCreated + "\",\n" +
                "    \"job\": \"" + newJob + "\"\n" +
                "}";

        response = SerenityRest.given()
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .body(bodyRequest)
                .pathParam("userId", userId)
                .when().log().all()
                .put(endPoint);
    }

    @Then("visualizo el statusCode {int}")
    public void thenVisualizoElStatusCode(int statusCode) {
        response.then().statusCode(statusCode);
        response.then().body("updatedAt", notNullValue());
        response.prettyPeek();
    }
}