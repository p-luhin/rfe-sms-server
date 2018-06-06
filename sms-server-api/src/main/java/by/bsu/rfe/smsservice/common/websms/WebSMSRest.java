package by.bsu.rfe.smsservice.common.websms;

/**
 * Created by pluhin on 12/27/15.
 */
public enum WebSMSRest {
    SEND_MESSAGE("msg_send"),
    BULK_SEND_MESSAGE("msg_send_bulk"),
    BALANCE("user_balance");

    private static final String API_HOST = "http://cp.websms.by/?r=api/";

    private String apiCommand;

    WebSMSRest(String apiCommand) {
        this.apiCommand = apiCommand;
    }

    public String getApiEndpoint() {
        return API_HOST + apiCommand;
    }

    public String getApiCommand() {
        return apiCommand;
    }
}
