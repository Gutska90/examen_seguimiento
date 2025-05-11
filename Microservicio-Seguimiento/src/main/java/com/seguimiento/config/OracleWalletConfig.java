package com.seguimiento.config;

import oracle.jdbc.pool.OracleDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
public class OracleWalletConfig {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.hikari.data-source-properties.oracle.net.ssl_truststore}")
    private String truststore;

    @Value("${spring.datasource.hikari.data-source-properties.oracle.net.ssl_truststore_password}")
    private String truststorePassword;

    @Bean
    public DataSource dataSource() throws SQLException {
        OracleDataSource dataSource = new OracleDataSource();
        
        // Configurar la URL y credenciales
        dataSource.setURL(url);
        dataSource.setUser(username);
        dataSource.setPassword(password);

        // Configurar propiedades SSL
        Properties properties = new Properties();
        properties.setProperty("oracle.net.ssl_version", "TLSv1.2");
        properties.setProperty("oracle.net.ssl_server_dn_match", "true");
        properties.setProperty("oracle.net.ssl_truststore", truststore);
        properties.setProperty("oracle.net.ssl_truststore_type", "SSO");
        properties.setProperty("oracle.net.ssl_truststore_password", truststorePassword);
        
        dataSource.setConnectionProperties(properties);

        return dataSource;
    }
} 