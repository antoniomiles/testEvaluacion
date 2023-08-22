package com.pichincha.automationtest.tasks.demo.retrieveusername;

import com.pichincha.automationtest.actions.BPRememberDate;
import com.pichincha.automationtest.ui.demo.PageOracle;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.waits.WaitUntil;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.Keys;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class ProccessRetrieveUserName implements Task {

    private final String email;

    public ProccessRetrieveUserName(String email) {
        this.email = email;
    }

    public static ProccessRetrieveUserName whitMail(String email) {
        return Tasks.instrumented(ProccessRetrieveUserName.class, email);
    }

    @Step("{0} solicita recordar su usuario")
    @Override
    public <T extends Actor> void performAs(T actor) {

        actor.attemptsTo(
                WaitUntil.the(PageOracle.INPUT_EMAIL, isVisible()),
                Enter.keyValues(email.trim().toLowerCase()).into(PageOracle.INPUT_EMAIL).thenHit(Keys.ENTER),
                BPRememberDate.current(),
                WaitUntil.the(PageOracle.BUTTON_CONTINUE, isVisible())
        );
    }
}