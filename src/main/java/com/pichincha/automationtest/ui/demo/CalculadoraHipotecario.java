package com.pichincha.automationtest.ui.demo;

import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.By;
import net.serenitybdd.core.pages.PageObject;

@DefaultUrl("page:webdriver.base.url.calculadoraHipotecario")
public class CalculadoraHipotecario  extends PageObject{
    public static final Target BUTTON_TIPO_VIVIENDA = Target.the("'Boton Tipo Vivienda Nueva o Usada'").locatedBy("(//div[@class='box-title'][contains(.,'{0}')])[1]");
    public static final Target TEXTFIELD_VALOR_VIVIENDA = Target.the("'Campo de ingreso valor vivienda'").locatedBy("//input[@id='home-price-calculator']");
    public static final Target TEXTFIELD_VALOR_PRESTAMO = Target.the("'Campo de ingreso valor prestamo'").locatedBy("//input[@id='requested-amount-calculator']");
    public static final Target TEXTFIELD_PLAZO_PRESTAMO = Target.the("'Campo de ingreso tiempo plazo prestamo'").locatedBy("//input[@id='loan-term-years-calculator']");
    public static final Target BUTTON_TASA_AMORTIZACION = Target.the("'Boton Tipo Tasa Amortizacion'").locatedBy("(//div[@class='box-title'][contains(.,'{0}')])[1]");
    public static final Target BUTTON_CALCULAR = Target.the("'Boton Calcular'").locatedBy("//pichincha-button[@id=\"calculateButton\"]");
    public static final Target LABEL_CUOTA_MENSUAL = Target.the("'Label Cuota Mensual'").locatedBy("//div[@class='monthly-fee']");
    public static final Target LABEL_TASA_INTERES = Target.the("'Label Tasa Interes'").located(By.cssSelector(".results-container .grid-spacing-2 > pichincha-grid:nth-of-type(2) > div > pichincha-grid > div > pichincha-grid > div > div:nth-of-type(2) > div > div > div:nth-of-type(5) > div:nth-of-type(2) > pichincha-typography"));
    public static final Target LABEL_GASTOS_AVALUO = Target.the("'Label Gastos Avaluo'").located(By.cssSelector(".results-container .grid-spacing-2 > pichincha-grid:nth-of-type(2) > div > pichincha-grid > div > pichincha-grid:nth-of-type(2) > div > div:nth-of-type(2)  > div > div > pichincha-grid:nth-of-type(2) > div > pichincha-grid:nth-of-type(3) > div > div > pichincha-typography"));
}
