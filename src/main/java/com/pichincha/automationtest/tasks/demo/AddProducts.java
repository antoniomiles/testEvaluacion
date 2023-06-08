package com.pichincha.automationtest.tasks.demo;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.pichincha.automationtest.ui.demo.PageCart.ELEMENT_CART;
import static com.pichincha.automationtest.ui.demo.PageHeaderMenu.MENU_CART;
import static com.pichincha.automationtest.ui.demo.PageProducts.BUTTON_ADD_TO_CART;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class AddProducts implements Task {

    private final String description;

    public AddProducts(String description) {
        this.description = description;
    }

    public static AddProducts toCart(String description) {
        return instrumented(AddProducts.class, description);
    }

    @Step("{0} añade el producto al carrito")
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(BUTTON_ADD_TO_CART)
        );
        acceptProduct(actor);
        actor.attemptsTo(
                Click.on(MENU_CART),
                Ensure.that(ELEMENT_CART.of(description)).isDisplayed()
        );
    }

    public void acceptProduct(Actor actor) {
        WebDriverWait wait = new WebDriverWait(BrowseTheWeb.as(actor).getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.alertIsPresent());
        BrowseTheWeb.as(actor).getDriver().switchTo().alert().accept();
    }
}