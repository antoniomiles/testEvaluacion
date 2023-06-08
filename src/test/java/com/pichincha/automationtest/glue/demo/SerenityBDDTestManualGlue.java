package com.pichincha.automationtest.glue.demo;

import com.pichincha.automationtest.model.ModelScenario;
import com.pichincha.automationtest.util.ManualReadFeature;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SerenityBDDTestManualGlue {

    @Given("que el cliente ingresa a la pagina Mantest")
    public void queElClienteIngresaAlaPagina() {
        ManualReadFeature.validateScenario(ModelScenario.getScenario());
    }

    @When("quien ingresa sus cred para iniciar sesion Mantest")
    public void quienIngresaSusCredencialesParaIniciarSesion() {
        //Metodo vacio por que pertenece a un step manual
    }

    @Then("el ingresa a ver los productos disponibles Mantest")
    public void elIngresaAVerLosProductosDisponibles() {
        //Metodo vacio por que pertenece a un step manual
    }

    @Given("que el cliente ingresa a la pagina de Google")
    public void queElClienteIngresaALaPaginaDeGoogle() {
        ManualReadFeature.validateScenario(ModelScenario.getScenario());
    }

    @When("el ingresa su criterio de busqueda")
    public void elIngresaSuCriterioDeBusqueda() {
        //Metodo vacio por que pertenece a un step manual
    }

    @Then("se deberia mostrar el resultado de la busqueda")
    public void seDeberiaMostrarElResultadoDeLaBusqueda() {
        //Metodo vacio por que pertenece a un step manual
    }
}