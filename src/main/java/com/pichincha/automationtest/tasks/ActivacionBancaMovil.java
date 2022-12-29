package com.pichincha.automationtest.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

import static com.pichincha.automationtest.userinterface.CodigoActivacionMovil.*;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isEnabled;

public class ActivacionBancaMovil implements Task {

    private static final EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();
    String platformName = variables.getProperty("appium.platformName");


    @Override
    public <T extends Actor> void performAs(T actor) {
        if (platformName.equals("iOS")) {
            actor.attemptsTo(
                    WaitUntil.the(BOTON_ENVIAR_CODIGO_IOS, isEnabled()).forNoMoreThan(30).seconds(),
                    Click.on(BOTON_ENVIAR_CODIGO_IOS));

        } else {
            actor.attemptsTo(
                    WaitUntil.the(BOTON_ENVIAR_CODIGO_ANDROID, isEnabled()).forNoMoreThan(30).seconds(),
                    Click.on(BOTON_ENVIAR_CODIGO_ANDROID));

        }

    }

    public static Performable conenviodecodigo() {
        return instrumented(ActivacionBancaMovil.class);
    }
}
