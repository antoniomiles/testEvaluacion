package com.pichincha.automationtest.model;

import com.pichincha.automationtest.ui.PageHipotecas;

public class ResultadosCalculo {

    private String cuotaMensual;
    private String tasaInteres;
    private String gastosAvaluo;

    public ResultadosCalculo(String cuotaMensual, String tasaInteres, String gastosAvaluo) {
        this.cuotaMensual = cuotaMensual;
        this.tasaInteres = tasaInteres;
        this.gastosAvaluo = gastosAvaluo;
    }

    public String getCuotaMensual() {
        return cuotaMensual;
    }

    public String getTasaInteres() {
        return tasaInteres;
    }

    public String getGastosAvaluo() {
        return gastosAvaluo;
    }

    // Método para obtener los resultados de la interfaz de usuario
    public static ResultadosCalculo obtenerDesdeInterfaz() {
        return new ResultadosCalculo(
                PageHipotecas.RESULT_MONTHLY_PAYMENT.resolveFor(actor).getText(),
                PageHipotecas.RESULT_INTEREST_RATE.resolveFor(actor).getText(),
                PageHipotecas.RESULT_APPRAISAL_FEES.resolveFor(actor).getText()
        );
    }

    // Método para obtener los resultados esperados (puedes cambiar esto según tus necesidades)
    public static ResultadosCalculo obtenerResultadosEsperados() {
        return new ResultadosCalculo("CUOTA_ESPERADA", "TASA_ESPERADA", "GASTOS_ESPERADOS");
    }
}