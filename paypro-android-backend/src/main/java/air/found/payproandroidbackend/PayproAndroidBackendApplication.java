package air.found.payproandroidbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication()
@PropertySource("classpath:secrets.properties")
public class PayproAndroidBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PayproAndroidBackendApplication.class, args);
	}
}
