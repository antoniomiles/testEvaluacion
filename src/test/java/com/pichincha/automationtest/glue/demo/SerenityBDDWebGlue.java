package com.pichincha.automationtest.glue.demo;

import com.pichincha.automationtest.model.demo.Customer;
import com.pichincha.automationtest.questions.QuesGetText;
import com.pichincha.automationtest.tasks.demo.*;
import com.pichincha.automationtest.tasks.demo.retrieveusername.ProccessRetrieveUserName;
import com.pichincha.automationtest.tasks.demo.retrieveusername.RetrieveData;
import com.pichincha.automationtest.tasks.demo.retrieveusername.UseRetrievedData;
import com.pichincha.automationtest.tasks.demo.retrieveusername.enums.VarMsGraph;
import com.pichincha.automationtest.ui.demo.*;
import com.pichincha.automationtest.util.EnvironmentConfig;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actions.Open;

import java.util.List;
import java.util.Map;

import static com.pichincha.automationtest.ui.demo.PageCart.SUCCESSFULL_PURCHASE;
import static net.serenitybdd.screenplay.GivenWhenThen.*;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isPresent;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
import static net.serenitybdd.screenplay.questions.WebElementQuestion.the;
import static org.hamcrest.CoreMatchers.*;

@Slf4j
public class SerenityBDDWebGlue {

    EnvironmentConfig environmentConfig = new EnvironmentConfig();

    @Given("que el cliente {actor} ingresa a la pagina SauceDemo")
    public void queElActorIngresaALaPaginaSauceDemo(Actor actor) {
        givenThat(actor).attemptsTo(
                Open.browserOn().the(PageSauceLogin.class)
        );
    }

    @When("el ingresa sus credenciales para iniciar sesion")
    public void elIngresaSusCredenciales(DataTable credentials) {
        List<Map<String, String>> rows = credentials.asMaps(String.class, String.class);
        for (Map<String, String> columns : rows) {
            when(theActorInTheSpotlight()).wasAbleTo(
                    MakeLogin.whitCredentials(columns.get("user"), columns.get("pass"))
            );
        }
    }

    @Then("el deberia ingresar a ver los productos disponibles")
    public void elDeberiaIngresarAVerLosProductosDisponibles() {
        then(theActorInTheSpotlight()).should(
                seeThat(the(PageSauceProducts.PRODUCT_TITLE), isVisible())
        );
    }

    @Then("se deberia mostrar el siguiente mensaje de error")
    public void seDeberiaMostrarElSiguienteMensajeDeError(String errorMessage) {
        then(theActorInTheSpotlight()).should(
                seeThat("Mensaje de Error ", QuesGetText.fromTarget(PageSauceLogin.LOCKED_ERROR), containsString(errorMessage))
        );
    }

    @Given("que el {actor} ingresa a la pagina de demoblaze para seleccionar el {string}")
    public void queElActorIngresaALaPaginaDeDemoblazeParaSeleccionarEl(Actor actor, String description) {
        log.info("ejemplo de log usando lombok joeslog");

        givenThat(actor).attemptsTo(
                Open.browserOn().the(PageMain.class)
        );

        andThat(actor).wasAbleTo(
                FindProduct.whitDescription(description),
                AddProducts.toCart(description)
        );
    }

    @When("el decide hacer la compra ingresa sus datos personales {string}, {string}, {string}, {string}, {string} y {string}")
    public void ySeIdentificaConLosDatosDeCompraY(String name, String country, String city, String carNumber, String expirationMonth, String expirationYear) {

        Customer dataCustomer = new Customer(name, country, city, carNumber, expirationMonth, expirationYear);

        when(theActorInTheSpotlight()).wasAbleTo(
                RegisterCustomer.withInformation(dataCustomer)
        );
    }

    @Then("el realiza la compra del producto exitosamente")
    public void completaLaCompraExitosamenteDelProducto() {
        then(theActorInTheSpotlight()).should(
                seeThat(the(SUCCESSFULL_PURCHASE), isPresent())
        );
    }

    @Given("que el {actor} inicia sesion en el aplicativo con sus credenciales")
    public void queElClienteIniciaSesionEnElAplicativoConSusCredenciales(Actor actor) {
        givenThat(actor).attemptsTo(
                Open.browserOn().the(PageLoginAzureUI.class),
                LoginAzure.conCredenciales(environmentConfig.getVariable("QA-USER-AUTOMATION"), environmentConfig.getVariable("QA-PASS-AUTOMATION"))
        );
    }


    @Given("que el {actor} ingresa a la pagina de recordar usuario oracle")
    public void queElClienteIngresaALaPaginaDeRecordarUsuarioOracle(Actor actor) {
        givenThat(actor).attemptsTo(
                Open.browserOn().the(PageOracle.class)
        );
    }

    @When("decide realizar el proceso ingresando su mail {string}")
    public void decideRealizarElProcesoIngresandoSuMail(final String email) {
        Actor actor = theActorInTheSpotlight();

        when(actor).attemptsTo(
                ProccessRetrieveUserName.whitMail(email),
                RetrieveData.fromMail()
        );
    }

    @Then("obtiene el usuario de logueo")
    public void obtieneElUsuarioDeLogueo() {
        Actor actor = theActorInTheSpotlight();
        String userName = actor.recall(VarMsGraph.DATA_FROM_MAIL.getVarName());
        then(actor).attemptsTo(
                UseRetrievedData.inLoginOracle(userName)
        );

        actor.should(
                seeThat("'Valor userName: " + userName + "'", validation -> userName, not(equalTo("")))
        );
    }


}