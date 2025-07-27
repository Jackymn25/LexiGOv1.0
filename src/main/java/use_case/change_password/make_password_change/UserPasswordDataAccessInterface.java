package use_case.change_password.make_password_change;

import entity.User;

public interface UserPasswordDataAccessInterface {
    void update(String username, User user);

    String getAnswer(String username);
    String getQuestion(String username);

    /**
     * Returns the user with the given username.
     * @param username the username to look up
     * @return the user with the given username
     */
    User get(String username);
}
