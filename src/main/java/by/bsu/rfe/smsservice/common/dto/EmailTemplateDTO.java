package by.bsu.rfe.smsservice.common.dto;

import by.bsu.rfe.smsservice.common.entity.CreationDetails;

/**
 * Created by pluhin on 9/4/16.
 */
public class EmailTemplateDTO extends CreationDetails {
    private Integer id;
    private String subject;
    private String content;
    private String smsType;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSmsType() {
        return smsType;
    }

    public void setSmsType(String smsType) {
        this.smsType = smsType;
    }
}
