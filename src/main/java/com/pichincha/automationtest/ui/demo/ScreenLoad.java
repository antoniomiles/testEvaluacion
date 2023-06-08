package com.pichincha.automationtest.ui.demo;

import io.appium.java_client.AppiumBy;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class ScreenLoad {


    private ScreenLoad() {
    }

    //Pantalla de carga 1: Logo Pichincha con el texto espera un momento
    public static final Target TEXTO_PANTALLA_DE_CARGA = Target.the("Mensaje Espera un momento")
            .locatedForAndroid(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.TextView")).locatedForIOS(By.xpath("//XCUIElementTypeStaticText[@name=\"Espera un momento\"]"));
}
