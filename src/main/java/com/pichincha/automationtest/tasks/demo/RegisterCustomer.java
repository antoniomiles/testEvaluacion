package com.pichincha.automationtest.tasks.demo;

import com.pichincha.automationtest.model.demo.Customer;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.thucydides.core.annotations.Step;

import static com.pichincha.automationtest.ui.demo.PageCart.*;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class RegisterCustomer implements Task {

    private final Customer customer;

    public RegisterCustomer(Customer customer) {
        this.customer = customer;
    }

    public static RegisterCustomer withInformation(Customer customer) {
        return instrumented(RegisterCustomer.class, customer);
    }

    @Step("{0} completa datos de compra")
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(PLACE_ORDER),
                Enter.theValue(customer.getName()).into(NAME),
                Enter.theValue(customer.getCountry()).into(COUNTRY),
                Enter.theValue(customer.getCity()).into(CITY),
                Enter.theValue(customer.getCarNumber()).into(CREDIT_CARD),
                Enter.theValue(customer.getExpirationMonth()).into(MONTH),
                Enter.theValue(customer.getExpirationYear()).into(YEAR),
                Click.on(PURCHASE)
        );
    }
}