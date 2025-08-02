package use_case.viewhistory;

import entity.LearnRecord;
import entity.ViewHistoryEntity;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Default implementation of ViewHistoryProcessorService.
 * Similar to DefaultScoreSort in the rank module.
 */
public class DefaultViewHistoryProcessorService implements ViewHistoryProcessorService {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public List<ViewHistoryEntryData> processRecords(List<LearnRecord> records) {
        if (records == null || records.isEmpty()) {
            return new ArrayList<>();
        }

        // Sort records by end time and create initial entities with session number 0
        List<ViewHistoryEntity> sortedEntries = records.stream()
                .sorted(Comparator.comparing(LearnRecord::getEndTime))
                .map(record -> new ViewHistoryEntity(
                        record.getUsername(),
                        record.getEndTime(),
                        record.getLearnedWordIds(),
                        0
                ))
                .collect(Collectors.toList());

        // Assign sequential session numbers starting from 1 and convert to ViewHistoryEntryData
        List<ViewHistoryEntryData> sessionData = new ArrayList<>();
        for (int i = 0; i < sortedEntries.size(); i++) {
            ViewHistoryEntity entry = sortedEntries.get(i);
            ViewHistoryEntryData entryData = new ViewHistoryEntryData(
                    i + 1, // session number starts from 1
                    entry.getEndTime().format(FORMATTER),
                    entry.getWordsCount()
            );
            sessionData.add(entryData);
        }

        return sessionData;
    }
}