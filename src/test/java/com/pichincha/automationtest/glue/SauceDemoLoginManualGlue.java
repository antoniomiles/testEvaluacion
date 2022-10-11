package com.pichincha.automationtest.glue;

import com.pichincha.automationtest.util.ManualReadFeature;
import com.pichincha.automationtest.util.PropertiesReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SauceDemoLoginManualGlue {
    static PropertiesReader readProperties = new PropertiesReader();
    private String atributopro = "path.manual.feature";
    private String userDir = "user.dir";

    @Given("que el cliente ingresa a la pagina Mantest")
    public void queElClienteIngresaAlaPagina() throws IOException, InvalidFormatException {
        List<String> scenarios = ManualReadFeature.readManualFeaturePassedOrdFailed(
                new File(System.getProperty(userDir) + readProperties.getPropiedad(atributopro)));
        ManualReadFeature.validatePassedOrdFailed(scenarios, 0);
    }

    @When("quien ingresa sus cred para iniciar sesion Mantest")
    public void quienIngresaSusCredencialesParaIniciarSesion() {
        // Paso para prueba manual que esta vacío
    }

    @Then("el ingresa a ver los productos disponibles Mantest")
    public void elIngresaAVerLosProductosDisponibles() {
        // es un paso para la prueba manual por eso esta vacío
    }

    @Given("que el cliente ingresa a la pagina SauceDemo Mantest")
    public void queElClienteIngresaaAlaPaginaSauceDemo() throws IOException {
        List<String> scenarios = ManualReadFeature.readManualFeaturePassedOrdFailed(
                new File(System.getProperty(userDir) + readProperties.getPropiedad(atributopro)));
        ManualReadFeature.validatePassedOrdFailed(scenarios, 1);
    }

    @When("el ingresa sus cred para iniciar sesion Mantest")
    public void elIngresaSusCredParaIniciarSesion() {
        // es un paso que esta vacío por prueba manual
    }

    @Then("se deberia mostrar el mensaje de error Mantest")
    public void seDeberiaMostrarElMensajeDeError() {
        // Es un paso de prueba manual por lo que se mantiene vacío
    }

    @Given("que el cliente ingresa a la pagina SauceDemo2 Mantest")
    public void queElClienteIngresaAlaPaginaSauceDemo2() throws IOException {
        List<String> scenarios = ManualReadFeature.readManualFeaturePassedOrdFailed(
                new File(System.getProperty(userDir) + readProperties.getPropiedad(atributopro)));
        ManualReadFeature.validatePassedOrdFailed(scenarios, 2);
    }

    @When("el ingresa sus cred para iniciar sesion2 Mantest")
    public void elIngresaSusCredParaIniciarSesion2() {
        // Es un paso de prueba manual poreso esta vacío
    }

    @Then("se deberia mostrar el mensaje de error2 Mantest")
    public void seDeberiaMostrarElMensajeDeError2() {
        // Es un paso de prueba manual
    }

}