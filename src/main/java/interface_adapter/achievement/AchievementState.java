package interface_adapter.achievement;

import java.util.ArrayList;
import java.util.List;

/**
 * The State information representing the logged-in user.
 */
public class AchievementState {
    private List<String> unlockedAchievements = new ArrayList<>();
    private boolean shouldShowAchievements = false;

    /*
    getters
     */
    public List<String> getUnlockedAchievements() {
        return unlockedAchievements;
    }

    public boolean getShouldShowAchievements() {
        return shouldShowAchievements;
    }

    /*
    setters
     */
    public void setUnlockedAchievements(List<String> unlockedAchievements) {
        this.unlockedAchievements = unlockedAchievements;
    }


    public void setShouldShowAchievements(boolean shouldShowAchievements) {
        this.shouldShowAchievements = shouldShowAchievements;
    }
}
