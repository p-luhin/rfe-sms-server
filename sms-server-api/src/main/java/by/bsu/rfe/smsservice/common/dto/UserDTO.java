package by.bsu.rfe.smsservice.common.dto;

/**
 * Created by pluhin on 3/21/16.
 */
public class UserDTO extends CreatedDetails {
    private Integer id;
    private String username;
    private String token;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
