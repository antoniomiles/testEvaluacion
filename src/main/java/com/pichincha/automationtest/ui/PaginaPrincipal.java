package com.pichincha.automationtest.ui;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.By;

@DefaultUrl("page:webdriver.base.url.demoblaze")
public class PaginaPrincipal extends PageObject {
    public static final Target SELECT_TYPE_PRODUCT = Target.the("Botón de tipo de producto").located(By.xpath("//a[contains(text(),'Phones')]"));
    public static final Target SELECTED_PRODUCT = Target.the("Link de producto encontrado").locatedBy("//a[contains(text(),'{0}')]");
}