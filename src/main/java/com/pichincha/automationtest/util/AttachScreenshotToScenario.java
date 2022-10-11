package com.pichincha.automationtest.util;

import io.cucumber.java.Scenario;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actors.OnStage;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AttachScreenshotToScenario {
    static PropertiesReader readProperties = new PropertiesReader();
    private DateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public void addScreenshot(Scenario scenario) {
        scenario.attach(
                ((TakesScreenshot) BrowseTheWeb.as(OnStage.theActorInTheSpotlight()).getDriver())
                        .getScreenshotAs(OutputType.BYTES),
                "image/png", // "text/plain"
                String.format("%s %s.jpg", scenario.getName(), formatDate.format(new Date())));
    }

    public void addScreenshotManualTest(Scenario scenario) throws IOException {
        String[] tagsScenario = scenario.getSourceTagNames().toArray(new String[0]);
        for (String lineTag : tagsScenario) {
            if (lineTag.contains("@manual-test-evidence:")) {
                String[] numberEvidence = lineTag.split(",");
                for (String e : numberEvidence) {
                    String nameEvidence = StringUtils.substringBetween(e, "(", ")");
                    nameEvidence = nameEvidence.substring(6);
                    FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.dir")
                            + readProperties.getPropiedad("report.assets.directory") + nameEvidence);
                    byte[] imageInBytes = IOUtils.toByteArray(fileInputStream);
                    if (nameEvidence.endsWith(".png") || nameEvidence.endsWith(".jpg")
                            || nameEvidence.endsWith(".jpeg")) {
                        scenario.attach(imageInBytes,
                                "image/png", // "text/plain"
                                String.format("%s %s.jpg", scenario.getName(), formatDate.format(new Date())));
                    } else if (nameEvidence.endsWith(".txt")) {
                        scenario.attach(imageInBytes,
                                "text/plain",
                                String.format("%s %s.txt", scenario.getName(), formatDate.format(new Date())));
                    } else {
                        throw new IllegalStateException();
                    }

                }
            }
        }
    }
}
