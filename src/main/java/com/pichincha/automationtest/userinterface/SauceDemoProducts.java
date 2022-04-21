package com.pichincha.automationtest.userinterface;

import net.serenitybdd.screenplay.targets.Target;

public class SauceDemoProducts {

    public static final Target PRODUCT_TITLE = Target.the("Titulo de lista de Productos")
            .locatedBy("//span[contains(text(), 'Products')]");
}
