package data_access;

import entity.LearnRecord;
import use_case.gateway.UserRecordDataAccessInterface;
import use_case.viewhistory.ViewHistoryUserDataAccessInterface;

import java.util.List;

/**
 * Adapter that allows JsonUserRecordDataAccessObject to be used as ViewHistoryUserDataAccessInterface.
 * This follows the Adapter pattern and maintains separation of concerns.
 */
public class ViewHistoryDataAccessAdapter implements ViewHistoryUserDataAccessInterface {

    private final UserRecordDataAccessInterface userRecordDAO;

    public ViewHistoryDataAccessAdapter(UserRecordDataAccessInterface userRecordDAO) {
        this.userRecordDAO = userRecordDAO;
    }

    @Override
    public List<LearnRecord> get(String username) {
        return userRecordDAO.get(username);
    }
}