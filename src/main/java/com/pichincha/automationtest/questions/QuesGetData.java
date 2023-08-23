package com.pichincha.automationtest.questions;

import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Properties;

@Slf4j
public class QuesGetData implements Question<String> {

    private final String keywordToStart;
    private final int beginIndex;
    private final int endIndex;

    public QuesGetData(Properties msGraphProp) {
        this.keywordToStart = msGraphProp.get("msgraph.filter.keywordToStart").toString();
        this.beginIndex = Integer.parseInt(msGraphProp.get("msgraph.filter.beginIndex").toString());
        this.endIndex = Integer.parseInt(msGraphProp.get("msgraph.filter.endIndex").toString());
    }

    public static QuesGetData fromMail(Properties msGraphProp) {
        return new QuesGetData(msGraphProp);
    }

    @Override
    public String answeredBy(Actor actor) {
        String data = "";
        String responseBody = SerenityRest.lastResponse().getBody().asString();
        JSONObject responseBodyJSON = new JSONObject(responseBody);
        int numberEmailsRecovered = responseBodyJSON.getInt("@odata.count");//Numero de mails encontrados
        if (numberEmailsRecovered > 0) {
            String bodyHtml = getHtmlBodyEmailFromResponse(responseBodyJSON, numberEmailsRecovered);
            data = getOtpFromBodyHtml(bodyHtml);
        }
        return data;
    }

    private String getHtmlBodyEmailFromResponse(JSONObject responseBodyJSON, int numberEmailsRecovered) {
        JSONArray valueArray = responseBodyJSON.getJSONArray("value");
        String bodyHtml = "";
        if (!valueArray.isEmpty()) {
            JSONObject valueObject = valueArray.getJSONObject(numberEmailsRecovered - 1);
            JSONObject bodyObject = valueObject.getJSONObject("body");
            bodyHtml = bodyObject.getString("content");
        }
        return bodyHtml;
    }

    private String getOtpFromBodyHtml(String bodyHtml) {
        int numCharacters = keywordToStart.length();
        String otp = bodyHtml.substring(bodyHtml.indexOf(keywordToStart) + numCharacters);
        return otp.substring(beginIndex, endIndex);
    }
}
