package com.pichincha.automationtest.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.waits.WaitUntil;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

import static com.pichincha.automationtest.ui.LoginMovil.*;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isEnabled;

public class LoginBancaMovil implements Task {

    private static final EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();
    String platformName = variables.getProperty("appium.platformName");
    private String user;
    private String contrasena;

    public LoginBancaMovil(String user, String contrasena) {
        this.user = user;
        this.contrasena = contrasena;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        if (platformName.equals("iOS")) {
            actor.attemptsTo(
                    WaitUntil.the(BOTON_YASOYCLIENTE, isEnabled()).forNoMoreThan(40).seconds(),
                    Click.on(BOTON_YASOYCLIENTE),
                    WaitUntil.the(CAMPO_USUARIO, isEnabled()).forNoMoreThan(5).seconds(),
                    Enter.theValue(user).into(CAMPO_USUARIO),
                    Enter.theValue(contrasena).into(CAMPO_CONTRASENA),
                    Click.on(ACEPTO_CONDICIONES),
                    Click.on(BOTON_INGRESARBM));
        } else {
            actor.attemptsTo(
                    WaitUntil.the(BOTON_YASOYCLIENTE_ANDROID, isEnabled()).forNoMoreThan(40).seconds(),
                    Click.on(BOTON_YASOYCLIENTE_ANDROID),
                    WaitUntil.the(CAMPO_USUARIO_ANDROID, isEnabled()).forNoMoreThan(5).seconds(),
                    Enter.theValue(user).into(CAMPO_USUARIO_ANDROID),
                    Enter.theValue(contrasena).into(CAMPO_CONTRASENA_ANDROID),
                    Click.on(ACEPTO_CONDICIONES_ANDROID),
                    Click.on(BOTON_INGRESARBM_ANDROID));
        }

    }

    public static Performable enBancaMovil(String user, String contrasena) {
        return instrumented(LoginBancaMovil.class, user, contrasena);
    }
}
