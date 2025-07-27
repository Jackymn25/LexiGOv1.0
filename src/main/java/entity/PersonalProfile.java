package entity;

public class PersonalProfile {

    private String language;
    private String username;

    public PersonalProfile(String username, String language) {
        this.username = username;
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

    public String getUsername() {
        return username;
    }

}
