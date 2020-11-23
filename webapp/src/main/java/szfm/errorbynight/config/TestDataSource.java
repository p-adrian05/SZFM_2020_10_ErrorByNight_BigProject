package szfm.errorbynight.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.nio.file.Paths;


@Configuration
@Profile("test")
public class TestDataSource {

    @Bean
    public DataSource createDataSource() {

        String path2 = Paths.get("init.sql").toString();
        String path1 = Paths.get("create.sql").toString();
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:test;INIT=runscript from '"+path1+"'\\;runscript from '"+path2+"'");
        dataSource.setUsername("sa");
        dataSource.setPassword("12");
        return dataSource;
    }

}



