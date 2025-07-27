package use_case.viewhistory;

public class ViewHistoryEntryData {

    private final int sessionNumber;
    private final String endTime;
    private final int wordsCount;

    public ViewHistoryEntryData(int sessionNumber, String endTime, int wordsCount) {
        this.sessionNumber = sessionNumber;
        this.endTime = endTime;
        this.wordsCount = wordsCount;
    }

    public int getSessionNumber() {
        return sessionNumber;
    }

    public String getEndTime() {
        return endTime;
    }

    public int getWordsCount() {
        return wordsCount;
    }
}