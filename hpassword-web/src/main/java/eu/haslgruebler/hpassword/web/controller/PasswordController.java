package eu.haslgruebler.hpassword.web.controller;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.google.code.kaptcha.servlet.KaptchaExtend;

import eu.haslgruebler.core.ui.api.CSSAsset;
import eu.haslgruebler.core.ui.api.CorePageController;
import eu.haslgruebler.core.ui.api.JavascriptAsset;
import eu.haslgruebler.hpassword.api.PasswordChangeRequest;
import eu.haslgruebler.hpassword.api.PasswordResetRequest;
import eu.haslgruebler.hpassword.api.PasswordTokenReset;
import eu.haslgruebler.hpassword.api.exceptions.ErrorSendingEmail;
import eu.haslgruebler.hpassword.api.exceptions.PasswordDontMatchException;
import eu.haslgruebler.hpassword.api.exceptions.TokenNotFoundException;
import eu.haslgruebler.hpassword.api.exceptions.UserNotFoundException;
import eu.haslgruebler.hpassword.api.facade.PasswordFacade;

/**
 * 
 * @author Michael Haslgrübler
 * 
 */
@Controller
@RequestMapping("/password")
public class PasswordController extends CorePageController {

    private PasswordFacade passwordFacade;

    private KaptchaExtend kaptchaExtend;

    @RequestMapping(value = "/captcha.jpg", method = RequestMethod.GET)
    public void captcha(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        kaptchaExtend.captcha(req, resp);
    }

    /**
     * 
     */
    private PasswordController() {
        super("/password/password.jsp");
        kaptchaExtend = new KaptchaExtend();
        addCssAsset(new CSSAsset("password", "/password/password.css"));
        JavascriptAsset jsAsset = new JavascriptAsset("password", "/password/password.js").addDependency("angular").addDependency("ng-grid").addDependency("jquery").addDependency("bootstrap-modal");
        jsAsset.setAngularModuleName("passwordApp");
        addJavascriptAsset(jsAsset);
    }

    /**
     * 
     * @param passwordFacade .
     */
    public PasswordController(PasswordFacade passwordFacade) {
        this();
        this.passwordFacade = passwordFacade;
    }

    /**
     * register change template page
     * 
     * @return the viewName
     */
    @RequestMapping(value = "/api/change.html", method = RequestMethod.GET)
    public String change() {
        return "password/template/change";
    }

    /**
     * register dialog template page
     * 
     * @return the viewName
     */
    @RequestMapping(value = "/api/dialog.html", method = RequestMethod.GET)
    public String dialog() {
        return "password/template/dialog";
    }

    /**
     * register reset link template page
     * 
     * @return the viewName
     */
    @RequestMapping(value = "/api/resetlink.html", method = RequestMethod.GET)
    public String resetlink() {
        return "password/template/resetlink";
    }
    
    /**
     * register reset link template page
     * 
     * @return the viewName
     */
    @RequestMapping(value = "/api/reset.html", method = RequestMethod.GET)
    public String reset() {
        return "password/template/reset";
    }

    private String getCaptcha() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession();
        return String.valueOf(session.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY));
    }

    private boolean checkCaptcha(String captcha) {
        System.out.println(getCaptcha());
        System.out.println(captcha);
        return true;
    }

    private Locale getLocale() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return RequestContextUtils.getLocale(attr.getRequest());
    }

    /**
     * save a password
     * 
     * @param password to save
     * @return OK or Exception
     */
    @RequestMapping(value = "/api/resetrequest", method = RequestMethod.POST)
    @ResponseBody
    public String resetrequest(@RequestBody PasswordResetRequest resetRequest, @RequestParam(value = "captcha") String captcha) {
        if (captcha.equals(getCaptcha())) {
            try {
                passwordFacade.passwordReset(resetRequest, getLocale());
            } catch (UserNotFoundException e) {
                e.printStackTrace();
                throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (ErrorSendingEmail e) {
                e.printStackTrace();
                throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return "0";
        } else {
            return "1";
        }
    }

    /**
     * save a password
     * 
     * @param password to save
     * @return OK or Exception
     */
    @RequestMapping(value = "/api/changepassword", method = RequestMethod.POST)
    @ResponseBody
    public String changePassword(@RequestBody PasswordChangeRequest passwordChangeRequest, @RequestParam(value = "captcha") String captcha) {
        if (captcha.equals(getCaptcha())) {
            try {
                passwordFacade.changePassword(passwordChangeRequest);
            } catch (UserNotFoundException e) {
                e.printStackTrace();
                throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (PasswordDontMatchException e) {
                e.printStackTrace();
                throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (Exception e) {
            }
            return "0";
        } else {
            return "1";
        }
    }

    /**
     * save a password
     * 
     * @param password to save
     * @return OK or Exception
     */
    @RequestMapping(value = "/api/tokenreset", method = RequestMethod.POST)
    @ResponseBody
    public String tokenreset(@RequestBody PasswordTokenReset passwordChangeRequest, @RequestParam(value = "captcha") String captcha) {
        if (captcha.equals(getCaptcha())) {
            try {
                passwordFacade.changePassword(passwordChangeRequest);
            } catch (UserNotFoundException e) {
                e.printStackTrace();
                throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (TokenNotFoundException e) {
                e.printStackTrace();
                throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (Exception e) {
            }
            return "0";
        } else {
            return "1";
        }
    }
}
