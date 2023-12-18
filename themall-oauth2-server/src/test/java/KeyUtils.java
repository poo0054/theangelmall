import org.junit.jupiter.api.Test;
import org.springframework.util.Base64Utils;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

/**
 * @author poo0054
 */
public class KeyUtils {

    private static KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return keyPair;
    }

    @Test
    void test() {
        KeyPair keyPair = generateRsaKey();
        System.out.println("*-----------------------------------*");
        System.out.println(Base64Utils.encodeToString(keyPair.getPublic().getEncoded()));
        System.out.println("*-----------------------------------*");
        System.out.println(Base64Utils.encodeToString(keyPair.getPrivate().getEncoded()));
        System.out.println("*-----------------------------------*");
    }
}
