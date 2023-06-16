package com.pichincha.automationtest.glue.demo;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.interactions.Get;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static net.serenitybdd.screenplay.GivenWhenThen.*;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.Matchers.*;
public class PruebaLucioAriasAPI {
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String APPLICATION_JSON = "application/json";
    private Response response;
    @Given("el {actor} ingresa a la pagina {string}")
    public void elClienteIngresaALaPagina(Actor actor, String url) {
        givenThat(actor).whoCan(
                CallAnApi.at(url)
        );
    }
    @When("consulta el endpoint {string}")
    public void consultaElEndpoint(String endPoint) {

        when(theActorInTheSpotlight()).attemptsTo(
                Get.resource(endPoint).with(requestSpecification -> requestSpecification
                        .header(CONTENT_TYPE, APPLICATION_JSON))
        );
    }

    @Then("verifica que exista un producto con rate {double} tenga un id: {int}, categoria {string} y con titulo {string}")
    public void verificaQueExistaUnProductoConRateTengaUnIdCategoriaYConTitulo(Double rate, Integer id, String categoria, String titulo) {
        then(theActorInTheSpotlight()).should(
                seeThatResponse("Response con statusCode 200", responseSpecification -> responseSpecification.statusCode(200))
        );

        Response response = SerenityRest.lastResponse();
        List<Map<String, Object>> productos = response.jsonPath().getList("$");

        System.out.println(productos);
        List<Map<String, Object>> productosConCalificacionValida = productos.stream().filter(product -> (Double)((Map<String, Object>) product.get("rating")).get("rate") == rate).toList();
        System.out.println("LISTAAAAAAA");

        System.out.println(productosConCalificacionValida.toString());
    }

    //Caso 2
    @Then("validar que contenga un total de {int} y exista el producto con id: {int}")
    public void validarQueContengaUnTotalDeTotalProductosYExistaElProductoConId(int totalProductos, int idProducto) {
        then(theActorInTheSpotlight()).should(
                seeThatResponse("Response con statusCode 200", responseSpecification -> responseSpecification.statusCode(200)),
                seeThatResponse("Tenga "+ totalProductos  + " elementos en la respuesta", responseSpecification -> responseSpecification.body(hasSize(totalProductos))),
                seeThatResponse("Exista un elemento con id: "+ idProducto, responseSpecification -> responseSpecification.body(hasItemInArray(hasEntry("id",idProducto))))
        );

    }

    //Caso 3
    @When("consulta el endpoint {string} y limite de {int} elementos")
    public void consultaElEndpointYLimiteDeElementos(String endPoint, Integer limite) {
        when(theActorInTheSpotlight()).attemptsTo(
                Get.resource(endPoint)
                        .with( request -> request.queryParam("limit", limite))
                        .with(requestSpecification -> requestSpecification
                            .header(CONTENT_TYPE, APPLICATION_JSON))
        );
    }
    @Then("validar que solo tenga el limite de {int} elementos solicitados")
    public void validarQueSoloTengaElLimiteDeElementosSolicitados(Integer limite) {
        then(theActorInTheSpotlight()).should(
                seeThatResponse("Response con statusCode 200", responseSpecification -> responseSpecification.statusCode(200)),
                seeThatResponse("Tenga " + limite + " elementos en la respuesta", responseSpecification -> responseSpecification.body(hasSize(limite)))
        );
    }
}
