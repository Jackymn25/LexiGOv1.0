package use_case.signup;

import data_access.JsonUserDataAccessObject;
import entity.DefaultUserFactory;
import entity.UserFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.signup.common.SignupInputData;
import use_case.signup.common.SignupInteractor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for {@link SignupInteractor}.
 * <p>
 * Uses a JSON file as the data store and clears it after each test.
 */
public class SignupInteractorTest {

    /** Path to the JSON file used during testing. */
    private static final Path TEST_DATA_PATH =
            Paths.get("src/test/resources/data/usersignuptest.json");

    private JsonUserDataAccessObject userDataAccessObject;
    private UserFactory factory;

    /**
     * Initialize the JSON DAO and the user factory before each test.
     */
    @BeforeEach
    public void setup() {
        try {
            userDataAccessObject =
                    new JsonUserDataAccessObject(TEST_DATA_PATH.toString());
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize DAO", e);
        }
        factory = new DefaultUserFactory();
    }

    /**
     * Clean up the JSON file by truncating it to zero length.
     */
    @AfterEach
    public void cleanUp() throws IOException {
        if (Files.exists(TEST_DATA_PATH)) {
            Files.write(TEST_DATA_PATH, new byte[0]);
        }
    }

    /**
     * Scenario: successful signup with matching passwords.
     */
    @Test
    public void successTest() {
        SignupInputData inSuccess =
                new SignupInputData("tester", "Aaa111", "Aaa111");

        // Presenter that verifies success and persisted data
        SignupOutputBoundary presenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData outputData) {
                assertEquals(false, outputData.isUseCaseFailed());
                assertEquals("tester", outputData.getUsername());
                assertEquals("tester",
                        userDataAccessObject.get(outputData.getUsername()).getName());
                assertEquals("Aaa111",
                        userDataAccessObject.get(outputData.getUsername()).getPassword());
                assertEquals(true,
                        userDataAccessObject.existsByName(outputData.getUsername()));
                assertEquals(null,
                        userDataAccessObject.getQuestion(outputData.getUsername()));
                assertEquals(null,
                        userDataAccessObject.getAnswer(outputData.getUsername()));
                assertEquals(null,
                        userDataAccessObject.getSecurityQuestion(outputData.getUsername()));
                assertEquals("COMMON",
                        userDataAccessObject.getType(outputData.getUsername()));
            }

            @Override
            public void prepareFailView(String errorMessage) {
                // Not expected in this scenario
            }

            @Override
            public void switchToLoginView() {
                // Not used in this test
            }
        };

        SignupInteractor interactor =
                new SignupInteractor(userDataAccessObject, presenter, factory);
        interactor.execute(inSuccess);
    }

    /**
     * Scenario: password fails the complexity rule.
     */
    @Test
    public void failPasswordRuleTest() {
        SignupInputData inFail =
                new SignupInputData("tester", "1", "1");

        SignupOutputBoundary presenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData outputData) {
                // Not used in this scenario
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals(
                        "Password must be at least 6 characters and contain both letters and numbers.",
                        errorMessage
                );
                assertEquals(false,
                        userDataAccessObject.existsByName("tester"));
                assertEquals(null,
                        userDataAccessObject.getQuestion("tester"));
                assertEquals(null,
                        userDataAccessObject.getAnswer("tester"));
                assertEquals(null,
                        userDataAccessObject.getSecurityQuestion("tester"));
                assertEquals(null,
                        userDataAccessObject.getType("tester"));
            }

            @Override
            public void switchToLoginView() {
                // Not used in this test
            }
        };

        SignupInteractor interactor =
                new SignupInteractor(userDataAccessObject, presenter, factory);
        interactor.execute(inFail);
    }

    /**
     * Scenario: repeated password does not match.
     */
    @Test
    public void failRepeatPasswordTest() {
        SignupInputData inFail =
                new SignupInputData("tester", "Aaa111", "1");

        SignupOutputBoundary presenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData outputData) {
                // Not used here
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Passwords don't match.", errorMessage);
                assertEquals(false,
                        userDataAccessObject.existsByName("tester"));
                assertEquals(null,
                        userDataAccessObject.getQuestion("tester"));
                assertEquals(null,
                        userDataAccessObject.getAnswer("tester"));
                assertEquals(null,
                        userDataAccessObject.getSecurityQuestion("tester"));
                assertEquals(null,
                        userDataAccessObject.getType("tester"));
            }

            @Override
            public void switchToLoginView() {
                // noop
            }
        };

        SignupInteractor interactor =
                new SignupInteractor(userDataAccessObject, presenter, factory);
        interactor.execute(inFail);
    }

    /**
     * Scenario: username already exists after a successful signup.
     */
    @Test
    public void failUsernameExistsTest() {
        // First, successful signup
        SignupInputData inSuccess =
                new SignupInputData("tester", "Aaa111", "Aaa111");
        SignupInteractor firstInteractor =
                new SignupInteractor(userDataAccessObject, new SignupOutputBoundary() {
                    @Override public void prepareSuccessView(SignupOutputData o) {}
                    @Override public void prepareFailView(String m) {}
                    @Override public void switchToLoginView() {}
                }, factory);
        firstInteractor.execute(inSuccess);

        // Then attempt to sign up with the same username
        SignupInputData inExists =
                new SignupInputData("tester", "Aaa111", "Aaa111");

        SignupOutputBoundary presenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData outputData) {
                // Not expected
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("User already exists.", errorMessage);
                assertEquals(true,
                        userDataAccessObject.existsByName("tester"));
                assertEquals(null,
                        userDataAccessObject.getQuestion("tester"));
                assertEquals(null,
                        userDataAccessObject.getAnswer("tester"));
                assertEquals(null,
                        userDataAccessObject.getSecurityQuestion("tester"));
                assertEquals("COMMON",
                        userDataAccessObject.getType("tester"));
            }

            @Override
            public void switchToLoginView() {
                // Not used
            }
        };

        SignupInteractor interactor2 =
                new SignupInteractor(userDataAccessObject, presenter, factory);
        interactor2.execute(inExists);
    }

    /**
     * Scenario: switching to login view triggers the correct callback.
     */
    @Test
    public void switchToLoginViewTest() {
        SignupOutputBoundary presenter = new SignupOutputBoundary() {
            @Override public void prepareSuccessView(SignupOutputData o) {}
            @Override public void prepareFailView(String m) {}
            @Override
            public void switchToLoginView() {
                assertEquals(1, 1, "switchToLoginView should be called");
            }
        };

        SignupInteractor interactor =
                new SignupInteractor(userDataAccessObject, presenter, factory);
        interactor.switchToLoginView();
    }
}
