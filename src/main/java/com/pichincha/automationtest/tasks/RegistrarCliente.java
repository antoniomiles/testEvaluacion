package com.pichincha.automationtest.tasks;

import com.pichincha.automationtest.model.Cliente;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;

import static com.pichincha.automationtest.userinterface.CarritoCompra.*;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class RegistrarCliente implements Task {

    private Cliente cliente;

    public RegistrarCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public static RegistrarCliente conInformacionCompra(Cliente cliente) {
        return instrumented(RegistrarCliente.class, cliente);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(PLACE_ORDER),
                Task.where("{0} registra sus datos de compra",
                        Enter.theValue(cliente.getNombre()).into(NAME),
                        Enter.theValue(cliente.getPais()).into(COUNTRY),
                        Enter.theValue(cliente.getCiudad()).into(CITY),
                        Enter.theValue(cliente.getNumeroTarjeta()).into(CREDIT_CARD),
                        Enter.theValue(cliente.getMesVencimiento()).into(MONTH),
                        Enter.theValue(cliente.getAnioVencimiento()).into(YEAR)),
                Click.on(PURCHASE));
    }
}