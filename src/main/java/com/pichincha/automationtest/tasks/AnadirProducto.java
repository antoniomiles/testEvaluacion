package com.pichincha.automationtest.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.pichincha.automationtest.ui.CabeceraMenu.MENU_CART;
import static com.pichincha.automationtest.ui.CarritoCompra.ELEMENT_CART;
import static com.pichincha.automationtest.ui.PaginaProductos.BUTTON_ADD_TO_CART;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class AnadirProducto  implements Task {

    private String descripcion;
    public AnadirProducto (String descripcion){
        this.descripcion = descripcion;
    }

    public static AnadirProducto alCarrito(String descripcion) {
        return instrumented(AnadirProducto.class, descripcion);
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
                Ensure.that(ELEMENT_CART.of(descripcion)).isDisplayed()
        );
    }
    public void acceptProduct (Actor actor) {
                WebDriverWait wait = new WebDriverWait(BrowseTheWeb.as(actor).getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.alertIsPresent());
        BrowseTheWeb.as(actor).getDriver().switchTo().alert().accept();
    }
}