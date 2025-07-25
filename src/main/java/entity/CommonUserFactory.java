package entity;

/**
 * Factory for creating CommonUser objects.
 */
public class CommonUserFactory implements UserFactory {

    @Override
    public User create(String name, String password) {
        return new CommonUser(name, password);
    }

    @Override
    public User create(String name, String password, String securityQuestion, String securityAnswer) {
        return null;
    }
}
