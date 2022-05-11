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
    static PropertiesReader readProperties= new PropertiesReader();
    @Given("que el cliente ingresa a la pagina Mantest")
    public void que_el_cliente_ingresa_a_la_pagina() throws IOException, InvalidFormatException {
        List<String> scenarios = ManualReadFeature.readManualFeaturePassedOrdFailed(new File(System.getProperty("user.dir") + readProperties.getPropiedad("path.manual.feature")));
        ManualReadFeature.validatePassedOrdFailed(scenarios,0);
    }
    @When("quien ingresa sus cred para iniciar sesion Mantest")
    public void quien_ingresa_sus_credenciales_para_iniciar_sesion() {

    }
    @Then("el ingresa a ver los productos disponibles Mantest")
    public void el_ingresa_a_ver_los_productos_disponibles() {

    }

    @Given("que el cliente ingresa a la pagina SauceDemo Mantest")
    public void que_el_cliente_ingresa_a_la_pagina_sauce_demo() throws IOException {
        List<String> scenarios = ManualReadFeature.readManualFeaturePassedOrdFailed(new File(System.getProperty("user.dir") + readProperties.getPropiedad("path.manual.feature")));
        ManualReadFeature.validatePassedOrdFailed(scenarios,1);
    }
    @When("el ingresa sus cred para iniciar sesion Mantest")
    public void el_ingresa_sus_cred_para_iniciar_sesion() {

    }
    @Then("se deberia mostrar el mensaje de error Mantest")
    public void se_deberia_mostrar_el_mensaje_de_error() {

    }

    @Given("que el cliente ingresa a la pagina SauceDemo2 Mantest")
    public void que_el_cliente_ingresa_a_la_pagina_sauce_demo2() throws IOException {
        List<String> scenarios = ManualReadFeature.readManualFeaturePassedOrdFailed(new File(System.getProperty("user.dir") + readProperties.getPropiedad("path.manual.feature")));
        ManualReadFeature.validatePassedOrdFailed(scenarios,2);
    }
    @When("el ingresa sus cred para iniciar sesion2 Mantest")
    public void el_ingresa_sus_cred_para_iniciar_sesion2() {

    }
    @Then("se deberia mostrar el mensaje de error2 Mantest")
    public void se_deberia_mostrar_el_mensaje_de_error2() {

    }

}