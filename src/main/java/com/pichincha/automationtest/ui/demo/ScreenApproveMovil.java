package com.pichincha.automationtest.ui.demo;

import io.appium.java_client.AppiumBy;
import net.serenitybdd.screenplay.targets.Target;

public class ScreenApproveMovil {

    private ScreenApproveMovil() {
    }

    //IOS 15 y 16
    public static final Target PERMITIR_NOTIFICACIONES_IOS15_16 = Target.the("Permitir notificaciones").locatedBy("//XCUIElementTypeButton[@name='Permitir' or @name='Allow']");
    public static final Target PERMITIR_UBICACION_IOS15_16 = Target.the("IOS Permitir usar ubicación").locatedBy("//XCUIElementTypeButton[@name='Permitir al usar la app' or @name='Allow While Using App']");
    public static final Target PERMITIR_RASTREO_IOS15_16 = Target.the("Permitir rastreo").locatedBy("//XCUIElementTypeButton[@name='Permitir' or @name='Allow Tracking' or @name='Allow']");



    //ANDROID: botones para dar permisos
    //Para la localización de elementos se puede usar la libreria de Appium haciendo uso del comando AppiumBy
    public static final Target PERMITIR_UBICACION_ANDROID_12 = Target.the("Android 12: Permitir usar ubicación").located(AppiumBy.id("com.android.permissioncontroller:id/permission_allow_foreground_only_button"));



}
