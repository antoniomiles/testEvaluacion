package com.pichincha.automationtest.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

import static com.pichincha.automationtest.userinterface.AprobacionesMovil.*;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isEnabled;

public class AprobarpermisosMovil implements Task {

    private static final EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();
    String platformName = variables.getProperty("appium.platformName");

    @Override
    public <T extends Actor> void performAs(T actor) {

        if (platformName.equals("iOS")) {
            actor.attemptsTo(
            WaitUntil.the(PERMITIR_UBICACION, isEnabled()).forNoMoreThan(60).seconds(),
                    Click.on(PERMITIR_UBICACION),
                    WaitUntil.the(NOTIFICACIONES, isEnabled()).forNoMoreThan(60).seconds(),
                    Click.on(NOTIFICACIONES),
                    WaitUntil.the(RASTREO, isEnabled()).forNoMoreThan(60).seconds(),
                    Click.on(RASTREO)

                    );

        } else {
            actor.attemptsTo(
            WaitUntil.the(PERMITIR_UBICACION_ANDROID, isEnabled()).forNoMoreThan(60).seconds(),
                    Click.on(PERMITIR_UBICACION_ANDROID));
        }

    }

    public static Performable permitir(){
        return instrumented(AprobarpermisosMovil.class);
    }
}
