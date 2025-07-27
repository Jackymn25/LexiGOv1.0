package interface_adapter.change_password.MakePasswordChange;

import interface_adapter.change_password.ChangePwState;
import interface_adapter.change_password.ChangeViewModel;
import use_case.change_password.make_password_change.MakePasswordChangeOutputBoundary;
import use_case.change_password.make_password_change.MakePasswordChangeOutputData;

public class MakePasswordChangePresenter implements MakePasswordChangeOutputBoundary {

    private final ChangeViewModel changeViewModel;

    public MakePasswordChangePresenter(ChangeViewModel changeViewModel) {
        this.changeViewModel = changeViewModel;
    }

    @Override
    public void presentFailure(MakePasswordChangeOutputData out) {
        ChangePwState state = changeViewModel.getState();
        state.setPassword("");
        state.setChangeError(out.getErrorMessage());
        changeViewModel.setState(state);
        changeViewModel.firePropertyChanged();
    }

    @Override
    public void presentSuccess() {
        ChangePwState state = changeViewModel.getState();
        state.setPassword("");
        state.setChangeError("");
        changeViewModel.setState(state);
        changeViewModel.firePropertyChanged();
    }
}
