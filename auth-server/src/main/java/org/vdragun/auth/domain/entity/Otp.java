package org.vdragun.auth.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * One-time password
 */
@Entity
@Table(name = "otps")
public class Otp {

    @Id
    private String username;

    @Column
    private String code;

    public Otp() {
    }

    public Otp(String username, String code) {
        this.username = username;
        this.code = code;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
