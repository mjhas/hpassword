package eu.haslgruebler.hpassword.data.facade;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

import org.apache.commons.codec.digest.Crypt;

import eu.haslgruebler.hpassword.api.PasswordChangeRequest;
import eu.haslgruebler.hpassword.api.PasswordResetRequest;
import eu.haslgruebler.hpassword.api.PasswordTokenReset;
import eu.haslgruebler.hpassword.api.exceptions.ErrorSendingEmail;
import eu.haslgruebler.hpassword.api.exceptions.PasswordDontMatchException;
import eu.haslgruebler.hpassword.api.exceptions.PasswordInvalidException;
import eu.haslgruebler.hpassword.api.exceptions.TokenNotFoundException;
import eu.haslgruebler.hpassword.api.exceptions.UserNotFoundException;
import eu.haslgruebler.hpassword.api.facade.PasswordFacade;
import eu.haslgruebler.hpassword.data.UserDAO;

public class PasswordFacadeImpl implements PasswordFacade {

    private PasswordRepository repository;
    private EmailService emailService;
    private HashMap<String, String> tokenMap;
    private String salt;

    public PasswordFacadeImpl(String salt, PasswordRepository repository, EmailService emailService) {
        this.repository = repository;
        this.salt=salt;
        this.emailService = emailService;
        tokenMap = new HashMap<String, String>();
    }

    @Override
    public void changePassword(PasswordChangeRequest pcr) throws UserNotFoundException, PasswordDontMatchException, PasswordInvalidException {
        UserDAO user = repository.get(pcr.getEmail());
        if (user == null) {
            throw new UserNotFoundException("user " + pcr.getEmail() + " not found");
        } else {
            String cryptedOldPassword = Crypt.crypt(pcr.getOldPassword(), "salt");
            if (cryptedOldPassword.equals(user.getPassword())) {
                isValidPassword(pcr.getNewPassword());
                UserDAO newUser = new UserDAO(pcr.getEmail(), pcr.getOldPassword());
                newUser.setCryptedPassword(Crypt.crypt(pcr.getNewPassword(), salt));
                repository.save(newUser);
            } else {
                throw new PasswordDontMatchException("user " + user.getEmail() + " given password (" + cryptedOldPassword + ") doesn't match with stored password (" + user.getPassword() + ")");
            }
        }

    }

    @Override
    public void passwordReset(PasswordResetRequest prr, Locale locale) throws ErrorSendingEmail, UserNotFoundException {
        UserDAO user = repository.get(prr.getEmail());
        if (user == null) {
            throw new UserNotFoundException("user " + prr.getEmail() + " not found");
        } else {
            String token = getToken();
            tokenMap.put(prr.getEmail(), token);
            emailService.sendEmail(prr.getEmail(), token, locale);
        }
    }

    private String getToken() {
        int length = 64;
        char[] chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
        Random rand = new SecureRandom();
        StringBuffer text = new StringBuffer();
        for (int i = 0; i < length; i++) {
            text.append(chars[rand.nextInt(chars.length)]);
        }
        return text.toString();
    }

    @Override
    public void changePassword(PasswordTokenReset pcr) throws UserNotFoundException, TokenNotFoundException, PasswordInvalidException {
        UserDAO user = repository.get(pcr.getEmail());
        if (user == null) {
            throw new UserNotFoundException("user " + pcr.getEmail() + " not found");
        } else {
            if (pcr.getToken().equals(tokenMap.get(pcr.getEmail()))) {
                isValidPassword(pcr.getNewPassword());
                tokenMap.remove(pcr.getEmail());
                UserDAO newUser = new UserDAO(pcr.getEmail(), "");
                newUser.setCryptedPassword(Crypt.crypt(pcr.getNewPassword(), "salt"));
                repository.save(newUser);
            } else {
                throw new TokenNotFoundException("user " + user.getEmail() + " given token (" + pcr.getToken() + ") not found");
            }
        }
    }

    void isValidPassword(String newPassword) throws PasswordInvalidException {
        if (newPassword.length() < 8) {
            throw new PasswordInvalidException("password too short");
        } 
        if (newPassword.length() > 20) {
            throw new PasswordInvalidException("password too long");
        } 
    }
}
