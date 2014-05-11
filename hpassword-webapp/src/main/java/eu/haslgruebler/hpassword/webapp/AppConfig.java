package eu.haslgruebler.hpassword.webapp;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import eu.haslgruebler.hpassword.data.PasswordDaoConfiguration;

/**
 * Spring Configuration for Application
 * 
 * @author Michael Haslgrübler
 * 
 */
@Configuration
@Import({PasswordDaoConfiguration.class})
public class AppConfig {

}
