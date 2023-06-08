package com.pichincha.automationtest.tasks.demo;

import com.pichincha.automationtest.ui.demo.ScreenLoad;
import com.pichincha.automationtest.ui.demo.ScreensCreateOrChangePIN;
import com.pichincha.automationtest.ui.demo.ScreensShortEnrollment;
import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.waits.WaitUntil;

import java.time.Duration;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isEnabled;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isNotVisible;


@Slf4j
public class ShortEnrollment implements Task {

    private final String pin;

    public ShortEnrollment(String pin) {
        this.pin = pin;
    }

    public static Performable completeEnrollment(String pin) {
        return instrumented(ShortEnrollment.class, pin);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {

        actor.attemptsTo(
                WaitUntil.the(ScreenLoad.TEXTO_PANTALLA_DE_CARGA, isNotVisible()).forNoMoreThan(80).seconds()
        );
        log.info("Ya no es visible icono cargando");
        //Caso en que la cuenta previamente esta registrada en otro dicpositivo
        if (ScreensShortEnrollment.BOTON_CONTINUAR_REGISTRO.waitingForNoMoreThan(Duration.ofSeconds(2)).isVisibleFor(actor)) {
            actor.attemptsTo(
                    Click.on(ScreensShortEnrollment.BOTON_CONTINUAR_REGISTRO)
            );
        }
        log.info("Pasa condicional para validar si se muestra btn registro en este cel");
        log.info("Empiez logica ingresar pin");
        actor.attemptsTo(
                WaitUntil.the(ScreensCreateOrChangePIN.CAJA_DE_TEXTO_INGRESAPIN, isEnabled()).forNoMoreThan(90).seconds(),
                Enter.theValue(pin).into(ScreensCreateOrChangePIN.CAJA_DE_TEXTO_INGRESAPIN),
                Enter.theValue(pin).into(ScreensCreateOrChangePIN.CAJA_DE_TEXTO_REPITETUPIN),
                Click.on(ScreensCreateOrChangePIN.BOTON_CREARAHORA),
                WaitUntil.the(ScreensShortEnrollment.BOTON_INGRESAR_A_LA_BANCA_MOVIL, isEnabled()).forNoMoreThan(60).seconds(),
                Click.on(ScreensShortEnrollment.BOTON_INGRESAR_A_LA_BANCA_MOVIL)
        );
        log.info("Termina logiga de enrrollamiento");

    }
}
