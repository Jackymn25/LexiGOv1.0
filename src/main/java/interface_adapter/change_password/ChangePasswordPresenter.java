package interface_adapter.change_password;

import use_case.change_password.ChangePasswordOutputBoundary;
import use_case.change_password.ChangePasswordOutputData;

/**
 * The Presenter for the Change Password Use Case.
 */
public class ChangePasswordPresenter implements ChangePasswordOutputBoundary {

    private final ChangeViewModel changeViewModel;

    public ChangePasswordPresenter(ChangeViewModel loggedInViewModel) {
        this.changeViewModel = loggedInViewModel;
    }

    @Override
    public void preparePage(ChangePasswordOutputData outputData) {
        ChangePwState state = changeViewModel.getState();
        state.setUsername(outputData.getUsername());
        state.setVerification(outputData.isNeedVerified());
        state.setSecurityQuestion(outputData.getSecurityQuestion());
        state.setPassword("");
        changeViewModel.setState(state);
        changeViewModel.firePropertyChanged();
    }
}
