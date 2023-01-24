package com.pichincha.automationtest.hooks;

import com.pichincha.automationtest.exceptions.ExcRuntime;
import com.pichincha.automationtest.util.ManualReadFeature;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Scenario;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;

@Slf4j
public class ValidateManualScenario {

    @AfterStep("@manual")
    public void validateScenario(Scenario scenario) {
        try {
            File featureFile = new File(scenario.getUri());
            int lineScenario = scenario.getLine();
            log.info("Validando scenario de la linea " + lineScenario + " en el feature " + featureFile.getName());
            ManualReadFeature.validatePassedOrdFailed(featureFile, lineScenario);
        } catch (IOException e) {
            throw new ExcRuntime("Error al validar resultado de scenario Manual - " + e.getMessage(), e);
        }
    }

}