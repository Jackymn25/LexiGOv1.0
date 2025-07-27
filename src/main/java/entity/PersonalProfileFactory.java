package entity;

public class PersonalProfileFactory implements ProfileFactory {
    @Override
    public PersonalProfile createPersonalProfile(String username, String language) {
        return new PersonalProfile(username, language);
    }
}
