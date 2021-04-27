package org.launchcode.Financial_Budget.models;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Entity;


@Entity
public class User extends AbstractEntity {


    private String username;

    private String pwHash;

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User(String username, String password) {
        this.username = username;
        this.pwHash = encoder.encode(password);
    }

    public String getUsername() {
        return username;
    }
    public User() {}

    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwHash);
    }

}
