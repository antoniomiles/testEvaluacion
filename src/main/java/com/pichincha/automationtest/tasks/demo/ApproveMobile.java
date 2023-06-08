package com.pichincha.automationtest.tasks.demo;

import com.pichincha.automationtest.util.appium.ManageAppiumDrivers;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static com.pichincha.automationtest.ui.demo.ScreenApproveMovil.*;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isEnabled;

public class ApproveMobile implements Task {


    public static Performable permit() {
        return instrumented(ApproveMobile.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {

        if (ManageAppiumDrivers.PLATFORM_NAME.equals(ManageAppiumDrivers.IOS)) {
            actor.attemptsTo(
                    WaitUntil.the(PERMITIR_UBICACION_IOS15_16, isEnabled()).forNoMoreThan(60).seconds(),
                    Click.on(PERMITIR_UBICACION_IOS15_16),
                    WaitUntil.the(PERMITIR_NOTIFICACIONES_IOS15_16, isEnabled()).forNoMoreThan(60).seconds(),
                    Click.on(PERMITIR_NOTIFICACIONES_IOS15_16),
                    WaitUntil.the(PERMITIR_RASTREO_IOS15_16, isEnabled()).forNoMoreThan(60).seconds(),
                    Click.on(PERMITIR_RASTREO_IOS15_16)
            );

        } else {
            actor.attemptsTo(
                    WaitUntil.the(PERMITIR_UBICACION_ANDROID_12, isEnabled()).forNoMoreThan(60).seconds(),
                    Click.on(PERMITIR_UBICACION_ANDROID_12)
            );
        }

    }
}
