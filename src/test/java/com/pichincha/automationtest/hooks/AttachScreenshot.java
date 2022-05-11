package com.pichincha.automationtest.hooks;

import com.pichincha.automationtest.util.AttachScreenshotToScenario;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Scenario;

public class AttachScreenshot extends AttachScreenshotToScenario {

    @After @AfterStep("not @manual")
    public void attachScreenshotJsonReportForScenario(Scenario scenario) {
        boolean isManualScenario=false;
        if(scenario.isFailed()){
            try {
                String[] tagsScenario = scenario.getSourceTagNames().toArray(new String[0]);
                for (String lineTag : tagsScenario) {
                    if (lineTag.trim().equalsIgnoreCase("@manual")) {
                        isManualScenario = true;
                        break;
                    }
                }
                if(isManualScenario){
                    addScreenshotManualTest(scenario);
                }else {
                    addScreenshot(scenario);
                }
            } catch (Exception e) {
                System.out.println("ERROR: al adjuntar imagen al reporte JSON generado por cucumber: "+ e);
            }
        }
    }
}