package com.pichincha.automationtest.util;

//import org.apache.log4j.Logger;

import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class CustomCucumberWithSerenityRunner extends Runner {

    //static final Logger LOGGER = Logger.getLogger(CustomCucumberRunner.class);
    private final Class<CucumberWithSerenity> classValue;
    private CucumberWithSerenity cucumberWithSerenity;

    public CustomCucumberWithSerenityRunner(final Class<CucumberWithSerenity> classValue) throws Exception {
        this.classValue = classValue;
        cucumberWithSerenity = new CucumberWithSerenity(classValue);
    }

    @Override
    public Description getDescription() {
       return cucumberWithSerenity.getDescription();
    }

    private void runAnnotatedMethods(final Class<?> annotation) throws Exception {
        if (!annotation.isAnnotation()) {
            return;
        }
        final Method[] methods = this.classValue.getMethods();
        for (final Method method : methods) {
            final Annotation[] annotations = method.getAnnotations();
            for (final Annotation item : annotations) {
                if (item.annotationType().equals(annotation)) {
                    method.invoke(null);
                    break;
                }
            }
        }
    }

    @Override
    public void run(final RunNotifier notifier) {
        try {
            runAnnotatedMethods(BeforeSuite.class);
            cucumberWithSerenity = new CucumberWithSerenity(classValue);
        } catch (Exception e) {
           // LOGGER.error(e.getMessage(), e);
            System.out.println("Z>>>>>>>>>>>ERROR: "+e);
        }
        cucumberWithSerenity.run(notifier);
        try {
            runAnnotatedMethods(AfterSuite.class);
        } catch (Exception e) {
            //LOGGER.error(e.getMessage(), e);
            System.out.println("Z>>>>>>>>>>>ERROR: "+e);
        }
    }
}
