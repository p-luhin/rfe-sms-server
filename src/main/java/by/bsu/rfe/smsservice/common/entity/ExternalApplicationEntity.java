package by.bsu.rfe.smsservice.common.entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by pluhin on 9/3/16.
 */
@Entity
@Table(name = "external_application")
public class ExternalApplicationEntity extends CreationDetails {

    @Column(name = "application_name", nullable = false)
    private String applicationName;

    @Column(name = "authentication", nullable = false)
    private String authenticationToken;

    @OneToOne
    @JoinColumn(name = "default_credentials")
    private CredentialsEntity defaultCredentials;

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

    public CredentialsEntity getDefaultCredentials() {
        return defaultCredentials;
    }

    public void setDefaultCredentials(CredentialsEntity defaultCredentials) {
        this.defaultCredentials = defaultCredentials;
    }
}
