package com.pichincha.automationtest.runners;

import com.pichincha.automationtest.util.AfterSuite;
import com.pichincha.automationtest.util.BeforeSuite;
import com.pichincha.automationtest.util.CustomCucumberWithSerenityRunner;
import com.pichincha.automationtest.util.FeatureOverwrite;
import io.cucumber.junit.CucumberOptions;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.runner.RunWith;

import java.io.IOException;

@RunWith(CustomCucumberWithSerenityRunner.class)
@CucumberOptions(
        features = "src/test/resources/features/",
        glue = {"com.pichincha.automationtest.hooks","com.pichincha.automationtest.glue",},
        plugin = "json:build/cucumber-reports/SimulacionCompraTelefono.json",
        tags = "@SimulacionCompraTelefonoTng",
        snippets = CucumberOptions.SnippetType.CAMELCASE
)
public class SimulacionCompraTelefonoCSVRunnerTest {

    @BeforeSuite
    public static void init() throws IOException, InvalidFormatException {
        FeatureOverwrite.overwriteFeatureFileAdd(System.getProperty("user.dir") + "/src/test/resources/features/SimulacionCompraTelefono.feature");
    }

    @AfterSuite
    public static void after() throws IOException, InvalidFormatException {
        FeatureOverwrite.overwriteFeatureFileRemove(System.getProperty("user.dir") + "/src/test/resources/features/SimulacionCompraTelefono.feature");
    }
}