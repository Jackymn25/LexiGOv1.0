package interface_adapter.change_password;

public class ChangePwState {

    private String username = "";
    private String changeError = "";
    private String password = "";
    private boolean verification;
    private String securityQuestion = null;

    public String getUsername() {
        return username;
    }

    public String getChangeError() {
        return changeError;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setChangeError(String usernameError) {
        this.changeError = usernameError;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isVerification() {
        return verification;
    }

    public void setVerification(boolean verification) {
        this.verification = verification;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }
}
