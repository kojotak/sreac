package cz.kojotak.sreac.cfg;

import java.net.URISyntaxException;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Profile("local")
@Configuration
public class LocalDatabase {

	@Bean
    public DataSource dataSource() throws URISyntaxException {
		//TODO replace H2 with Heroku's PostgreSQL (need SSL)
		
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUrl("jdbc:h2:mem:myDB;MODE=PostgreSQL");
        ds.setUsername("sa");
        ds.setPassword("");
        ds.setDriverClassName("org.h2.Driver");

        return ds;
    }
}
