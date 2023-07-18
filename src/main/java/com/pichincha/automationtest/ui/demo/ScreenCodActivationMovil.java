package com.pichincha.automationtest.ui.demo;

import net.serenitybdd.screenplay.targets.Target;

public class ScreenCodActivationMovil {

    private ScreenCodActivationMovil() {
    }

    //ios
    public static final Target BOTON_ENVIAR_CODIGO_IOS = Target.the("'Botón enviar código'").locatedBy("//XCUIElementTypeButt[@name='EmailBtn']");

    //android
    public static final Target BOTON_ENVIAR_CODIGO_ANDROID = Target.the("'Botón enviar código'").locatedBy("//android.widget.TextView[@text = 'Enviar código']");

}
