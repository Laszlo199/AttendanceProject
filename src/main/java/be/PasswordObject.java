package be;

/**
 * @author Kuba
 * @date 4/7/2021 2:36 PM
 */
public class PasswordObject {
    private String hashedPassword;
    private String salt;

    public PasswordObject(String hashedPassword, String salt) {
        this.hashedPassword = hashedPassword;
        this.salt = salt;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public String getSalt() {
        return salt;
    }
}
