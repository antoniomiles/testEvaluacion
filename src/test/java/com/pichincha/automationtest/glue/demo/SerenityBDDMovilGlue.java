package com.pichincha.automationtest.glue.demo;

import com.pichincha.automationtest.tasks.demo.ApproveMobile;
import com.pichincha.automationtest.tasks.demo.LoginBancaMovilFistTime;
import com.pichincha.automationtest.tasks.demo.ShortEnrollment;
import com.pichincha.automationtest.tasks.demo.ValidateScreenPreLogin;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;

import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class SerenityBDDMovilGlue {

    @Before
    public void prepareStage() {
        OnStage.setTheStage(new OnlineCast());
    }

    @Given("que el usuario {word} esta en la aplicación y es cliente")
    public void actorinapp(String actor) {
        theActorCalled(actor).attemptsTo(
                ApproveMobile.permit()
        );
    }

    @When("el usuario completa el enrollamiento ingresando usuario {string}, contraseña {string} y pin {string}")
    public void queElUsuarioIngreseUsuarioYContrasena(String usuario, String contrasena, String pin) {
        theActorInTheSpotlight().attemptsTo(
                LoginBancaMovilFistTime.inBancaMovil(usuario, contrasena),
                ShortEnrollment.completeEnrollment(pin)
        );
    }

    @Then("se muestra la pantalla de login")
    public void envioDeCodigoActivacion() {
        theActorInTheSpotlight().attemptsTo(
                ValidateScreenPreLogin.validate()
        );
    }
}