package com.pichincha.automationtest.tasks.demo;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.thucydides.core.annotations.Step;
import net.serenitybdd.screenplay.Task;

import com.pichincha.automationtest.model.ModelCalculadoraHipotecario;
import static com.pichincha.automationtest.ui.demo.CalculadoraHipotecario.*;



public class IngresarDatosCalculadora implements Task {
    private final ModelCalculadoraHipotecario data;

    public IngresarDatosCalculadora(ModelCalculadoraHipotecario data) {
        this.data = data;
    }

    public static IngresarDatosCalculadora conDatos(ModelCalculadoraHipotecario data) {
        return Tasks.instrumented(IngresarDatosCalculadora.class, data);
    }

    @Step("{0} Ingreso de datos a la calculadora y doy click:")
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(BUTTON_TIPO_VIVIENDA.of(data.getProductoInteres())),
                Enter.keyValues(data.getCosto()).into(TEXTFIELD_VALOR_VIVIENDA),
                Enter.keyValues(data.getMontoSolicitud()).into(TEXTFIELD_VALOR_PRESTAMO),
                Enter.keyValues(data.getPlazoAnios()).into(TEXTFIELD_PLAZO_PRESTAMO),
                Click.on(BUTTON_TASA_AMORTIZACION.of(data.getAmortizacion())),
                Click.on(BUTTON_CALCULAR)
        );
    }
}
