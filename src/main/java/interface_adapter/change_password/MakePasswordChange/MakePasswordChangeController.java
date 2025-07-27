package interface_adapter.change_password.MakePasswordChange;

import use_case.change_password.make_password_change.MakePasswordChangeInputBoundary;
import use_case.change_password.make_password_change.MakePasswordChangeInputData;

public class MakePasswordChangeController {

    private final MakePasswordChangeInputBoundary makePasswordChangeInteractor;

    public MakePasswordChangeController(MakePasswordChangeInputBoundary makePasswordChangeInteractor) {
        this.makePasswordChangeInteractor = makePasswordChangeInteractor;
    }

    public void execute(String username,
                        String newPassword,
                        String securityAnswer) {
        makePasswordChangeInteractor.make_password_change(new MakePasswordChangeInputData(username,
                newPassword,
                securityAnswer));
    }
}
