package com.pichincha.automationtest.ui.demo;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.DefaultUrl;

@DefaultUrl("page:webdriver.base.url.saucedemo")
public class PageSauceLogin extends PageObject {

    public static final Target USER_NAME_FIELD = Target.the("'Campo del usuario'").locatedBy("#user-name");
    public static final Target PASSWORD_FIELD = Target.the("'Campo de la contraseña'").locatedBy("#password");
    public static final Target LOGIN_BUTTON = Target.the("'Botón para iniciar sesión'").locatedBy("#login-button");
    public static final Target LOCKED_ERROR = Target.the("'Mensaje de error para usuario bloqueado'").locatedBy("//div/h3[@data-test='error']");
}