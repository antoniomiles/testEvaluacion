package com.pichincha.automationtest.hooks;

import com.pichincha.automationtest.util.AttachScreenshotToScenario;
import com.pichincha.automationtest.util.PropertiesReader;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Scenario;
import io.restassured.specification.FilterableRequestSpecification;
import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.rest.SerenityRest;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
                String addEvidenceOn = readProperties.getProperty("add.evidence.cucumber.on", "manualtest.properties");
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
            Map<String, Object> metadata = new HashMap<>();
            metadata.put("URL", requestSpecification.getURI());
            metadata.put("Request Headers", requestSpecification.getHeaders().toString().split("\n"));
            metadata.put("Request Method", requestSpecification.getMethod());
            metadata.put("Request Body", requestSpecification.getBody());
            metadata.put("Status Code", SerenityRest.lastResponse().statusCode());
            metadata.put("Response Headers", SerenityRest.lastResponse().getHeaders().toString().split("\n"));
            metadata.put("Response Body", SerenityRest.lastResponse().getBody().prettyPrint());
            JSONObject evidenceJSON = new JSONObject(metadata);
            scenario.log(evidenceJSON.toString());
        }
    }
}