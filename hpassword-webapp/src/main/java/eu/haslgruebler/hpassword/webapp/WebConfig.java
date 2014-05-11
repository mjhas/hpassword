package eu.haslgruebler.hpassword.webapp;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import eu.haslgruebler.core.bootstrap.BootstrapConfiguration;
import eu.haslgruebler.core.ui.impl.CoreWebConfiguration;
import eu.haslgruebler.hpassword.web.PasswordWebConfiguration;

/**
 * Spring Configuration for Dispatcher
 * 
 * @author Michael Haslgrübler
 * 
 */
@EnableWebMvc
@Configuration
@Import(value = { BootstrapConfiguration.class, CoreWebConfiguration.class, PasswordWebConfiguration.class })
public class WebConfig extends WebMvcConfigurerAdapter {

}
