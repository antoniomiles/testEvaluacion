package com.pichincha.automationtest.glue;

import com.pichincha.automationtest.tasks.ActivacionBancaMovil;
import com.pichincha.automationtest.tasks.AprobarpermisosMovil;
import com.pichincha.automationtest.tasks.LoginBancaMovil;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;

import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class BancaMovilStepDefinition {

    @Before
    public void prepareStage() {
        OnStage.setTheStage(new OnlineCast());
    }

    @Given("que el usuario {word} esta en la aplicación y es cliente")
    public void actorinapp(String actor) {
        theActorCalled(actor).attemptsTo(AprobarpermisosMovil.permitir());
    }


    @When("que el usuario ingrese usuario {string} y contraseña {string}")
    public void queElUsuarioIngreseUsuarioYContrasena(String usuario, String contrasena) {
        theActorInTheSpotlight().attemptsTo(LoginBancaMovil.enBancaMovil(usuario, contrasena));
    }



    @Then("envio de codigo activacion")
    public void envioDeCodigoActivacion() {
        theActorInTheSpotlight().attemptsTo(ActivacionBancaMovil.conenviodecodigo());
    }
}