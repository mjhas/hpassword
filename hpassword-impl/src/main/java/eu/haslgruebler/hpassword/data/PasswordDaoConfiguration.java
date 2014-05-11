package eu.haslgruebler.hpassword.data;

import java.sql.Driver;

import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import eu.haslgruebler.hpassword.api.facade.PasswordFacade;
import eu.haslgruebler.hpassword.data.facade.EmailService;
import eu.haslgruebler.hpassword.data.facade.PasswordFacadeImpl;
import eu.haslgruebler.hpassword.data.facade.PasswordRepository;

@Configuration
public class PasswordDaoConfiguration {
    @Autowired
    private MessageSource messageSource;
    @Value("password${mail.host}")
    private String mailHost;
    @Value("password${mail.from}")
    private String mailFrom;
    @Value("password${mail.smtp.localhost}")
    private String smtpLocalhost;
    @Value("password${password.query.get}")
    private String passwordGetQuery;
    @Value("password${password.query.update}")
    private String passwordUpdateQuery;
    @Value("password${jdbc.driver}")
    private String jdbcDriver;
    @Value("password${jdbc.user}")
    private String jdbcUser;
    @Value("password${jdbc.password}")
    private String jdbcPassword;
    @Value("password${jdbc.url}")
    private String jdbcUrl;
    @Value("password${crypt.salt}")
    private String cryptSalt;

    @Bean
    public PasswordFacade passwordFacade() throws BeanInstantiationException, ClassNotFoundException {
        return new PasswordFacadeImpl(cryptSalt, passwordRepository(), emailService());
    }

    @Bean
    public PasswordRepository passwordRepository() throws BeanInstantiationException, ClassNotFoundException {
        return new PasswordRepository(dataSourceTransactionManager().getDataSource(), passwordGetQuery, passwordUpdateQuery);
    }

    @Bean
    DataSourceTransactionManager dataSourceTransactionManager() throws BeanInstantiationException, ClassNotFoundException {
        return new DataSourceTransactionManager(simpleDriverDataSource());
    }

    @Bean
    public SimpleDriverDataSource simpleDriverDataSource() throws BeanInstantiationException, ClassNotFoundException {
        return new SimpleDriverDataSource((Driver) BeanUtils.instantiateClass(Class.forName(jdbcDriver)), jdbcUrl, jdbcUser, jdbcPassword);
    }

    @Bean
    public EmailService emailService() {
        return new EmailService(mailHost, smtpLocalhost, mailFrom, messageSource);
    }

    @Bean
    public static PropertyPlaceholderConfigurer passwordPlaceholderConfigurer() {
        PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
        ppc.setPlaceholderPrefix("password${");
        Resource[] resources;
        ClassPathResource cpr = new ClassPathResource("password.properties");
        if (System.getProperty("password.propertiesFile") == null) {
            resources = new Resource[] { cpr };
        } else {
            resources = new Resource[] { cpr, new FileSystemResource(System.getProperty("password.propertiesFile")) };
        }
        ppc.setLocations(resources);
        ppc.setIgnoreUnresolvablePlaceholders(true);
        return ppc;
    }
}
