package com.pichincha.automationtest.glue.demo;
import com.pichincha.automationtest.ui.demo.PageHipotecas;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.targets.Target;

import static net.serenitybdd.screenplay.GivenWhenThen.givenThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class SerenityBDDHipotecas {
    @Given("{actor} navega por la calculadora")
    public void juanNavegaPorLaCalculadora(Actor actor) {
        givenThat(actor).attemptsTo(
                Open.browserOn().the(PageHipotecas.class)
        );
    }

    @When("selecciona el producto de vivienda de interés público y social")
    public void seleccionaElProductoDeViviendaDeInterésPúblicoYSocial() {
        Target radioButton = PageHipotecas.RADIO_BUTTON_SELECT;
        theActorInTheSpotlight().attemptsTo(
                Click.on(radioButton)
        );
    }

    @And("proporciona los datos para el cálculo {string} {string} y {string}")
    public void proporcionaLosDatosParaElCálculoY(String costo, String monto, String plazo) {
        theActorInTheSpotlight().attemptsTo(
                Enter.theValue(costo).into(PageHipotecas.AMOUNT_INPUT),
                Enter.theValue(monto).into(PageHipotecas.AMOUNTTWO_INPUT),
                Enter.theValue(plazo).into(PageHipotecas.TERM_INPUT)
        );
    }
    @And("selecciona el sistema de amortización deseas aplicar al crédito")
    public void seleccionaElSistemaDeAmortizaciónDeseasAplicarAlCrédito() {
        Target radioButton = PageHipotecas.RADIO_BUTTON_SELECT_AMORT;
        theActorInTheSpotlight().attemptsTo(
                Click.on(radioButton)
        );
    }
    @And("da clic en el botón calcular")
    public void daClicEnElBotónCalcular() {
        theActorInTheSpotlight().attemptsTo(
                Click.on(PageHipotecas.CALCULATE_BUTTON)
        );
    }

    @Then("visualizará los resultados del cálculo")
    public void visualizaráLosResultadosDelCálculo() {
    }


}