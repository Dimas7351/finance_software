package rpo.finance.software;

import jakarta.annotation.PostConstruct;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;

@SpringBootApplication
public class FinanceApplication {
	public static void main(String[] args) {
		// Запуск Spring Boot приложения
		SpringApplication.run(FinanceApplication.class, args);
	}
}
