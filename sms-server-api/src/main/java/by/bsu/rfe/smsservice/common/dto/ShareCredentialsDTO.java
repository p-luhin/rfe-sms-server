package by.bsu.rfe.smsservice.common.dto;

/**
 * Created by pluhin on 9/5/16.
 */
public class ShareCredentialsDTO {
    private Integer userId;
    private Integer credentialsId;
    private Integer applicationId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCredentialsId() {
        return credentialsId;
    }

    public void setCredentialsId(Integer credentialsId) {
        this.credentialsId = credentialsId;
    }

    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }
}
