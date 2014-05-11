package eu.haslgruebler.hpassword.api.exceptions;

public class PasswordDontMatchException extends GenericException {

    public PasswordDontMatchException(String message) {
        super(ErrorCode.PASSWORD_DONT_MATCH, message);
    }

    /**
     * 
     */
    private static final long serialVersionUID = 4267717197437605448L;
}
