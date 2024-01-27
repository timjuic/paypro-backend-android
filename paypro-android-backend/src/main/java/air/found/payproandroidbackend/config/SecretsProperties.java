package air.found.payproandroidbackend.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Component
@ConfigurationProperties(prefix = "secrets")
public class SecretsProperties {
    private String file;

    public void setFile(String file) {
        this.file = file;
    }
}