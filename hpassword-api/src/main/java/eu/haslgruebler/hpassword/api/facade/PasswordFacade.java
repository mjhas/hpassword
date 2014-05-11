package eu.haslgruebler.hpassword.api.facade;

import java.util.Locale;

import eu.haslgruebler.hpassword.api.PasswordChangeRequest;
import eu.haslgruebler.hpassword.api.PasswordResetRequest;
import eu.haslgruebler.hpassword.api.PasswordTokenReset;
import eu.haslgruebler.hpassword.api.exceptions.ErrorSendingEmail;
import eu.haslgruebler.hpassword.api.exceptions.PasswordDontMatchException;
import eu.haslgruebler.hpassword.api.exceptions.PasswordInvalidException;
import eu.haslgruebler.hpassword.api.exceptions.TokenNotFoundException;
import eu.haslgruebler.hpassword.api.exceptions.UserNotFoundException;

public interface PasswordFacade {

    void changePassword(PasswordChangeRequest passwordChangeRequest) throws UserNotFoundException, PasswordDontMatchException, PasswordInvalidException;

    void changePassword(PasswordTokenReset passwordTokenReset) throws UserNotFoundException, TokenNotFoundException, PasswordInvalidException;

    void passwordReset(PasswordResetRequest passwordChangeRequest, Locale locale) throws UserNotFoundException, ErrorSendingEmail;

}
