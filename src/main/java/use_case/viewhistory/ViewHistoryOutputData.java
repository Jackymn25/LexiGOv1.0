
package use_case.viewhistory;

import java.util.List;

public class ViewHistoryOutputData {

    private final String username;
    private final List<ViewHistoryEntryData> sessions;
    private final int totalSessions;
    private final int totalWords;

    public ViewHistoryOutputData(String username,
                                 List<ViewHistoryEntryData> sessions,
                                 int totalSessions,
                                 int totalWords) {
        this.username = username;
        this.sessions = sessions;
        this.totalSessions = totalSessions;
        this.totalWords = totalWords;
    }

    public String getUsername() {
        return username;
    }

    public List<ViewHistoryEntryData> getSessions() {
        return sessions;
    }

    public int getTotalSessions() {
        return totalSessions;
    }

    public int getTotalWords() {
        return totalWords;
    }
}