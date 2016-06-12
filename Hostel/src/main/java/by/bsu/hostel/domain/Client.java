package by.bsu.hostel.domain;

/**
 * Created by Kate on 13.02.2016.
 */
public class Client extends Entity{
    private String name;
    private String surname;
    private String country;
    private Status status;
    private Authentication authentication;

    public Client(){
        this.status = new Status();
        this.authentication = new Authentication();
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", country='" + country + '\'' +
                ", status=" + status +
                ", authentication=" + authentication +
                '}';
    }
}
