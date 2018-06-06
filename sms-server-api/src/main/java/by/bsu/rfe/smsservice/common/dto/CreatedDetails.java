package by.bsu.rfe.smsservice.common.dto;

import java.util.Date;

/**
 * Created by pluhin on 9/3/16.
 */
public class CreatedDetails {
    private String createdBy;
    private Date createdDate;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
