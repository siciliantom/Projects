package by.bsu.hostel.domain;

/**
 * Created by Kate on 13.02.2016.
 */
public class Authentication extends Entity{
    private String login;
    private String password;
    private RoleEnum role;

    public Authentication() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Authentication{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
