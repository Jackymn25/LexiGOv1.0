package use_case.viewhistory;

import entity.LearnRecord;

import java.util.List;

/**
 * Service interface for processing learning records into ViewHistoryEntryData.
 * Similar to ScoreSortService in the rank module.
 */
public interface ViewHistoryProcessorService {
    /**
     * Processes learning records into ViewHistoryEntryData list.
     * @param records the learning records to process
     * @return processed list of ViewHistoryEntryData with session numbers and formatted dates
     */
    List<ViewHistoryEntryData> processRecords(List<LearnRecord> records);
}