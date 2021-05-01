package Models;

public class UserModel {

    private String name;
    private String lastname;
    private String identification;
    private String year;

    private UserModel(){}

    public UserModel(String name, String lastname, String identification, String year) {
        this.name = name;
        this.lastname = lastname;
        this.identification = identification;
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
