package com.pichincha.automationtest.hooks;

import com.pichincha.automationtest.util.AttachScreenshotToScenario;
import com.pichincha.automationtest.util.PropertiesReader;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Scenario;
import io.restassured.specification.FilterableRequestSpecification;
import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.rest.SerenityRest;

@Slf4j
public class AttachScreenshot extends AttachScreenshotToScenario {

    static PropertiesReader readProperties = new PropertiesReader();

    @After("not @Database and not @api")
    @AfterStep("not @manual and not @Database and not @api")
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

    @After("@api and @smokeTest and not @karate")
    public void addEvidenceApis(Scenario scenario) {
        if (scenario.isFailed()) {
            FilterableRequestSpecification requestSpecification = (FilterableRequestSpecification) SerenityRest.when();
            String templateJson = """
                    {
                        "URL": "%s",
                        "Request Headers": "%s",
                        "Request Body": "%s",
                        "Status Code": "%s",
                        "Response Headers": "%s",
                        "Response Body": "%s"
                    }""";

            String evidences = String.format(templateJson, requestSpecification.getURI(), requestSpecification.getHeaders().toString(), requestSpecification.getBody(), SerenityRest.lastResponse().statusCode(), SerenityRest.lastResponse().getHeaders(), SerenityRest.lastResponse().getBody().prettyPrint());
            scenario.log(evidences);
        }
    }
}