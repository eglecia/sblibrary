package io.github.eglecia.sblibrary.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DatasourceConfiguration {
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.driver-class-name}")
    private String driver;

    /**
     * Devolve conexão com o banco de dados.
     */
    //@Bean : Descomente esta linha para usar conexão simples sem pool
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setDriverClassName(driver);
        return ds;
    }

    /**
     * Devolve conexão com o banco de dados utilizando um pool de conexões.
     */
    @Bean
    public DataSource dataSourcePool() {
        HikariConfig hc = new HikariConfig();
        hc.setJdbcUrl(url);
        hc.setUsername(username);
        hc.setPassword(password);
        hc.setDriverClassName(driver);
        // Configura poll de conexões
        hc.setPoolName("library-db-pool");
        hc.setMaximumPoolSize(10);
        hc.setMinimumIdle(5);
        hc.setMaxLifetime(1800000); // 30 minutos
        hc.setConnectionTimeout(30000); // 30 segundos
        hc.setIdleTimeout(60000); // 1 minuto
        hc.setConnectionTestQuery("select 1");

        return new HikariDataSource(hc);
    }
}
