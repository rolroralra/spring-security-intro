package com.example.springsecurityinaction.security;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;

public class EncryptorsTest {
    private final String password = "password";
    private final String salt = KeyGenerators.string().generateKey();

    @ParameterizedTest
    @ValueSource(strings = {"input1", "input2"})
    void standardBytesEncryptor(String inputString) {
        BytesEncryptor standardBytesEncryptor = Encryptors.standard(password, salt);

        byte[] encrypted = standardBytesEncryptor.encrypt(inputString.getBytes());
        byte[] decrypted = standardBytesEncryptor.decrypt(encrypted);

        String decrpytedString = new String(decrypted);

        assertThat(decrpytedString).isEqualTo(inputString);
    }

    @ParameterizedTest
    @ValueSource(strings = {"input1", "input2"})
    void stongerBytesEncryptor(String inputString) {
        BytesEncryptor stongerBytesEncryptor = Encryptors.stronger(password, salt);

        byte[] encrypted = stongerBytesEncryptor.encrypt(inputString.getBytes());
        byte[] decrypted = stongerBytesEncryptor.decrypt(encrypted);

        String decrpytedString = new String(decrypted);

        assertThat(decrpytedString).isEqualTo(inputString);
    }

    @ParameterizedTest
    @ValueSource(strings = {"input1", "input2"})
    void textEncryptor(String inputString) {
        TextEncryptor textEncryptor = Encryptors.text(password, salt);

        String encrypted = textEncryptor.encrypt(inputString);
        String decrypted = textEncryptor.decrypt(encrypted);

        assertThat(decrypted).isEqualTo(inputString);
    }

    @ParameterizedTest
    @ValueSource(strings = {"input1", "input2"})
    void deluxTextEncryptor(String inputString) {
        TextEncryptor deluxTextEncryptor = Encryptors.delux(password, salt);

        String encrypted = deluxTextEncryptor.encrypt(inputString);
        String decrypted = deluxTextEncryptor.decrypt(encrypted);

        assertThat(decrypted).isEqualTo(inputString);
    }

    @ParameterizedTest
    @ValueSource(strings = {"input1", "input2"})
    @SuppressWarnings("deprecation")
    void queryableTextEncryptor(String inputString) {
        TextEncryptor queryableTextEncryptor = Encryptors.queryableText(password, salt);

        String encrypted = queryableTextEncryptor.encrypt(inputString);
        String decrypted = queryableTextEncryptor.decrypt(encrypted);

        String encrypted2 = queryableTextEncryptor.encrypt(inputString);
        String decrypted2 = queryableTextEncryptor.decrypt(encrypted2);

        assertThat(decrypted).isEqualTo(inputString);   // 주의: 입력이 같으면 암호화 출력 값이 동일하다.
        assertThat(decrypted2).isEqualTo(inputString);
        assertThat(encrypted2).isEqualTo(encrypted);
    }

}
