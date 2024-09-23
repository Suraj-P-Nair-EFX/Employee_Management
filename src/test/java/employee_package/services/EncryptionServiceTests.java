package employee_package.services;

import employee_package.extras.CustomException;
import employee_package.extras.EncryptionConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class EncryptionServiceTests {

    private EncryptionService encryptionService;

    @BeforeEach
    void setUp() {
        EncryptionConfig encryptionConfig = Mockito.mock(EncryptionConfig.class);
        Mockito.when(encryptionConfig.getSecretKey()).thenReturn("thisissecretkeyfortestingonly123"); // Ensure this matches the key length for AES
        encryptionService = new EncryptionService(encryptionConfig);
    }

    @Test
    void encryptAndDecrypt_ShouldReturnOriginalText() {
        String originalText = "Hello, World!";
        String encryptedText = encryptionService.encrypt(originalText);
        String decryptedText = encryptionService.decrypt(encryptedText);

        assertEquals(originalText, decryptedText);
    }

    @Test
    void decrypt_InvalidCiphertext_ShouldThrowCustomException() {
        String invalidCiphertext = "invalidCiphertext";

        assertThrows(CustomException.class, () -> encryptionService.decrypt(invalidCiphertext));
    }

    @Test
    void encrypt_NullInput_ShouldThrowException() {
        assertThrows(CustomException.class, () -> encryptionService.encrypt(null));
    }

    @Test
    void decrypt_NullInput_ShouldThrowException() {
        assertThrows(CustomException.class, () -> encryptionService.decrypt(null));
    }
}
