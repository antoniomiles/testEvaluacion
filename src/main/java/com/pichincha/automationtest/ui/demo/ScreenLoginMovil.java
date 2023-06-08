package com.pichincha.automationtest.ui.demo;

import io.appium.java_client.AppiumBy;
import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.screenplay.targets.Target;

public class ScreenLoginMovil {

    private ScreenLoginMovil() {
    }

    //ANDROID
    public static final Target BOTON_YASOYCLIENTE = Target.the("'Botón Ya soy cliente'").locatedForAndroid(By.xpath("//android.widget.Button[1]")).locatedForIOS(AppiumBy.accessibilityId("btnAlreadyClient"));
    public static final Target CAMPO_USUARIO = Target.the("'Campo Usuario'").locatedForAndroid(By.xpath("//android.widget.EditText[@text = 'Usuario']")).locatedForIOS(AppiumBy.id("txtUsername"));
    public static final Target CAMPO_CONTRASENA = Target.the("'Campo Contraseña'").locatedForAndroid(By.xpath("//android.widget.EditText[@text = 'Contraseña']")).locatedForIOS(AppiumBy.id("txtPassword"));
    public static final Target ACEPTO_CONDICIONES = Target.the("'Aceptar términos y condiciones'").locatedForAndroid(By.xpath("//android.widget.CheckBox[1]")).locatedForIOS(AppiumBy.id("checkboxTermsAndConditions"));
    public static final Target BOTON_INGRESARBM = Target.the("'Bóton Ingresar'").locatedForAndroid(By.xpath("//android.widget.TextView[@text = 'Ingresar a la Banca móvil']")).locatedForIOS(AppiumBy.accessibilityId("btnAccept"));

    public static final Target TEXTO_INGRESARCONOTROUSUARIO = Target.the("Botón Ingresar con otro usuario").locatedForAndroid(By.xpath("//android.widget.TextView[@text = '¿Ingresar con otro usuario?']")).locatedForIOS(By.xpath("//XCUIElementTypeLink[@name=\"¿Ingresar con otro usuario?\"]"));

}
