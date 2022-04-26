package com.pichincha.automationtest.hooks;

import com.pichincha.automationtest.util.AttachScreenshotToScenario;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Scenario;

public class AttachScreenshot extends AttachScreenshotToScenario {

    @AfterStep
    public void attachScreenshotJsonReport(Scenario scenario) {
        try {
            addScreenshot(scenario);
        } catch (Exception e) {
            System.out.println("ERROR: al adjuntar imagen al reporte JSON generado por cucumber: "+ e);
        }
    }
}