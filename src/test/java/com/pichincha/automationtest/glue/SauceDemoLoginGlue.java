package com.pichincha.automationtest.glue;

import com.pichincha.automationtest.tasks.IniciaSesion;
import com.pichincha.automationtest.userinterface.SauceDemoLogin;
import com.pichincha.automationtest.userinterface.SauceDemoProducts;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actions.Open;

import java.util.List;
import java.util.Map;

import static net.serenitybdd.screenplay.GivenWhenThen.*;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isPresent;
import static net.serenitybdd.screenplay.questions.WebElementQuestion.the;

public class SauceDemoLoginGlue {

    @Given("que el cliente {actor} ingresa a la pagina SauceDemo")
    public void queElActorIngresaALaPaginaSauceDemo(Actor actor) {
        givenThat(actor).attemptsTo(Open.browserOn().the(SauceDemoLogin.class));
    }
    @When("el ingresa sus credenciales para iniciar sesion")
    public void elIngresaSusCredenciales(DataTable table) {
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);
        for (Map<String, String> columns : rows) {
            when(theActorInTheSpotlight()).wasAbleTo(
                    IniciaSesion.conLasCredenciales(columns.get("user"), columns.get("pass"))
            );
        }
    }
    @Then("el deberia ingresar a ver los productos disponibles")
    public void elDeberiaIngresarAVerLosProductosDisponibles() {
        then(theActorInTheSpotlight()).should(seeThat(the(SauceDemoProducts.PRODUCT_TITLE), isPresent()));
    }
    @Then("se deberia mostrar el siguiente mensaje de error")
    public void seDeberiaMostrarElSiguienteMensajeDeError(String errorMessage) {
        then(theActorInTheSpotlight()).should(seeThat(the(SauceDemoLogin.LOCKED_ERROR.of(errorMessage)), isPresent()));
    }
}