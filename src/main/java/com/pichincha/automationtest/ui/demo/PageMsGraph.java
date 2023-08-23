package com.pichincha.automationtest.ui.demo;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.By;

@DefaultUrl("page:webdriver.base.url.msgraph")
public class PageMsGraph extends PageObject {

    public static final Target BUTTON_SINGIN = Target.the("'Button Sign in'").located(By.xpath("//button[@aria-label='Sign in']"));
    public static final Target INPUT_EMAIL = Target.the("'Input email'").located(By.name("loginfmt"));
    public static final Target BUTTON_NEXT = Target.the("'Button Next'").located(By.id("idSIButton9"));
    public static final Target TITLE_ENTER_PASSWORD = Target.the("'Title Enter Password'").locatedBy("//div[@role='heading' and text()='Enter password']");
    public static final Target INPUT_PASSWORD = Target.the("'Input password'").located(By.name("passwd"));
    public static final Target BUTTON_COMPLETE_SINGIN = Target.the("'Button Sign in'").located(By.id("idSIButton9"));
    public static final Target BUTTON_NO = Target.the("'Button No'").located(By.id("idBtn_Back"));
}