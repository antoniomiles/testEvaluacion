package com.pichincha.automationtest.questions;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.waits.WaitUntil;
import java.util.List;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
public class QuesGetTotal implements  Question<String> {

    private final List<Target> targets;

    public QuesGetTotal(List<Target> targets) {
            this.targets = targets;
            }

    public static QuesGetTotal getTotal(List<Target> targets) {
            return new QuesGetTotal(targets);
            }

    @Override
    public String answeredBy(Actor actor) {
            double total = 0;
            targets.forEach(target -> {
                String value = target.resolveFor(actor).getText();
                total = value.replace("$","").replace(",",".")
            });
            return Double.toString(total);
    }
}
