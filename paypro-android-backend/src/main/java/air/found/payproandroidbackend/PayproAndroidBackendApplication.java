package air.found.payproandroidbackend;

import air.found.payproandroidbackend.config.SecretsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication()
@EnableConfigurationProperties(SecretsProperties.class)
public class PayproAndroidBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PayproAndroidBackendApplication.class, args);
	}
}
