package com.pichincha.automationtest.ui.demo;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.By;
@DefaultUrl("page:webdriver.base.url.hipoteca")
public class PageHipotecas extends PageObject {
    public static final Target RADIO_BUTTON_SELECT= Target.the("'SELECCION_PRODUCTO'").located(By.id("setVipVisProductCheckboxInput"));
    public static final Target AMOUNT_INPUT= Target.the("'COSTO_VIVIENDA'").located(By.id("home-price-calculator"));
    public static final Target AMOUNTTWO_INPUT= Target.the("'PRESTAMO'").located(By.id("requested-amount-calculator"));
    public static final Target RADIO_BUTTON_SELECT_AMORT= Target.the("'SELECCION_AMORTIZACION'").located(By.id("setGermanAmortizationCheckboxInput"));
    public static final Target TERM_INPUT  = Target.the("'TIEMPO_PRESTAMO'").located(By.id("loan-term-years-calculator"));
    public static final Target CALCULATE_BUTTON = Target.the("'CALCULAR'").located(By.id("calculateButton"));

    public static final Target RESULT_MONTHLY_PAYMENT = Target.the("'Cuota Mensual'").located(By.xpath("//div[@class='monthly-fee']"));
    public static final Target RESULT_INTEREST_RATE = Target.the("'Tasa de Interés'").located(By.xpath("//pichincha-typography[contains(text(), '4.87%')]"));


    public static final Target RESULT_APPRAISAL_FEES = Target.the("'Gastos de Avalúo'").located(By.xpath("//pichincha-typography[text()='$94,08']"));

}
