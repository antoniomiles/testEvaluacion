package com.pichincha.automationtest.ui.demo;

import io.appium.java_client.AppiumBy;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class ScreensCreateOrChangePIN {

    private ScreensCreateOrChangePIN() {
    }

    //Pantalla Crear Pin
    public static final Target CAJA_DE_TEXTO_INGRESAPIN = Target.the("Campo ingresa código Pin").locatedForAndroid(By.xpath("//android.view.ViewGroup[@resource-id='container_txtPin']/android.widget.EditText")).locatedForIOS(AppiumBy.accessibilityId("container_txtPin"));
    public static final Target CAJA_DE_TEXTO_REPITETUPIN = Target.the("Campo repite tu código Pin").locatedForAndroid(By.xpath("//android.view.ViewGroup[@resource-id='container_txtRepeatPin']/android.widget.EditText")).locatedForIOS(AppiumBy.accessibilityId("container_txtRepeatPin"));
    public static final Target BOTON_CREARAHORA = Target.the("Boton crear ahora").locatedForAndroid(By.xpath("//android.widget.Button[1]")).locatedForIOS(AppiumBy.accessibilityId("btnCreatePin"));
}
