package eu.haslgruebler.hpassword.api.exceptions;

public class UserNotFoundException extends GenericException {

    public UserNotFoundException(String message) {
        super(ErrorCode.USER_NOT_FOUND, message);
    }

    /**
     * 
     */
    private static final long serialVersionUID = 4084086117572101702L;

}
