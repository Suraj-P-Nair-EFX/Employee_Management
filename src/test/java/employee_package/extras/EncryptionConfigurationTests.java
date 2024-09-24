package employee_package.extras;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource(properties = "app.encryption.secret-key=mytestsecretkey")
class EncryptionConfigurationTests {

    @Autowired
    private EncryptionConfig encryptionConfig;

    @Test
    void secretKey_ShouldBeLoadedFromProperties() {
        assertEquals("mytestsecretkey", encryptionConfig.getSecretKey());
    }
}
