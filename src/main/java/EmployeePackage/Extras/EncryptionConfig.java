package EmployeePackage.Extras;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EncryptionConfig {
    @Value("${app.encryption.secret-key}")
    private String secretKey;

    public String getSecretKey() {
        return secretKey;
    }
}
