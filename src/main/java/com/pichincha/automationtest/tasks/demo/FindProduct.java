package com.pichincha.automationtest.tasks.demo;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;
import net.thucydides.core.annotations.Step;

import static com.pichincha.automationtest.ui.demo.PageMain.SELECTED_PRODUCT;
import static com.pichincha.automationtest.ui.demo.PageMain.SELECT_TYPE_PRODUCT;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class FindProduct implements Task {

    private final String description;

    public FindProduct(String description) {
        this.description = description;
    }

    public static FindProduct whitDescription(String description) {
        return instrumented(FindProduct.class, description);
    }

    @Step("{0} busca un producto")
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Task.where("Busca el producto " + description,
                        Click.on(SELECT_TYPE_PRODUCT),
                        WaitUntil.the(SELECTED_PRODUCT.of(description), isVisible()),
                        Click.on(SELECTED_PRODUCT.of(description))
                )
        );
    }
}