package use_case.viewhistory;

public interface ViewHistoryOutputBoundary {
    void prepareSuccessView(ViewHistoryOutputData outputData);
    void prepareFailView(String errorMessage);
}