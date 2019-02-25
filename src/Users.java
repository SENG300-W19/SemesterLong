import java.io.Serializable;

public class Users implements Serializable {



    private String username;

    private String password;

    /** 0 = Not a user
     * 1 = Admin
     * 2 = Doctor
     * 3 = Patient
     */
    private int accountType;

    /**
     * Empty default constructor
     */
    public Users() {
    }

    /**
     * Constructor for creating a new user
     * @param username
     * @param password
     * @param accountType
     */
    public Users(String username, String password, int accountType) {
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
