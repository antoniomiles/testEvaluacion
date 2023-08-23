package com.pichincha.automationtest.actions;

import com.pichincha.automationtest.tasks.demo.retrieveusername.enums.VarMsGraph;
import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.thucydides.core.annotations.Step;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static net.serenitybdd.screenplay.Tasks.instrumented;

@Slf4j
public class BPRememberDate implements Interaction {

    public static BPRememberDate current() {
        return instrumented(BPRememberDate.class);
    }

    @Override
    @Step("{0} guarda un recuerdo de fecha y hora")
    public <T extends Actor> void performAs(T actor) {
        ZonedDateTime currentZonedDateTime = ZonedDateTime.now(ZoneId.of("Zulu"));
        currentZonedDateTime = currentZonedDateTime.minusSeconds(2);
        String currentDateTime = currentZonedDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
        currentDateTime = currentDateTime.replace("[Zulu]", "");
        actor.remember(VarMsGraph.SEND_DATE.getVarName(), currentDateTime);
    }
}