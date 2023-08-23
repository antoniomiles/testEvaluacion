package com.pichincha.automationtest.tasks.demo.retrieveusername;

import com.pichincha.automationtest.tasks.demo.retrieveusername.enums.VarMsGraph;
import com.pichincha.automationtest.ui.demo.PageMsGraph;
import com.pichincha.automationtest.util.EnvironmentConfig;
import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.markers.IsSilent;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.Switch;
import net.serenitybdd.screenplay.waits.WaitUntil;
import net.thucydides.core.annotations.Step;

import static net.serenitybdd.core.Serenity.getDriver;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

@Slf4j
public class MakeLoginMsGraph implements Task, IsSilent {

    EnvironmentConfig environmentConfig = new EnvironmentConfig();
    private final String user;
    private final String pass;
    private final String msGraphToken = VarMsGraph.MSGRAPH_TOKEN.getVarName();

    public MakeLoginMsGraph(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

    public static MakeLoginMsGraph whitCredentials(String user, String pass) {
        return Tasks.instrumented(MakeLoginMsGraph.class, user, pass);
    }

    @Step("{0} inicia sesión en MsGraph")
    @Override
    public <T extends Actor> void performAs(T actor) {
        String tokenEnv = environmentConfig.getVariable(msGraphToken);
        if (tokenEnv == null || tokenEnv.isEmpty()) {
            actor.attemptsTo(
                    Click.on(PageMsGraph.BUTTON_SINGIN),
                    Switch.toNewWindow(),
                    Enter.keyValues(this.user).into(PageMsGraph.INPUT_EMAIL),
                    Click.on(PageMsGraph.BUTTON_NEXT),
                    WaitUntil.the(PageMsGraph.TITLE_ENTER_PASSWORD, isVisible()),
                    Enter.keyValues(this.pass).into(PageMsGraph.INPUT_PASSWORD),
                    Click.on(PageMsGraph.BUTTON_COMPLETE_SINGIN),
                    Click.on(PageMsGraph.BUTTON_NO)
            );
            String accessToken;
            do {
                accessToken = environmentConfig.getVariable(msGraphToken);
            } while (accessToken == null || accessToken.isEmpty());
            getDriver().close();
        }
    }
}