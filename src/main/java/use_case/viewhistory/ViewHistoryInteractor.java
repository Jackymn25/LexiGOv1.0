package use_case.viewhistory;

import entity.LearnRecord;
import entity.ViewHistoryEntity;
import use_case.gateway.UserRecordDataAccessInterface;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ViewHistoryInteractor implements ViewHistoryInputBoundary {

    private final UserRecordDataAccessInterface dataAccess;
    private final ViewHistoryOutputBoundary presenter;
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ViewHistoryInteractor(UserRecordDataAccessInterface dataAccess,
                                 ViewHistoryOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(ViewHistoryInputData inputData) {
        String username = inputData.getUsername();

        List<LearnRecord> records = dataAccess.get(username);

        if (records.isEmpty()) {
            presenter.prepareFailView("No learning records found for user: " + username);
            return;
        }

        List<ViewHistoryEntity> sortedEntries = records.stream()
                .sorted(Comparator.comparing(LearnRecord::getEndTime))
                .map(record -> new ViewHistoryEntity(
                        record.getUsername(),
                        record.getEndTime(),
                        record.getLearnedWordIds(),
                        0
                ))
                .collect(Collectors.toList());

        List<ViewHistoryEntity> numberedEntries = new ArrayList<>();
        for (int i = 0; i < sortedEntries.size(); i++) {
            ViewHistoryEntity oldEntry = sortedEntries.get(i);
            ViewHistoryEntity newEntry = new ViewHistoryEntity(
                    oldEntry.getUsername(),
                    oldEntry.getEndTime(),
                    oldEntry.getLearnedWordIds(),
                    i + 1
            );
            numberedEntries.add(newEntry);
        }

        List<ViewHistoryEntryData> sessionData = numberedEntries.stream()
                .map(entry -> new ViewHistoryEntryData(
                        entry.getSessionNumber(),
                        entry.getEndTime().format(FORMATTER),
                        entry.getWordsCount()
                ))
                .collect(Collectors.toList());

        int totalSessions = sessionData.size();
        int totalWords = sessionData.stream()
                .mapToInt(ViewHistoryEntryData::getWordsCount)
                .sum();

        ViewHistoryOutputData outputData = new ViewHistoryOutputData(
                username, sessionData, totalSessions, totalWords);

        presenter.prepareSuccessView(outputData);


    }
}