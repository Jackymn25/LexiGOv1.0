package interface_adapter.achievement;

import interface_adapter.ViewModel;


public class AchievementViewModel extends ViewModel<AchievementState> {
    private AchievementState state;

    public AchievementViewModel() {
        super("achievement");// this is the screen name
        this.state = new AchievementState();
    }

    public AchievementState getState() {
        return state;
    }

    public void setState(AchievementState state) {
        this.state = state;
    }
}