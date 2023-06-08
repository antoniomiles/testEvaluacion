package com.pichincha.automationtest.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class QuesGetText implements Question<String> {

    private final Target target;

    public QuesGetText(Target target) {
        this.target = target;
    }

    public static QuesGetText getText(Target target) {
        return new QuesGetText(target);
    }

    @Override
    public String answeredBy(Actor actor) {
        actor.attemptsTo(
                WaitUntil.the(target, isVisible())
        );
        return target.resolveFor(actor).getText();
    }
}
