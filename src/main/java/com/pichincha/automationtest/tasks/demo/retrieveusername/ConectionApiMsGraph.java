package com.pichincha.automationtest.tasks.demo.retrieveusername;

import com.pichincha.automationtest.exceptions.ExcRuntime;
import com.pichincha.automationtest.questions.QuesGetData;
import com.pichincha.automationtest.tasks.demo.retrieveusername.enums.VarMsGraph;
import com.pichincha.automationtest.util.EnvironmentConfig;
import com.pichincha.automationtest.util.PropertiesReader;
import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.markers.IsSilent;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.interactions.Get;
import net.thucydides.core.annotations.Step;

import java.util.Optional;
import java.util.Properties;

@Slf4j
public class ConectionApiMsGraph implements Task, IsSilent {

    public static final String MSGRAPH_PROPERTIES = "msgraph.properties";
    EnvironmentConfig environmentConfig = new EnvironmentConfig();
    PropertiesReader propertiesReader = new PropertiesReader();
    Properties msGraphProp;

    public static ConectionApiMsGraph getMails() {
        return Tasks.instrumented(ConectionApiMsGraph.class);
    }

    @Override
    @Step("{0} recupera mails desde API")
    public <T extends Actor> void performAs(T actor) {
        Optional<Properties> opProperties = propertiesReader.getPropValues(MSGRAPH_PROPERTIES);

        if (opProperties.isPresent()) {
            msGraphProp = opProperties.get();
        } else {
            throw new ExcRuntime("ERROR READING PROPERTIES " + MSGRAPH_PROPERTIES);
        }

        String urlBase = msGraphProp.get("msgraph.urlbase").toString();
        String endPoint = msGraphProp.get("msgraph.endPoint.getMessages").toString();
        String senderMail = msGraphProp.get("msgraph.filter.senderMail").toString();
        String subject = msGraphProp.get("msgraph.filter.subject").toString();
        String sendDate = actor.recall(VarMsGraph.SEND_DATE.getVarName());
        String finalEndpoint = String.format(endPoint, senderMail, subject, sendDate);
        log.info("=====> FinalEndPoint MsGraph: " + finalEndpoint);

        actor.whoCan(CallAnApi.at(urlBase));

        String retrievedData = "";
        long startTime = System.currentTimeMillis();
        long timeLimit = Long.parseLong(msGraphProp.get("msgraph.wait.formail").toString());

        do {
            actor.attemptsTo(
                    Get.resource(finalEndpoint).with(requestSpecification -> requestSpecification.header("Authorization", environmentConfig.getVariable(VarMsGraph.MSGRAPH_TOKEN.getVarName())))
            );
            retrievedData = QuesGetData.fromMail(msGraphProp).answeredBy(actor);
            long currentTime = System.currentTimeMillis();
            if (currentTime - startTime >= timeLimit) {
                log.info("=====> No se pudo recuperar data desde el mail | tiempo esperado: " + timeLimit + "millis");
                break;
            }
        } while (retrievedData == null || retrievedData.isEmpty());


        actor.remember(VarMsGraph.DATA_FROM_MAIL.getVarName(), retrievedData);
    }
}