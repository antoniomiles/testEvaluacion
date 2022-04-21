package com.pichincha.automationtest.userinterface;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.targets.Target;

public class PaginaProductos extends PageObject {

    public static final Target BUTTON_ADD_TO_CART = Target.the("botón para añadir al carrito el producto")
            .locatedBy("//a[@class='btn btn-success btn-lg']");
}