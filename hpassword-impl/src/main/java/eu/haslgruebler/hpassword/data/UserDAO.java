package eu.haslgruebler.hpassword.data;

public class UserDAO {
    private String email;
    private String password;
    private boolean crypted;

    public UserDAO(String email, String password, boolean crypted) {
        super();
        this.email = email;
        this.password = password;
        this.crypted = crypted;
    }

    public UserDAO(String email, String password) {
        this(email, password, false);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isCrypted() {
        return crypted;
    }

    public void setCrypted(boolean crypted) {
        this.crypted = crypted;
    }

    public void setCryptedPassword(String cryptedPassword) {
        this.password = cryptedPassword;
        this.crypted = true;
    }
}
