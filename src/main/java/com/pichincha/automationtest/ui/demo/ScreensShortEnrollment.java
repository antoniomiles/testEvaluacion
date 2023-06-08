package com.pichincha.automationtest.ui.demo;

import io.appium.java_client.AppiumBy;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class ScreensShortEnrollment {

    private ScreensShortEnrollment() {
    }
    //Pantalla de confirmación de cambio de dispositivo
    public static final Target BOTON_CONTINUAR_REGISTRO = Target.the("Boton continuar registro con otor celular").locatedForAndroid(By.xpath("//android.widget.Button[1]")).locatedForIOS(AppiumBy.id("Continuar registro en este teléfono"));

    //Pantalla de confirmación
    public static final Target BOTON_INGRESAR_A_LA_BANCA_MOVIL = Target.the("Boton ingresar a la banca movil").locatedForAndroid(By.xpath("//android.widget.TextView[@text = 'Ingresar a la Banca móvil']")).locatedForIOS(By.xpath("//XCUIElementTypeButton[1]"));


}
