package com.pichincha.automationtest.util;

import io.cucumber.java.Scenario;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actors.OnStage;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AttachScreenshotToScenario {
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    public void addScreenshot( Scenario scenario){
        scenario.attach(((TakesScreenshot) BrowseTheWeb.as(OnStage.theActorInTheSpotlight()).getDriver()).getScreenshotAs(OutputType.BYTES),
                "image/png", //"text/plain"
                String.format("%s %s.jpg", scenario.getName(), DATE_FORMAT.format(new Date()))
        );
    }
}
