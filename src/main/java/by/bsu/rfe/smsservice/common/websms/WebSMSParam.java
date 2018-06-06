package by.bsu.rfe.smsservice.common.websms;

/**
 * Created by pluhin on 12/27/15.
 */
public enum WebSMSParam {
    USER("user"),
    APIKEY("apikey"),
    RECIPIENTS("recipients"),
    MESSAGE("message"),
    MESSAGES("messages"),
    SENDER("sender"),
    TEST("test");

    private String requestParam;

    WebSMSParam(String requestParam) {
        this.requestParam = requestParam;
    }

    public String getRequestParam() {
        return requestParam;
    }
}
