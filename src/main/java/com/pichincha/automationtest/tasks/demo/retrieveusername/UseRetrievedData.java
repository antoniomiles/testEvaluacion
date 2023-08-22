package com.pichincha.automationtest.tasks.demo.retrieveusername;

import com.pichincha.automationtest.ui.demo.PageOracle;
import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.waits.WaitUntil;
import net.thucydides.core.annotations.Step;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

@Slf4j
public class UseRetrievedData implements Task {

    private final String user;

    public UseRetrievedData(String user) {
        this.user = user;
    }

    public static UseRetrievedData inLoginOracle(String user) {
        return Tasks.instrumented(UseRetrievedData.class, user);
    }

    @Step("{0} ingresa el usuario recuperado")
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Open.url("https://login.oracle.com"),
                WaitUntil.the(PageOracle.INPUT_USERNAME, isVisible()),
                Enter.keyValues(user).into(PageOracle.INPUT_USERNAME)
        );
    }
}