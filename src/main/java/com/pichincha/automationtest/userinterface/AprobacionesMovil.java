package com.pichincha.automationtest.userinterface;

import io.appium.java_client.AppiumBy;
import net.serenitybdd.screenplay.targets.Target;

public class AprobacionesMovil {


    private AprobacionesMovil() {
    }

    //botones permitir iniciales IOS
    public static final Target PERMITIR_UBICACION = Target.the("Permitir usar ubicación").locatedBy("//XCUIElementTypeButton[@name='Permitir al usar la app' or @name='Allow While Using App']");
    public static final Target NOTIFICACIONES = Target.the("Permitir notificaciones").locatedBy("//XCUIElementTypeButton[@name='Permitir' or @name='Allow']");
    public static final Target RASTREO = Target.the("Permitir rastreo").locatedBy("//XCUIElementTypeButton[@name='Permitir' or @name='Allow Tracking' or @name='Allow']");



    //botones para dar permisos ANDROID
    //Para la localización de elementos se puede usar la libreria de Appium
    public static final Target PERMITIR_UBICACION_ANDROID = Target.the("Permitir usar ubicación").located(AppiumBy.id("com.android.packageinstaller:id/permission_allow_button"));




}
