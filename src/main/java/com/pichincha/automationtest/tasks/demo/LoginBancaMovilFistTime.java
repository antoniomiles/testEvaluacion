package com.pichincha.automationtest.tasks.demo;

import com.pichincha.automationtest.util.appium.ManageAppiumDrivers;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static com.pichincha.automationtest.ui.demo.ScreenLoginMovil.*;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isEnabled;

public class LoginBancaMovilFistTime implements Task {

    private final String user;
    private final String password;

    public LoginBancaMovilFistTime(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public static Performable inBancaMovil(String user, String password) {
        return instrumented(LoginBancaMovilFistTime.class, user, password);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(BOTON_YASOYCLIENTE, isEnabled()).forNoMoreThan(40).seconds(),
                Click.on(BOTON_YASOYCLIENTE),
                WaitUntil.the(CAMPO_USUARIO, isEnabled()).forNoMoreThan(5).seconds(),
                Enter.theValue(user).into(CAMPO_USUARIO),
                Enter.theValue(password).into(CAMPO_CONTRASENA)
        );
        if (ManageAppiumDrivers.PLATFORM_NAME.equals("iOS")) {
            ManageAppiumDrivers.iosDriverHideKeyboard();
        }
        actor.attemptsTo(
                Click.on(ACEPTO_CONDICIONES),
                Click.on(BOTON_INGRESARBM)
        );
    }
}
