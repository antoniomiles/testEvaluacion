package com.pichincha.automationtest.glue;

import com.pichincha.automationtest.util.ManualReadFeature;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.IOException;

public class BusquedaGoogleManualGlue {

    @Given("que el cliente ingresa a la pagina de Google")
    public void queElClienteIngresaALaPaginaDeGoogle() throws IOException {
        ManualReadFeature.validatePassedOrdFailed("BusquedaGoogleManual.feature", 1);
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