package users;
import java.io.Serializable;

public class User implements Serializable {

    protected String firstName;
    protected String lastName;
    protected String username;
    protected String password;

    /** 0 = Not a user
     * 1 = Admin
     * 2 = Doctor
     * 3 = Patient
     */
    private int accountType;

    /**
     * Empty default constructor
     */
    public User() {
    }

    /**
     * Constructor for creating a new user
     * @param usernames
     * @param password
     * @param accountType
     */
    public User(String username, String password, int accountType) {
        this.username = username;
        this.password = password;
        this.accountType = accountType;
    }

    /**
     * Getter method for username
     * @return username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Getter method for password
     * @return password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Getter method for account type
     * @return account type
     */
    public int getAccountType() {
        return this.accountType;
    }


}
