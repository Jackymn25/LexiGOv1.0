package entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


public class ViewHistoryEntity implements LearnRecord {

    private final String username;
    private final LocalDateTime endTime;
    private final List<UUID> learnedWordIds;
    private final int sessionNumber;

    public ViewHistoryEntity(String username, LocalDateTime endTime,
                             List<UUID> learnedWordIds, int sessionNumber) {
        this.username = username;
        this.endTime = endTime;
        this.learnedWordIds = List.copyOf(learnedWordIds);
        this.sessionNumber = sessionNumber;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }

    @Override
    public List<UUID> getLearnedWordIds() {
        return learnedWordIds;
    }

    public int getSessionNumber() {
        return sessionNumber;
    }

    public int getWordsCount() {
        return learnedWordIds.size();
    }



    @Override
    public String toString() {
        return String.format("ViewHistoryEntry{user='%s', session=%d, words=%d, time='%s'}",
                username, sessionNumber, getWordsCount(), endTime);
    }
}