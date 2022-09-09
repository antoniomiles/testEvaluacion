package com.pichincha.automationtest.glue;

import com.pichincha.automationtest.tasks.AnadirProducto;
import com.pichincha.automationtest.tasks.BuscarProducto;
import com.pichincha.automationtest.tasks.RegistrarCliente;
import com.pichincha.automationtest.userinterface.PaginaPrincipal;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actions.Open;

import static com.pichincha.automationtest.userinterface.CarritoCompra.SUCCESSFULL_PURCHASE;
import static net.serenitybdd.screenplay.GivenWhenThen.*;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isPresent;
import static net.serenitybdd.screenplay.questions.WebElementQuestion.the;

public class SimulacionCompraTelefonoStepdefinitions {

    @Given("que el {actor} ingresa a la pagina de demoblaze para la compra de telefonos selecciona el {string}")
    public void quiereComprarProducto(Actor actor,String descripcion) {
    givenThat(actor).attemptsTo(Open.browserOn().the(PaginaPrincipal.class));
    andThat(actor).wasAbleTo(
            BuscarProducto.conDescripcion(descripcion),
            AnadirProducto.alCarrito(descripcion));
   }
    @When("el decide hacer la compra ingresa sus datos personales {string}, {string}, {string}, {string}, {string} y {string}")
    public void ySeIdentificaConLosDatosDeCompraY(String nombre, String pais, String ciudad, String numeroDeTarjeta, String mesVencimiento, String anioVencimiento) {
        when(theActorInTheSpotlight()).wasAbleTo(
                RegistrarCliente.conInformacionCompra(nombre, pais,ciudad, numeroDeTarjeta, mesVencimiento,  anioVencimiento));
    }
    @Then("el realiza la compra del producto exitosamente")
    public void completaLaCompraExitosamenteDelProducto() {
        then(theActorInTheSpotlight()).should(seeThat(the(SUCCESSFULL_PURCHASE), isPresent()));
    }
}