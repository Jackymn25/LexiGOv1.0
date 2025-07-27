package entity;

/** Produces SecurityUser instances that require a security question / answer. */
public final class SecurityUserFactory implements UserFactory {

    @Override
    public User create(String name, String password) {
        throw new UnsupportedOperationException(
                "SecurityUser requires a security question/answer. "
                        + "Use the 4‑argument create method.");
    }

    @Override
    public User create(String name, String password,
                       String securityQuestion,
                       String securityAnswer) {
        return new SecurityUser(name, password,
                securityQuestion, securityAnswer);
    }
}
