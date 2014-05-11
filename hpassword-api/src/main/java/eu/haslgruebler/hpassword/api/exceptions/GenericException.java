package eu.haslgruebler.hpassword.api.exceptions;

public class GenericException extends Exception {
    /**
     * 
     */
    private static final long serialVersionUID = -8715312745944537029L;

    public GenericException(ErrorCode errorCode, String message) {
        super("[" + errorCode.getErrorCode() + "][" + errorCode + "]" + message);
    }
}
