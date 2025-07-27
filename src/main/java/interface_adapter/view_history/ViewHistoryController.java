package interface_adapter.view_history;

import use_case.viewhistory.ViewHistoryInputBoundary;
import use_case.viewhistory.ViewHistoryInputData;


public class ViewHistoryController {

    private final ViewHistoryInputBoundary viewHistoryInteractor;

    public ViewHistoryController(ViewHistoryInputBoundary viewHistoryInteractor) {
        this.viewHistoryInteractor = viewHistoryInteractor;
    }


    public void execute(String username) {
        ViewHistoryInputData inputData = new ViewHistoryInputData(username);
        viewHistoryInteractor.execute(inputData);
    }
}