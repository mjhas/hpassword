package eu.haslgruebler.hpassword.api;

public class PasswordResetRequest {
    private String email;
    
    public PasswordResetRequest() {
        super();
    }
    
    public PasswordResetRequest(String email) {
        this();
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
