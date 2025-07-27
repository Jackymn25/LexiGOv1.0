package interface_adapter.change_password;

import interface_adapter.ViewModel;

public class ChangeViewModel extends ViewModel<ChangePwState> {
    public ChangeViewModel() {
        super("change password");
        setState(new ChangePwState());
    }
}
