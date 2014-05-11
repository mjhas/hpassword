package eu.haslgruebler.hpassword.api.exceptions;

public class PasswordInvalidException extends GenericException {

    /**
     * 
     */
    private static final long serialVersionUID = 9201102780857819612L;

    public PasswordInvalidException(String message) {
        super(ErrorCode.PASSWORD_INVALID, message);
    }

    /**
     * 
     */
}
