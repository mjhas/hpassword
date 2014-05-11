package eu.haslgruebler.hpassword.api.exceptions;

public class TokenNotFoundException extends GenericException {

    /**
     * 
     */
    private static final long serialVersionUID = -115911561554281565L;

    public TokenNotFoundException(String message) {
        super(ErrorCode.TOKEN_NOT_FOUND, message);
    }
}
