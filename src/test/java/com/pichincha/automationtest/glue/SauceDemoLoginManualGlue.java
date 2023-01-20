package com.pichincha.automationtest.glue;

import com.pichincha.automationtest.util.ManualReadFeature;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.IOException;

public class SauceDemoLoginManualGlue {

    private static final String SAUCEDEMOLOGINMANUAL_FEATURE = "SauceDemoLoginManual.feature";

    @Given("que el cliente ingresa a la pagina Mantest")
    public void queElClienteIngresaAlaPagina() throws IOException {
        ManualReadFeature.validatePassedOrdFailed(SAUCEDEMOLOGINMANUAL_FEATURE, 1);
    }

    @When("quien ingresa sus cred para iniciar sesion Mantest")
    public void quienIngresaSusCredencialesParaIniciarSesion() {
        //Metodo vacio por que pertenece a un step manual
    }

    @Then("el ingresa a ver los productos disponibles Mantest")
    public void elIngresaAVerLosProductosDisponibles() {
        //Metodo vacio por que pertenece a un step manual
    }

    @Given("que el cliente ingresa a la pagina SauceDemo Mantest")
    public void queElClienteIngresaaAlaPaginaSauceDemo() throws IOException {
        ManualReadFeature.validatePassedOrdFailed(SAUCEDEMOLOGINMANUAL_FEATURE, 2);
    }

    @When("el ingresa sus cred para iniciar sesion Mantest")
    public void elIngresaSusCredParaIniciarSesion() {
        //Metodo vacio por que pertenece a un step manual
    }

    @Then("se deberia mostrar el mensaje de error Mantest")
    public void seDeberiaMostrarElMensajeDeError() {
        //Metodo vacio por que pertenece a un step manual
    }

    @Given("que el cliente ingresa a la pagina SauceDemo2 Mantest")
    public void queElClienteIngresaAlaPaginaSauceDemo2() throws IOException {
        ManualReadFeature.validatePassedOrdFailed(SAUCEDEMOLOGINMANUAL_FEATURE, 3);
    }

    @When("el ingresa sus cred para iniciar sesion2 Mantest")
    public void elIngresaSusCredParaIniciarSesion2() {
        //Metodo vacio por que pertenece a un step manual
    }

    @Then("se deberia mostrar el mensaje de error2 Mantest")
    public void seDeberiaMostrarElMensajeDeError2() {
        //Metodo vacio por que pertenece a un step manual
    }

}