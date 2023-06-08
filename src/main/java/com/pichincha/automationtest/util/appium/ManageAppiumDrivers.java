package com.pichincha.automationtest.util.appium;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import net.thucydides.core.environment.SystemEnvironmentVariables;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.webdriver.WebDriverFacade;

import static net.serenitybdd.core.Serenity.getDriver;

public class ManageAppiumDrivers {

    private ManageAppiumDrivers() {
    }

    private static final EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();
    public static final String PLATFORM_NAME = variables.getProperty("appium.platformName");
    public static final String PLATFORM_VERSION = variables.getProperty("appium.platformVersion");
    public static final String IOS = "iOS";

    private static IOSDriver getIosDriver(){
        return ((IOSDriver) ((WebDriverFacade) getDriver()).getProxiedDriver());
    }

    private static AndroidDriver getAndroidDriver(){
        return ((AndroidDriver) ((WebDriverFacade) getDriver()).getProxiedDriver());
    }

    private static AppiumDriver getAppiumDriver(){
        return ((AppiumDriver) ((WebDriverFacade) getDriver()).getProxiedDriver());
    }


    public static void iosDriverHideKeyboard() {
        if (PLATFORM_VERSION.equals("16.1.2")){
            getAppiumDriver().findElement(AppiumBy.accessibilityId("Done")).click();
        }else{
            getIosDriver().hideKeyboard();
        }
    }

}
