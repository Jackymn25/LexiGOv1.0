package use_case.change_password.make_password_change;

public class MakePasswordChangeInputData {
    private final String username;
    private final String newPassword;
    private final String securityAnswer;

    public MakePasswordChangeInputData(String username,
                                       String newPassword,
                                       String securityAnswer) {
        this.username = username;
        this.newPassword = newPassword;
        this.securityAnswer = securityAnswer;
    }

    public String getUsername() {
        return username;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }
}
