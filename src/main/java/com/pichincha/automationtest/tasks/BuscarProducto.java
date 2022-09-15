package com.pichincha.automationtest.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.thucydides.core.annotations.Step;

import static com.pichincha.automationtest.userinterface.PaginaPrincipal.SELECTED_PRODUCT;
import static com.pichincha.automationtest.userinterface.PaginaPrincipal.SELECT_TYPE_PRODUCT;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import java.util.logging.Level;
import java.util.logging.Logger;


public class BuscarProducto implements Task {

    private String descripcion;

    Logger logger = Logger.getLogger(this.getClass().getName());

    public BuscarProducto(String descripcion) {
        this.descripcion = descripcion;
    }

    public static BuscarProducto conDescripcion(String descripcion) {
        return instrumented(BuscarProducto.class, descripcion);
    }
    @Step("{0} busca un producto")
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Task.where("Busca el producto " + descripcion,
                        Click.on(SELECT_TYPE_PRODUCT),
                        Click.on(SELECTED_PRODUCT.of(descripcion)))
        );
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            logger.log(Level.WARNING,"ERROR: ",e);
            Thread.currentThread().interrupt();

        }

    }
}