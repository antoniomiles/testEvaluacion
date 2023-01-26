package com.pichincha.automationtest.glue;

import com.pichincha.automationtest.util.EnvironmentConfig;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Steps;

import java.util.logging.Level;
import java.util.logging.Logger;

public class RestsAssuredGlue {

    @Steps
    private EnvironmentConfig env;
    private Response response;
    private String userId;
    private String userCreated;

    @Given("que creo al nuevo usuario {string} con el trabajo {string}")
    public void queCreoAlNuevoUsuarioConElTrabajo(String userName, String job) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO,
                "Creando al nuevo usuario \"{0}\" con el trabajo \"{1}\"", new Object[]{userName, job});
        //El body request tambien puede ser leido desde un archivo externo: JSON
        String bodyRequest = "{\n" +
                "    \"name\": \""+userName+"\",\n" +
                "    \"job\": \""+job+"\"\n" +
                "}";

        response =  SerenityRest.
                given()
                //configuracion de cabeceras, pueden enviarse de la siguiente manera, en forma de mapas Map<?,?>
                // y a traves del objecto Headers de RestAssured
                .header("Content-Type", "application/json")
                //envio del parametro bodyRequest
                .body(bodyRequest)
                .when().log().all() //retorna log total en consola de la peticion
                //llamado al metodo de accion para consumir el servicio. Estan disponibles todos los metodos HTTP
                //Dentro del metodo se envia la URL del servicio a consultar
                .post(env.getVariable("api.base") + "/api/users");

        response.then().statusCode(201); //validación del código de respuesta
        response.prettyPeek(); //metodo que imprime la respuesta de manera ordenada
    }

    @And("obtengo el id del nuevo usuario")
    public void obtengoElIdDelNuevoUsuario() {
        userId = JsonPath.from(response.asString()).get("id");
        userCreated = JsonPath.from(response.asString()).get("name");
        Logger.getLogger(this.getClass().getName()).log(Level.INFO,
                "Id del nuevo usuario creado: {0}", userId);
    }

    @When("actualizo al usuario creado con el nuevo trabajo {string}")
    public void actualizoAlUsuarioCreadoConElNuevoTrabajo(String newJob) {
        String bodyRequest = "{\n" +
                "    \"name\": \""+userCreated+"\",\n" +
                "    \"job\": \""+newJob+"\"\n" +
                "}";

        response = SerenityRest.given()
                .header("Content-Type", "application/json")
                .body(bodyRequest)
                .pathParam("userId", userId)
                .when().log().all()
                .put(env.getVariable("api.base") + "/api/users/{userId}");
    }

    @Then("valido que el nuevo trabajo del usuario creado haya sido actualizado")
    public void validoQueElNuevoTrabajoDelUsuarioCreadoHayaSidoActualizado() {
        response.then().statusCode(200);
        response.prettyPeek();
    }
}
