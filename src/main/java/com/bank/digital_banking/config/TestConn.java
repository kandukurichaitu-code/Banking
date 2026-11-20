package com.bank.digital_banking.config;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;

@Configuration
public class TestConn {

    @Bean
    CommandLineRunner testDatabase(DataSource dataSource) {
        return args -> {
            try (Connection connection =
                         dataSource.getConnection()) {

                System.out.println(
                        "Oracle Connection Successful!"
                );

                System.out.println(
                        "Database: " +
                                connection.getMetaData()
                                        .getDatabaseProductName()
                );

                System.out.println(
                        "Version: " +
                                connection.getMetaData()
                                        .getDatabaseProductVersion()
                );
            }
        };
    }
}
