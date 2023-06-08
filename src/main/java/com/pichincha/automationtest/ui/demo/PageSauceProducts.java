package com.pichincha.automationtest.ui.demo;

import net.serenitybdd.screenplay.targets.Target;

public class PageSauceProducts {

    private PageSauceProducts() {
    }

    public static final Target PRODUCT_TITLE = Target.the("'Titulo de lista de Productos'").locatedBy("//span[contains(text(), 'Products')]");
}
