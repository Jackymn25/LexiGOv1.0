package use_case.change_password;

public interface ChangePasswordUserTypeDataAccessInterface {
    String getType(String username);

    String getSecurityQuestion(String username);
}
