package org.sega.viewer.models;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users")
public class User extends BaseModel{
    public static final String ROLE_OPERATOR = "ROLE_OPERATOR";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    @Column(unique = true)
    private String email;

    @JsonIgnore
    private String password;

    private String role = User.ROLE_OPERATOR;

    protected User() {

    }

    public User(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
