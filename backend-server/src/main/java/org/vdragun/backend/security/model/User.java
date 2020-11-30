package org.vdragun.backend.security.model;

public class User {

    private String username;

    private String password;

    private String code;

    public static User forInitialRequest(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        return user;
    }

    public static User forOtpCheck(String username, String code) {
        User user = new User();
        user.setUsername(username);
        user.setCode(code);

        return user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
