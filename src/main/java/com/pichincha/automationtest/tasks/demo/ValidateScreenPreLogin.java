package com.pichincha.automationtest.tasks.demo;

import com.pichincha.automationtest.ui.demo.ScreenLoginMovil;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isEnabled;

public class ValidateScreenPreLogin implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {

        actor.attemptsTo(
                WaitUntil.the(ScreenLoginMovil.TEXTO_INGRESARCONOTROUSUARIO, isEnabled()).forNoMoreThan(60).seconds()
        );

    }

    public static Performable validate() {
        return instrumented(ValidateScreenPreLogin.class);
    }
}
