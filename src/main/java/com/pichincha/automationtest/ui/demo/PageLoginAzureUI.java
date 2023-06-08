package com.pichincha.automationtest.ui.demo;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.DefaultUrl;

@DefaultUrl("page:webdriver.base.url.azure")
public class PageLoginAzureUI extends PageObject {
    public static final Target TEXTFIELD_MAIL = Target.the("'Campo Mail'").locatedBy("//input[@type='email']");
    public static final Target TEXTFIELD_CONTRASENIA = Target.the("'Campo Contrasena'").locatedBy("//input[@name='passwd' and  @type='password']");
    public static final Target BUTTON_SIGUIENTE = Target.the("'Boton Siguiente'").locatedBy("//input[@type='submit']");
    public static final Target BUTTON_INICIAR_SESION = Target.the("'Boton Iniciar Sesion'").locatedBy("//input[@type='submit']");

}
