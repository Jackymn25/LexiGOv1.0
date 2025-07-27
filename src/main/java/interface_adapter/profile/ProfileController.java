package interface_adapter.profile;

import use_case.profile.ProfileInputBoundary;
import use_case.profile.ProfileInputData;

public class ProfileController {
    private final ProfileInputBoundary interactor;
    public ProfileController(ProfileInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String username) {
        final ProfileInputData inputData = new ProfileInputData(username);

        interactor.execute(inputData);
    }
}
