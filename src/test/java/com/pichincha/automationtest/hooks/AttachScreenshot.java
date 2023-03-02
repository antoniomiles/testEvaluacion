package com.pichincha.automationtest.hooks;

import com.pichincha.automationtest.util.AttachScreenshotToScenario;
import com.pichincha.automationtest.util.PropertiesReader;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Scenario;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AttachScreenshot extends AttachScreenshotToScenario {

    static PropertiesReader readProperties = new PropertiesReader();

    @After("not @Database")
    @AfterStep("not @manual and not @Database")
    public void attachScreenshotJsonReportForScenario(Scenario scenario) {
        boolean isManualScenario = false;

        try {
            String[] tagsScenario = scenario.getSourceTagNames().toArray(new String[0]);
            for (String lineTag : tagsScenario) {
                if (lineTag.trim().equalsIgnoreCase("@manual")) {
                    isManualScenario = true;
                    break;
                }
            }
            if (isManualScenario) {
                String addEvidenceOn = readProperties.getPropiedad("add.evidence.cucumber.on");
                if (addEvidenceOn.trim().equalsIgnoreCase("failed")) {
                    if (scenario.isFailed()) {
                        addScreenshotManualTest(scenario);
                    }
                } else {
                    addScreenshotManualTest(scenario);
                }
            } else {
                if (scenario.isFailed()) {
                    addScreenshot(scenario);
                }
            }
        } catch (Exception e) {
            log.warn("ERROR: al adjuntar imagen/evidencia al reporte JSON generado por cucumber:" + e.getMessage());
        }

    }
}