package com.pichincha.automationtest.userinterface;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.targets.Target;

public class SauceDemoProducts extends PageObject {

    public static final Target PRODUCT_TITLE = Target.the("Titulo de lista de Productos").locatedBy("//span[contains(text(), 'Products')]");
}
