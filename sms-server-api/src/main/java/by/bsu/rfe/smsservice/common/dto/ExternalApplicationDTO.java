package by.bsu.rfe.smsservice.common.dto;

/**
 * Created by pluhin on 9/3/16.
 */
public class ExternalApplicationDTO extends CreatedDetails {
    private Integer id;
    private String applicationName;
    private String authenticationToken;
    private String credentialsSenderName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getAuthenticationToken() {
        return authenticationToken;
    }

    public void setAuthenticationToken(String authenticationToken) {
        this.authenticationToken = authenticationToken;
    }

    public String getCredentialsSenderName() {
        return credentialsSenderName;
    }

    public void setCredentialsSenderName(String credentialsSenderName) {
        this.credentialsSenderName = credentialsSenderName;
    }
}
