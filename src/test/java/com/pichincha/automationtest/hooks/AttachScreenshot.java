package com.pichincha.automationtest.hooks;

import com.pichincha.automationtest.util.AttachScreenshotToScenario;

import java.util.logging.Level;
import java.util.logging.Logger;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Scenario;

public class AttachScreenshot extends AttachScreenshotToScenario {
    Logger logger = Logger.getLogger(this.getClass().getName());

    @After
    @AfterStep("not @manual and not @Database")
    public void attachScreenshotJsonReportForScenario(Scenario scenario) {
        boolean isManualScenario = false;
        if (scenario.isFailed()) {
            try {
                String[] tagsScenario = scenario.getSourceTagNames().toArray(new String[0]);
                for (String lineTag : tagsScenario) {
                    if (lineTag.trim().equalsIgnoreCase("@manual")) {
                        isManualScenario = true;
                        break;
                    }
                }
                if (isManualScenario) {
                    addScreenshotManualTest(scenario);
                } else {
                    addScreenshot(scenario);
                }
            } catch (Exception e) {
                logger.log(Level.WARNING, "ERROR: al adjuntar imagen al reporte JSON generado por cucumber:", e);

            }
        }
    }
}