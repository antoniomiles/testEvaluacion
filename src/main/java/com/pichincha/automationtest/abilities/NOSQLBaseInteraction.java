package com.pichincha.automationtest.abilities;

import com.pichincha.automationtest.util.dbconection.MongoUtils;
import net.serenitybdd.screenplay.Ability;
import net.serenitybdd.screenplay.Actor;

import java.util.Map;

public class NOSQLBaseInteraction implements Ability {
    private MongoUtils mongoUtils;

    public NOSQLBaseInteraction(Map<String, Object> config) {
        this.mongoUtils =
                new MongoUtils(config);
    }

    public static NOSQLBaseInteraction using(Map<String, Object> config) {
        return new NOSQLBaseInteraction(config);
    }

    public static NOSQLBaseInteraction as(Actor actor) {
        return actor.abilityTo(NOSQLBaseInteraction.class);
    }

    public MongoUtils getMongoUtils() {
        return mongoUtils;
    }
}
