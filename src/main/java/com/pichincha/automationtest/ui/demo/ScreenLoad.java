package com.pichincha.automationtest.ui.demo;

import io.appium.java_client.AppiumBy;
import net.serenitybdd.screenplay.targets.Target;

public class ScreenLoad {


    private ScreenLoad() {
    }

    //Pantalla de carga 1: Logo Pichincha con el texto espera un momento
    public static final Target TEXTO_PANTALLA_DE_CARGA = Target.the("Mensaje Espera un momento")
            .locatedForAndroid(AppiumBy.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.TextView")).locatedForIOS(AppiumBy.xpath("//XCUIElementTypeStaticText[@name=\"Espera un momento\"]"));
}
