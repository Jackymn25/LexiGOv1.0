package interface_adapter.view_history;

import interface_adapter.ViewModel;

public class ViewHistoryViewModel extends ViewModel<ViewHistoryState> {

    public static final String TITLE_LABEL = "Learning History";
    public static final String USER_LABEL = "Select User:";
    public static final String REFRESH_BUTTON_LABEL = "View History";
    public static final String SESSIONS_LABEL = "Total Sessions:";
    public static final String WORDS_LABEL = "Total Words:";

    public ViewHistoryViewModel() {
        super("view history");
        setState(new ViewHistoryState());
    }
}