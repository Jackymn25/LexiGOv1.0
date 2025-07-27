//package data_access;
//
//import entity.LearnRecord;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.stream.Collectors;
//
///**
// * ViewHistory
// * Based on the original JsonUserRecordDataAccessObject
// */
//public class ViewHistoryDataAccessObject implements ViewHistoryDataAccessInterface {
//
//    private final JsonUserRecordDataAccessObject jsonDAO;
//
//    public ViewHistoryDataAccessObject(JsonUserRecordDataAccessObject jsonDAO) {
//        this.jsonDAO = jsonDAO;
//    }
//
//    @Override
//    public List<LearnRecord> getUserRecords(String username) {
//        return jsonDAO.get(username);
//    }
//
//    @Override
//    public Set<String> getAllUsernames() {
//        Map<String, List<LearnRecord>> allUsers = jsonDAO.getAllUsers();
//        return allUsers.keySet().stream()
//                .filter(username -> username != null && !username.trim().isEmpty())
//                .collect(Collectors.toSet());
//    }
//
//    @Override
//    public boolean userExists(String username) {
//        if (username == null || username.trim().isEmpty()) {
//            return false;
//        }
//        return getAllUsernames().contains(username);
//    }
//}