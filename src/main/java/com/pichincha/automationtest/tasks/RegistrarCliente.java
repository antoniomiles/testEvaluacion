package com.pichincha.automationtest.tasks;


import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;

import static com.pichincha.automationtest.userinterface.CarritoCompra.*;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class RegistrarCliente implements Task {


    private String nombre;
    private String ciudad;
    private String pais;
    private String numeroDeTarjeta;
    private String mesVencimiento;
    private String anioVencimiento;

    public RegistrarCliente(String nombre, String pais, String ciudad, String numeroDeTarjeta, String mesVencimiento, String anioVencimiento) {
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.pais = pais;
        this.numeroDeTarjeta = numeroDeTarjeta;
        this.mesVencimiento = mesVencimiento;
        this.anioVencimiento = anioVencimiento;


    }
    public static RegistrarCliente conInformacionCompra(String nombre, String pais, String ciudad, String numeroDeTarjeta, String mesVencimiento, String anioVencimiento) {
        return instrumented(RegistrarCliente.class,nombre,pais,ciudad,numeroDeTarjeta,mesVencimiento,anioVencimiento);

    }
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(PLACE_ORDER),
                Task.where("{0} registra sus datos de compra",
                        Enter.theValue(nombre).into(NAME),
                        Enter.theValue(pais).into(COUNTRY),
                        Enter.theValue(ciudad).into(CITY),
                        Enter.theValue(numeroDeTarjeta).into(CREDIT_CARD),
                        Enter.theValue(mesVencimiento).into(MONTH),
                        Enter.theValue(anioVencimiento).into(YEAR)),
                Click.on(PURCHASE)
        );
    }
}