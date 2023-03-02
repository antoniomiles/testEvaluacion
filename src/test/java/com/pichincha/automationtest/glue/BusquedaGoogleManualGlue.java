package com.pichincha.automationtest.glue;

import com.pichincha.automationtest.model.ModelScenario;
import com.pichincha.automationtest.util.ManualReadFeature;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class BusquedaGoogleManualGlue {

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