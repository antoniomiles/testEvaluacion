package com.pichincha.automationtest.glue.demo;

import com.pichincha.automationtest.model.ModelCalculadoraHipotecario;
import com.pichincha.automationtest.model.demo.Customer;
import com.pichincha.automationtest.questions.QuesGetText;
import com.pichincha.automationtest.tasks.demo.*;
import com.pichincha.automationtest.ui.demo.*;
import com.pichincha.automationtest.util.EnvironmentConfig;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.ensure.Ensure;

import java.util.List;
import java.util.Map;

import static com.pichincha.automationtest.ui.demo.PageCart.SUCCESSFULL_PURCHASE;
import static net.serenitybdd.screenplay.GivenWhenThen.*;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isPresent;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
import static net.serenitybdd.screenplay.questions.WebElementQuestion.the;
import static org.hamcrest.CoreMatchers.containsString;
import net.serenitybdd.screenplay.targets.Target;

@Slf4j
public class PruebaLucioAriasE2E {
    @Given("que el {actor} navega por la calculadora de hipotecario")
    public void queElClienteNavegaPorLaCalculadoraDeHipotecario(Actor actor) {
        givenThat(actor).attemptsTo(
                Open.browserOn().the(CalculadoraHipotecario.class)
        );
    }
    @When("selecciona el producto {string}, con un costo de ${string}, un monto a solicitar ${string}, un plazo de {string} años y una amortizacion {string}")
    public void seleccionaElProductoConUnCostoDe$AunUnPlazoDeAñosYUnaAmortizacion(String productoInteres, String costo, String montoSolicitud, String plazoAnios, String amortizacion) {
        ModelCalculadoraHipotecario calculadoraModel = new ModelCalculadoraHipotecario(productoInteres, costo, montoSolicitud, plazoAnios, amortizacion);
        when(theActorInTheSpotlight()).wasAbleTo(
                IngresarDatosCalculadora.conDatos(calculadoraModel)
        );
    }
    @Then("visualiza los resultados del calculo y valida la cuota mensual aproximada de ${string}, la tasa de interes del {string}% y gastos de avaluo del ${string}")
    public void visualizaLosResultadosDelCalculoYValidaLaCuotaMensualAproximadaDe$LaTasaDeInteresDelYGastosDeAvaluoDel$(String cuotaMensual, String tasaInteres, String gastosAvaluo) {
        String cuotaMensualFinal = "$"+cuotaMensual.replace(".",",");
        String tasaInteresFinal = tasaInteres+"%";
        String gastosAvaluoFinal = "$"+ gastosAvaluo.replace(".",",");
        List<Target> totalTargets = ;
        then(theActorInTheSpotlight()).should(
                seeThat("La cuota mensual es: ", QuesGetText.getText(CalculadoraHipotecario.LABEL_CUOTA_MENSUAL),containsString(cuotaMensualFinal)),
                seeThat("La tasa de interes es: ", QuesGetText.getText(CalculadoraHipotecario.LABEL_TASA_INTERES),containsString(tasaInteresFinal)),
                seeThat("El gasto de avaluo es: ", QuesGetText.getText(CalculadoraHipotecario.LABEL_GASTOS_AVALUO),containsString(gastosAvaluoFinal)),
                seeThat("El total de seguros, capital e interes es: ", QuesGetTotal.getTotal(List))

        );
    }
}
