package com.pichincha.automationtest.runners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features/",
        glue = {"com.pichincha.automationtest.hooks","com.pichincha.automationtest.glue",},
        plugin = "json:target/cucumber-report/SimulacionCompraTelefono.json",
        tags = "@SimulacionCompraTelefonoTng",
        snippets = CucumberOptions.SnippetType.CAMELCASE
)
public class SimulacionCompraTelefonoCSVRunnerTest {

    
}