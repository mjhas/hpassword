package eu.haslgruebler.hpassword.api.exceptions;

public enum ErrorCode {

    GENERIC(1), 
    PASSWORD_DONT_MATCH(21), 
    USER_NOT_FOUND(31),
    EMAIL_NOT_SENT(41),
    TOKEN_NOT_FOUND(51), 
    PASSWORD_INVALID(61);

    private int errorCode;

    private ErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
