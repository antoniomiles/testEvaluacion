package com.pichincha.automationtest.tasks.demo;

import com.pichincha.automationtest.ui.demo.PageLoginAzureUI;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.waits.WaitUntil;
import net.thucydides.core.annotations.Step;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class LoginAzure implements Task {

    public final String mail;
    public final String password;

    public LoginAzure(String mail, String password) {
        this.mail = mail;
        this.password = password;
    }

    public static LoginAzure conCredenciales(String mail, String password) {
        return instrumented(LoginAzure.class, mail, password);
    }

    @Step("{0} hace login en azure")
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Enter.theValue(mail).into(PageLoginAzureUI.TEXTFIELD_MAIL),
                Click.on(PageLoginAzureUI.BUTTON_SIGUIENTE),
                WaitUntil.the(PageLoginAzureUI.TEXTFIELD_CONTRASENIA, isVisible()),
                Enter.theValue(password).into(PageLoginAzureUI.TEXTFIELD_CONTRASENIA),
                Click.on(PageLoginAzureUI.BUTTON_INICIAR_SESION)
        );
    }
}



