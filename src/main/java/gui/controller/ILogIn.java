package gui.controller;

/**
 * @author Kuba
 * @date 3/24/2021 12:52 PM
 */
public interface ILogIn {
    boolean LogIn(String email, String password);
    void saveUserInPreferences();
    void unsaveUserInPreferences();
}
