package com.pichincha.automationtest.ui;

import net.serenitybdd.screenplay.targets.Target;

public class LoginMovil {



    private LoginMovil() {
    }
    //IOS
    public static final Target BOTON_YASOYCLIENTE = Target.the("Botón Ya soy cliente").locatedBy("//XCUIElementTypeButton[@name='Ya soy cliente']");

    public static final Target CAMPO_USUARIO = Target.the("Campo Usuario").locatedBy("//XCUIElementTypeTextField[@name='Ingresa tu usuario']");
    public static final Target CAMPO_CONTRASENA = Target.the("Campo Contraseña").locatedBy("//XCUIElementTypeSecureTextField[@name='Ingresa tu contraseña']");
    public static final Target ACEPTO_CONDICIONES = Target.the("Aceptar términos y condiciones").locatedBy("//XCUIElementTypeOther[@name='Acepto términos y condiciones.']");

    public static final Target BOTON_INGRESARBM = Target.the("Bóton Ingresar").locatedBy("//XCUIElementTypeButton[@name='Ingresar a la Banca móvil']");

    //ANDROID
    public static final Target BOTON_YASOYCLIENTE_ANDROID = Target.the("Botón Ya soy cliente").locatedBy("//android.widget.Button[1]");
    public static final Target CAMPO_USUARIO_ANDROID = Target.the("Campo Usuario").locatedBy("//android.widget.EditText[@text = 'Usuario']");
    public static final Target CAMPO_CONTRASENA_ANDROID = Target.the("Campo Contraseña").locatedBy("//android.widget.EditText[@text = 'Contraseña']");
    public static final Target ACEPTO_CONDICIONES_ANDROID = Target.the("Aceptar términos y condiciones").locatedBy("//android.widget.CheckBox/android.widget.TextView");

    public static final Target BOTON_INGRESARBM_ANDROID = Target.the("Bóton Ingresar").locatedBy("//android.widget.TextView[@text = 'Ingresar a la Banca móvil']");


}
