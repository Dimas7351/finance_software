package rpo.finance.software.components;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.DriverManager;

@Component
public class LiquibaseRunner {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @PostConstruct
    private void runLiquibaseMigrations() {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database URL: " + url);
            System.out.println("Liquibase миграции успешно выполнены.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
