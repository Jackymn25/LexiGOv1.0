package use_case.change_password;

/**
 * The Change Password Interactor.
 */
public class ChangePasswordInteractor implements ChangePasswordInputBoundary {

    private final ChangePasswordOutputBoundary userPresenter;
    private final ChangePasswordUserTypeDataAccessInterface userdatagetter;

    public ChangePasswordInteractor(ChangePasswordOutputBoundary changePasswordOutputBoundary,
                                    ChangePasswordUserTypeDataAccessInterface userdatagetter) {
        this.userPresenter = changePasswordOutputBoundary;
        this.userdatagetter = userdatagetter;
    }

    @Override
    public void execute(ChangePasswordInputData inputData) {

        boolean verification;
        String type = userdatagetter.getType(inputData.getUsername());

        switch (type) {
            default:
                verification = false;
                userPresenter.preparePage(new ChangePasswordOutputData(inputData.getUsername(), verification,
                        null));
                break;
            case "SECURITY":
                verification = true;
                String securityQuestion = userdatagetter.getSecurityQuestion(inputData.getUsername());
                userPresenter.preparePage(new ChangePasswordOutputData(inputData.getUsername(), verification,
                        securityQuestion));
        }


    }

}
