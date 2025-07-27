package use_case.change_password;

/**
 * Output Data for the Change Password Use Case.
 */
public class ChangePasswordOutputData {

    private final String username;
    private final boolean needVerified;
    private final String securityQuestion;

    public ChangePasswordOutputData(String username,
                                    boolean needVerified,
                                    String securityQuestion) {
        this.username = username;
        this.needVerified = needVerified;
        this.securityQuestion = securityQuestion;

    }

    public String getUsername() {
        return username;
    }

    public boolean isNeedVerified() {
        return needVerified;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }
}
