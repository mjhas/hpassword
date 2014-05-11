package eu.haslgruebler.hpassword.api;

public class PasswordTokenReset {

    private String email;
    private String token;
    private String newPassword;

    public PasswordTokenReset() {
        super();
    }
    
    public PasswordTokenReset(String email, String token, String newPassword) {
        this();
        this.email = email;
        this.setToken(token);
        this.newPassword = newPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
