package bank.develop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class BankMicroserviceApplication {
	public static void main(String[] args) {
		SpringApplication.run(BankMicroserviceApplication.class, args);
	}
}