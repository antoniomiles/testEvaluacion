package com.pichincha.automationtest.ui.demo;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.By;

@DefaultUrl("page:webdriver.base.url.oracle.forgotusername")
public class PageOracle extends PageObject {

    public static final Target INPUT_EMAIL = Target.the("'Input Email'").located(By.id("email::content"));
    public static final Target BUTTON_CONTINUE= Target.the("'Button Continuar'").located(By.id("forgotusername:dc6:f1"));
    public static final Target INPUT_USERNAME= Target.the("'Input Nombre de Usuario'").located(By.id("sso_username"));

}