package rpo.finance.software;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(
				title = "Finance Software API",
				version = "1.0",
				description = "" //TODO.. --dima add here smth (@karam)--
		)
)
// (-- http://localhost:8080/swagger-ui/index.html#/ --) swagger url
@SpringBootApplication
public class FinanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinanceApplication.class, args);
	}
}
