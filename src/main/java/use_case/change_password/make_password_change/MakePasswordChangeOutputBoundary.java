package use_case.change_password.make_password_change;

public interface MakePasswordChangeOutputBoundary {
    void presentFailure(MakePasswordChangeOutputData out);

    void presentSuccess();
}
