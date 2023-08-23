package com.pichincha.automationtest.tasks.demo.retrieveusername;

import com.pichincha.automationtest.util.EnvironmentConfig;
import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.thucydides.core.annotations.Step;

@Slf4j
public class RetrieveData implements Task {

    EnvironmentConfig environmentConfig = new EnvironmentConfig();
    public static RetrieveData fromMail() {
        return Tasks.instrumented(RetrieveData.class);
    }

    @Override
    @Step("{0} recupera data desde el mail")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                GetTokenMsGraph.delegate(),
                MakeLoginMsGraph.whitCredentials(environmentConfig.getVariable("MSG_USER"), environmentConfig.getVariable("MSG_PASS")),
                ConectionApiMsGraph.getMails()
        );
    }
}