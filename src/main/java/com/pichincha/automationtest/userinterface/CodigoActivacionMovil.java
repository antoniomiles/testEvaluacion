package com.pichincha.automationtest.userinterface;

import net.serenitybdd.screenplay.targets.Target;

public class CodigoActivacionMovil {

    private CodigoActivacionMovil() {
    }

    //ios
    public static final Target BOTON_ENVIAR_CODIGO_IOS = Target.the("Botón enviar código").locatedBy("//XCUIElementTypeButt[@name='EmailBtn']");
    //android

    public static final Target BOTON_ENVIAR_CODIGO_ANDROID = Target.the("Botón enviar código").locatedBy("//android.widget.TextView[@text = 'Enviar código']");



}
