package com.pichincha.automationtest.tasks.demo.retrieveusername;

import com.pichincha.automationtest.tasks.demo.retrieveusername.enums.VarMsGraph;
import com.pichincha.automationtest.ui.demo.PageMsGraph;
import com.pichincha.automationtest.util.EnvironmentConfig;
import com.pichincha.automationtest.util.PropertiesReader;
import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actions.WithDevTools;
import net.thucydides.core.annotations.Step;
import org.json.JSONObject;
import org.openqa.selenium.devtools.v112.network.Network;
import org.openqa.selenium.devtools.v112.network.model.RequestId;

import java.util.Optional;

import static net.serenitybdd.screenplay.GivenWhenThen.andThat;

@Slf4j
public class GetTokenMsGraph implements Task {

    EnvironmentConfig environmentConfig = new EnvironmentConfig();
    private final String msGraphToken = VarMsGraph.MSGRAPH_TOKEN.getVarName();

    public static GetTokenMsGraph delegate() {
        return Tasks.instrumented(GetTokenMsGraph.class);
    }

    @Step("{0} inicia proceso para obtener token")
    @Override
    public <T extends Actor> void performAs(T actor) {
        PropertiesReader propertiesReader = new PropertiesReader();
        String token = propertiesReader.getProperty(msGraphToken, "msgraph.properties");
        String tokenEnv = environmentConfig.getVariable(msGraphToken);
        if (token == null || token.isEmpty()) {

            if (tokenEnv == null || tokenEnv.isEmpty()) {
                final RequestId[] requestIds = new RequestId[1];

                andThat(actor).attemptsTo(
                        Open.browserOn().the(PageMsGraph.class),
                        WithDevTools.perform(
                                devTools -> {
                                    devTools.createSession();
                                    devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
                                    devTools.addListener(Network.responseReceived(), response -> {
                                                if (response.getResponse().getUrl().contains("oauth2/v2.0/token")) {
                                                    requestIds[0] = response.getRequestId();
                                                    String responseBody = devTools.send(Network.getResponseBody(requestIds[0])).getBody();
                                                    JSONObject responseBodyJSON = new JSONObject(responseBody);
                                                    String accessToken = responseBodyJSON.getString("access_token");
                                                    System.setProperty(msGraphToken, accessToken);
                                                    devTools.close();
                                                }
                                            }
                                    );
                                }
                        )
                );
            }
        } else {
            if (tokenEnv == null || tokenEnv.isEmpty()) {
                System.setProperty(msGraphToken, token);
            }
        }
    }
}