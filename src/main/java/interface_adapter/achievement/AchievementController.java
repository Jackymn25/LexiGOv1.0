package interface_adapter.achievement;

import use_case.achievement.AchievementInputBoundary;
import use_case.achievement.AchievementInputData;

public class AchievementController {
    private final AchievementInputBoundary interactor;

    public AchievementController(AchievementInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * This method is called when the user opens the Achievements screen.
     * It sends a request to the interactor with the user's ID.
     *
     * @param userId the ID of the currently logged-in user
     */
    public void showAchievements(String userId) {
        AchievementInputData inputData = new AchievementInputData(userId);
        interactor.evaluate(inputData);
    }
}
