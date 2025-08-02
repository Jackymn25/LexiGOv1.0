package app;

import java.awt.CardLayout;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import data_access.*;
import data_access.JsonUserProfileDAO;
import entity.*;
import infrastructure.*;
import interface_adapter.ViewManagerModel;
import interface_adapter.achievement.AchievementController;
import interface_adapter.achievement.AchievementPresenter;
import interface_adapter.achievement.AchievementViewModel;
import interface_adapter.change_password.ChangePasswordController;
import interface_adapter.change_password.ChangePasswordPresenter;
import interface_adapter.change_password.ChangeViewModel;
import interface_adapter.change_password.MakePasswordChange.MakePasswordChangeController;
import interface_adapter.change_password.MakePasswordChange.MakePasswordChangePresenter;
import interface_adapter.profile.ProfileController;
import interface_adapter.profile.ProfilePresenter;
import interface_adapter.profile.ProfileViewModel;
import interface_adapter.profile.profile_set.ProfileSetController;
import interface_adapter.profile.profile_set.ProfileSetPresenter;
import interface_adapter.session.LoggedInViewModel;
import interface_adapter.finish.FinishController;
import interface_adapter.finish.FinishPresenter;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutPresenter;
import interface_adapter.rank.RankController;
import interface_adapter.rank.RankPresenter;
import interface_adapter.rank.RankViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.start_checkin.StartCheckInController;
import interface_adapter.start_checkin.StartCheckInPresenter;
import interface_adapter.start_checkin.StartCheckInViewModel;
import interface_adapter.studysession.StudySessionController;
import interface_adapter.studysession.StudySessionPresenter;
import interface_adapter.studysession.StudySessionViewModel;
import interface_adapter.studysession.word_detail.WordDetailController;
import interface_adapter.studysession.word_detail.WordDetailPresenter;
import interface_adapter.studysession.word_detail.WordDetailViewModel;
import interface_adapter.view_history.ViewHistoryController;
import interface_adapter.view_history.ViewHistoryPresenter;
import interface_adapter.view_history.ViewHistoryViewModel;
import use_case.achievement.AchievementInputBoundary;
import use_case.achievement.AchievementInteractor;
import use_case.achievement.AchievementOutputBoundary;
import use_case.change_password.ChangePasswordInputBoundary;
import use_case.change_password.ChangePasswordInteractor;
import use_case.change_password.ChangePasswordOutputBoundary;
import use_case.change_password.make_password_change.MakePasswordChangeInputBoundary;
import use_case.change_password.make_password_change.MakePasswordChangeInteractor;
import use_case.change_password.make_password_change.MakePasswordChangeOutputBoundary;
import use_case.finish_checkin.FinishCheckInInputBoundary;
import use_case.finish_checkin.FinishCheckInInteractor;
import use_case.finish_checkin.FinishCheckInOutputBoundary;
import use_case.finish_checkin.TimeGetter;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInteractor;
import use_case.logout.LogoutOutputBoundary;
import use_case.profile.ProfileInputBoundary;
import use_case.profile.ProfileInteractor;
import use_case.profile.ProfileOutputBoundary;
import use_case.profile.profile_set.ProfileSetInputBoundary;
import use_case.profile.profile_set.ProfileSetInteractor;
import use_case.profile.profile_set.ProfileSetOutputBoundary;
import use_case.rank.RankInputBoundary;
import use_case.rank.RankInteractor;
import use_case.rank.RankOutputBoundary;
import use_case.signup.*;
import use_case.signup.common.SignupInputBoundary;
import use_case.signup.common.SignupInteractor;
import use_case.signup.security.SignupSecurityInputBoundary;
import use_case.signup.security.SignupSecurityInteractor;
import use_case.start_checkin.StartCheckInInputBoundary;
import use_case.start_checkin.StartCheckInInteractor;
import use_case.start_checkin.StartCheckInOutputBoundary;
import use_case.studysession.StudySessionInputBoundary;
import use_case.studysession.StudySessionInteractor;
import use_case.studysession.StudySessionOutputBoundary;
import use_case.studysession.word_detail.WordDetailInputBoundary;
import use_case.studysession.word_detail.WordDetailInteractor;
import use_case.studysession.word_detail.WordDetailOutputBoundary;
import use_case.viewhistory.ViewHistoryInputBoundary;
import use_case.viewhistory.ViewHistoryInteractor;
import use_case.viewhistory.ViewHistoryOutputBoundary;
import view.*;

/**
 * The AppBuilder class is responsible for putting together the pieces of
 * our CA architecture; piece by piece.
 * <p/>
 * This is done by adding each View and then adding related Use Cases.
 */
// Checkstyle note: you can ignore the "Class Data Abstraction Coupling"
//                  and the "Class Fan-Out Complexity" issues for this lab; we encourage
//                  your team to think about ways to refactor the code to resolve these
//                  if your team decides to work with this as your starter code
//                  for your final project this term.
public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final WordDeckFactory wordDeckFactory = new CommonWordDeckFactory();
    private final LearnRecordFactory learnRecordFactory = new CommonLearnRecordFactory();
    private final ProfileFactory profileFactory = new PersonalProfileFactory();
    private final DefaultUserFactory userFactory = new DefaultUserFactory();
    private final CommonCardFactory commonCardFactory = new CommonCardFactory();


    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    // thought question: is the hard dependency below a problem?
    // private final DBUserDataAccessObject userDataAccessObject = new DBUserDataAccessObject(userFactory);
    // private final InMemoryUserDataAccessObject userDataAccessObject = new InMemoryUserDataAccessObject();
    private final JsonUserDataAccessObject userDataAccessObject = new JsonUserDataAccessObject();
    private final InMemoryDeckDataAccessObejct deckDataAccessObject = new InMemoryDeckDataAccessObejct();
    private final JsonUserRecordDataAccessObject userRecordDataAccessObject = new JsonUserRecordDataAccessObject();
    private final WordBookDataAccessObject wordBookDataAccessObject = new WordBookDataAccessObject();
    private final WordDataAccessObject wordDataAccessObject = new WordDataAccessObject();
    private final JsonUserProfileDAO userProfileDAO = new JsonUserProfileDAO();

    private SignupView signupView;
    private WordDetailView wordDetailView;
    private SignupViewModel signupViewModel;
    private LoginViewModel loginViewModel;
    private LoggedInViewModel loggedInViewModel;
    private LoggedInView loggedInView;
    private LoginView loginView;
    private StudySessionView studySessionView;
    private StartCheckInViewModel startCheckInViewModel;
    private StartCheckInView startCheckInView;
    private StudySessionViewModel studySessionViewModel;
    private WordDetailViewModel wordDetailViewModel;
    private RankView rankView;
    private RankViewModel rankViewModel;
    private AchievementView achievementView;
    private AchievementViewModel achievementViewModel;
    private ViewHistoryView viewHistoryView;
    private ViewHistoryViewModel viewHistoryViewModel;
    private ProfileView profileView;
    private ProfileViewModel profileViewModel;
    private ChangePasswordView changePasswordView;
    private ChangeViewModel changeViewModel;

    private final DeepLAPIAdapter deepLapi = new DeepLAPIAdapter();
    private final FreeDictionaryApiAdapter freeDictionaryApi = new FreeDictionaryApiAdapter();
    private final LearnWordsGenerator learnWordsGenerator = new LearnWordsGenerator();
    private final TimeGetter timegetter = new TimeGenerator();


    public AppBuilder() throws IOException {
        cardPanel.setLayout(cardLayout);
    }

    /**
     * Adds the Signup View to the application.
     * @return this builder
     */
    public AppBuilder addSignupView() {
        signupViewModel = new SignupViewModel();
        signupView = new SignupView(signupViewModel);
        cardPanel.add(signupView, signupView.getViewName());
        return this;
    }

    /**
     * Adds the Login View to the application.
     * @return this builder
     */
    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel);
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

    /**
     * Adds the LoggedIn View to the application.
     * @return this builder
     */
    public AppBuilder addLoggedInView() {
        loggedInViewModel = new LoggedInViewModel();
        loggedInView = new LoggedInView(loggedInViewModel);

        startCheckInViewModel = new StartCheckInViewModel();
        startCheckInView = new StartCheckInView(startCheckInViewModel);
        loggedInView.addStartCheckInPage(startCheckInView);

        profileViewModel = new ProfileViewModel();
        final ProfileSetOutputBoundary profileSetPresenter = new ProfileSetPresenter(profileViewModel);
        final ProfileSetInputBoundary profileSetInteractor = new ProfileSetInteractor(userProfileDAO, profileSetPresenter, profileFactory);
        final ProfileSetController profileSetController = new ProfileSetController(profileSetInteractor);

        final ProfileOutputBoundary profilePresenter = new ProfilePresenter(profileViewModel);
        final ProfileInputBoundary profileInteractor = new ProfileInteractor(profilePresenter, userProfileDAO);
        final ProfileController profileController = new ProfileController(profileInteractor);
        profileView = new ProfileView(profileViewModel, profileSetController);

        loggedInView.addProfilePage(profileView);
        loggedInView.setProfileController(profileController);

        changeViewModel = new ChangeViewModel();
        final ChangePasswordOutputBoundary changePasswordPresenter = new ChangePasswordPresenter(changeViewModel);
        final ChangePasswordInputBoundary changePasswordInteractor = new ChangePasswordInteractor(
                changePasswordPresenter, userDataAccessObject);
        final ChangePasswordController changePasswordController = new ChangePasswordController(changePasswordInteractor);
        changePasswordView = new ChangePasswordView(changeViewModel);
        final MakePasswordChangeOutputBoundary  presenter = new MakePasswordChangePresenter(changeViewModel);
        final MakePasswordChangeInputBoundary interactor = new MakePasswordChangeInteractor(presenter, userDataAccessObject, userFactory);
        final MakePasswordChangeController controller = new MakePasswordChangeController(interactor);
        changePasswordView.setController(controller);

        loggedInView.addChangePasswordPage(changePasswordView);
        loggedInView.setChangePasswordController(changePasswordController);

        viewHistoryViewModel = new ViewHistoryViewModel();
        final ViewHistoryOutputBoundary viewHistoryPresenter =
                new ViewHistoryPresenter(viewHistoryViewModel);

        final ViewHistoryInputBoundary viewHistoryInteractor =
                new ViewHistoryInteractor(userRecordDataAccessObject, viewHistoryPresenter);

        final ViewHistoryController viewHistoryController =
                new ViewHistoryController(viewHistoryInteractor);
        viewHistoryView = new ViewHistoryView(viewHistoryViewModel);

        loggedInView.addViewHistoryPage(viewHistoryView);
        loggedInView.setViewHistoryController(viewHistoryController);
        viewHistoryView = new ViewHistoryView(viewHistoryViewModel);

        loggedInView.addViewHistoryPage(viewHistoryView);
        loggedInView.setViewHistoryController(viewHistoryController);

        rankViewModel = new RankViewModel();
        final RankOutputBoundary rankPresenter = new RankPresenter(rankViewModel);
        final RankInputBoundary rankInteractor = new RankInteractor(userRecordDataAccessObject, rankPresenter, new DefaultLeaderboardSelector(), new DefaultScoreSort(),10);
        final RankController rankController = new RankController(rankInteractor);
        rankView = new RankView(rankViewModel, rankController);

        loggedInView.addRankPage(rankView);
        loggedInView.setRankController(rankController);

        achievementViewModel = new AchievementViewModel();
        final AchievementOutputBoundary achievementPresenter = new AchievementPresenter(achievementViewModel);
        final AchievementInputBoundary achievementInteractor = new AchievementInteractor(achievementPresenter, userRecordDataAccessObject);
        final AchievementController achievementController = new AchievementController(achievementInteractor);
        achievementView = new AchievementView(achievementViewModel);


        loggedInView.addAchievementPage(achievementView);
        loggedInView.setAchievementController(achievementController);

        cardPanel.add(loggedInView, loggedInView.getViewName());
        return this;
    }

    public AppBuilder addStudySessionView() {
        studySessionViewModel = new StudySessionViewModel();
        studySessionView = new StudySessionView(studySessionViewModel);

        cardPanel.add(studySessionView, studySessionView.getViewName());
        return this;
    }

    public AppBuilder addWordDetailView() {
        wordDetailViewModel = new WordDetailViewModel();
        wordDetailView = new WordDetailView(wordDetailViewModel);

        cardPanel.add(wordDetailView, wordDetailView.getViewName());
        return this;
    }

    public AppBuilder addFinishCheckInUseCase() {
        final FinishCheckInOutputBoundary presenter = new FinishPresenter(loggedInViewModel,
                viewManagerModel, studySessionViewModel);
        final FinishCheckInInputBoundary interactor = new FinishCheckInInteractor(presenter,
                userRecordDataAccessObject, deckDataAccessObject, learnRecordFactory, timegetter);
        final FinishController controller = new FinishController(interactor);
        studySessionView.setFinishCheckInController(controller);
        return this;
    }

    public AppBuilder addStudySessionUseCase() {
        final StudySessionOutputBoundary presenter = new StudySessionPresenter(viewManagerModel,
                studySessionViewModel,
                wordDetailViewModel);
        final StudySessionInputBoundary interactor = new StudySessionInteractor(presenter, deckDataAccessObject);
        final StudySessionController controller = new StudySessionController(interactor);
        studySessionView.setStudySessionController(controller);
        return this;
    }

    public AppBuilder addStartCheckInUseCase() {
        final StartCheckInOutputBoundary presenter = new StartCheckInPresenter(viewManagerModel,
                startCheckInViewModel, studySessionViewModel, loggedInViewModel);
        final StartCheckInInputBoundary interactor
                = new StartCheckInInteractor(userRecordDataAccessObject,
                wordBookDataAccessObject, deckDataAccessObject, wordDataAccessObject, userProfileDAO,
                learnWordsGenerator, presenter, deepLapi, freeDictionaryApi, wordDeckFactory, commonCardFactory);
        final StartCheckInController controller = new StartCheckInController(interactor, presenter);
        startCheckInView.setController(controller);
        return this;
    }


    /**
     * Adds the Signup Use Case to the application.
     * @return this builder
     */
    public AppBuilder addSignupUseCase() {
        final SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel,
                signupViewModel, loginViewModel);
        final SignupInputBoundary userSignupInteractor = new SignupInteractor(
                userDataAccessObject, signupOutputBoundary, userFactory);
        final SignupSecurityInputBoundary userSecuritySignupInteractor = new SignupSecurityInteractor(
                userDataAccessObject, signupOutputBoundary, userFactory);

        final SignupController controller = new SignupController(userSignupInteractor, userSecuritySignupInteractor);
        signupView.setSignupController(controller);
        return this;
    }

    /**
     * Adds the Login Use Case to the application.
     * @return this builder
     */
    public AppBuilder addLoginUseCase() {
        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel,
                loggedInViewModel, loginViewModel, signupViewModel, startCheckInViewModel);
        final LoginInputBoundary loginInteractor = new LoginInteractor(
                userDataAccessObject, loginOutputBoundary);

        final LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;
    }

    /**
     * Adds the Logout Use Case to the application.
     * @return this builder
     */
    public AppBuilder addLogoutUseCase() {
        final LogoutOutputBoundary logoutOutputBoundary = new LogoutPresenter(viewManagerModel,
                loggedInViewModel, loginViewModel);

        final LogoutInputBoundary logoutInteractor =
                new LogoutInteractor(userDataAccessObject, logoutOutputBoundary);

        final LogoutController logoutController = new LogoutController(logoutInteractor);
        loggedInView.setLogoutController(logoutController);
        return this;
    }

    public AppBuilder addWordDetaiUsecase() {
        final WordDetailOutputBoundary presenter = new WordDetailPresenter(
                viewManagerModel, wordDetailViewModel, studySessionViewModel);
        final WordDetailInputBoundary interactor =
                new WordDetailInteractor(presenter, deckDataAccessObject);
        final WordDetailController controller = new WordDetailController(interactor);
        wordDetailView.setWordDetailController(controller);
        studySessionView.setWordDetailController(controller);
        return this;
    }

    /**
     * Creates the JFrame for the application and initially sets the SignupView to be displayed.
     * @return the application
     */
    public JFrame build() {
        final JFrame application = new JFrame("Login Example");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        viewManagerModel.setState(signupView.getViewName());
        viewManagerModel.firePropertyChanged();

        return application;

    }
}
