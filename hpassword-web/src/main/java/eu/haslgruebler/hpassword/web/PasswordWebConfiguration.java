package eu.haslgruebler.hpassword.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import eu.haslgruebler.core.ui.api.MenuItem;
import eu.haslgruebler.hpassword.api.facade.PasswordFacade;
import eu.haslgruebler.hpassword.web.controller.PasswordController;

/**
 * Spring Configuration for Web Module
 * 
 * @author Michael Haslgrübler
 * 
 */
@EnableWebMvc
@Configuration
public class PasswordWebConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    PasswordFacade passwordFacade;
    
    /**
     * @return {@link PasswordController}
     */
    @Bean
    public PasswordController passwordController() {
        return new PasswordController(passwordFacade);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/password/**").addResourceLocations("classpath:/password/");
    }

    /**
     * 
     * @return {@link MenuItem} for password
     */
    @Bean
    MenuItem passwordMenu() {
        MenuItem password = new MenuItem("menu.password", "password/");
        password.addSubMenuItem(new MenuItem("menu.password.change", "password/#/change"));
        password.addSubMenuItem(new MenuItem("menu.password.resetlink", "password/#/resetlink"));
        password.addSubMenuItem(new MenuItem("menu.password.reset", "password/#/reset"));
        return password;
    }

    /**
     * register a MessageSource for internationalisation
     * 
     * @return {@link MessageSource} for i18n
     */
    @Bean
    public MessageSource messageSource() {
        final ReloadableResourceBundleMessageSource ret = new ReloadableResourceBundleMessageSource();
        ret.setBasename("classpath:message");
        ret.setDefaultEncoding("UTF-8");
        return ret;
    }

}
