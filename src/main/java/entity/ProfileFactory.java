package entity;

public interface ProfileFactory {
    PersonalProfile createPersonalProfile(String username, Language language);
}
