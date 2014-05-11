package eu.haslgruebler.hpassword.api.exceptions;

public class ErrorSendingEmail extends GenericException {

    /**
     * 
     */
    private static final long serialVersionUID = -3760878709240485169L;

    public ErrorSendingEmail(String message) {
        super(ErrorCode.EMAIL_NOT_SENT, message);
    }
}
