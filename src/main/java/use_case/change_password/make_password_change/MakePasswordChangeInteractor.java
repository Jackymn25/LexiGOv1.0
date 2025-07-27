package use_case.change_password.make_password_change;

import entity.BuiltInUserFactory;
import entity.User;
import entity.UserFactory;

public class MakePasswordChangeInteractor implements MakePasswordChangeInputBoundary {

    private final MakePasswordChangeOutputBoundary presenter;
    private final UserPasswordDataAccessInterface userDAO;
    private final UserFactory commomUserFactory;
    private final UserFactory securityUserFactory;

    public MakePasswordChangeInteractor(MakePasswordChangeOutputBoundary presenter,
                                        UserPasswordDataAccessInterface userDAO,
                                        UserFactory commomUserFactory, UserFactory securityUserFactory) {
        this.presenter = presenter;
        this.userDAO = userDAO;
        this.commomUserFactory = commomUserFactory;
        this.securityUserFactory = securityUserFactory;
    }

    @Override
    public void make_password_change(MakePasswordChangeInputData in) {
        User user = userDAO.get(in.getUsername());
        if (in.getSecurityAnswer() == null) {
            if (in.getNewPassword() == null) {
                presenter.presentFailure(new MakePasswordChangeOutputData("Password cannot be empty"));
                        // new output
                return;
            }
            User newuser = commomUserFactory.create(in.getUsername(), in.getNewPassword());
            userDAO.update(in.getUsername(), newuser);
            presenter.presentSuccess();
            return;
        }

        String answer = userDAO.getAnswer(in.getUsername());

        if (answer.equals(in.getSecurityAnswer())) {
            if (in.getNewPassword() == null) {
                presenter.presentFailure(new MakePasswordChangeOutputData("Password cannot be empty"));
                // new output
                return;
            }
            User newuser = securityUserFactory.create(in.getUsername(), in.getNewPassword(), userDAO.getQuestion(in.getUsername()), in.getSecurityAnswer());
            userDAO.update(in.getUsername(), newuser);
            presenter.presentSuccess();
        }

    }
}
