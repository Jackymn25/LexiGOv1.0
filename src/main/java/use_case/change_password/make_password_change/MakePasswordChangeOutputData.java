package use_case.change_password.make_password_change;

public class MakePasswordChangeOutputData {

    private final String errorMessage;

    public MakePasswordChangeOutputData(String passwordCannotBeEmpty) {
        this.errorMessage = passwordCannotBeEmpty;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
