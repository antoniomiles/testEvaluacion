package com.pichincha.automationtest.runners.parallel;

import com.pichincha.automationtest.util.AfterSuite;
import com.pichincha.automationtest.util.BeforeSuite;
import com.pichincha.automationtest.util.ControlParallel;
import com.pichincha.automationtest.util.CustomCucumberWithSerenityRunner;
import io.cucumber.junit.CucumberOptions;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.concurrent.Callable;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

@Slf4j
@RunWith(CustomCucumberWithSerenityRunner.class)
@CucumberOptions(
        features = "src/test/resources/features/",
        glue = {"com.pichincha.automationtest.hooks", "com.pichincha.automationtest.glue"},
        plugin = "json:build/cucumberreportstest/cucumberParallel2.json",
        tags = "@R2 and not @karate and not @ManualTest and not @Mobiletest"
)

public class WebRunnerB {
    private WebRunnerB() {
    }

    private static final String RUNNER = "Runner2";

    @BeforeSuite
    public static void init() throws IOException {
        await().atMost(30, SECONDS).until(featuresOverwritten());
        ControlParallel.setOrRemoveExecution("add");
        log.info("Inicia " + RUNNER);
    }

    @AfterSuite
    public static void after() throws IOException {
        ControlParallel.setOrRemoveExecution("delete");
        log.info("Termina " + RUNNER);
    }

    private static Callable<Boolean> featuresOverwritten() {
        return () -> ControlParallel.validateExecution(RUNNER);
    }

}
