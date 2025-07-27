package interface_adapter.view_history;

import use_case.viewhistory.ViewHistoryEntryData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ViewHistoryState {

    private String currentUser = "";
    private List<ViewHistoryEntryData> sessions = Collections.synchronizedList(new ArrayList<>());
    private int totalSessions = 0;
    private int totalWords = 0;
    private String errorMessage = null;

    // Getters
    public String getCurrentUser() {
        return currentUser;
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

    public String getErrorMessage() {
        return errorMessage;
    }

    // Setters
    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public void setSessions(List<ViewHistoryEntryData> sessions) {
        this.sessions = sessions != null ? sessions : new ArrayList<>();
    }

    public void setTotalSessions(int totalSessions) {
        this.totalSessions = totalSessions;
    }

    public void setTotalWords(int totalWords) {
        this.totalWords = totalWords;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}