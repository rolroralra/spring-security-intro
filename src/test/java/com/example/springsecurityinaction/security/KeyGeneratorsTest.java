package com.example.springsecurityinaction.security;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.keygen.StringKeyGenerator;

public class KeyGeneratorsTest {

    @Test
    void stringKeyGenerator() {
        StringKeyGenerator stringKeyGenerator = KeyGenerators.string();

        String stringKey1 = stringKeyGenerator.generateKey();
        String stringKey2 = stringKeyGenerator.generateKey();

        assertThat(Hex.decode(stringKey1)).hasSize(8);
        assertThat(Hex.decode(stringKey2)).hasSize(8);
        assertThat(stringKey1).isNotEqualTo(stringKey2);
    }

    @Test
    void bytesKeyGenerator() {
        int keyLength = 8;
        BytesKeyGenerator bytesKeyGenerator = KeyGenerators.secureRandom(keyLength);

        byte[] key1 = bytesKeyGenerator.generateKey();
        byte[] key2 = bytesKeyGenerator.generateKey();

        String keyHexString1 = String.valueOf(Hex.encode(key1));
        String keyHexString2 = String.valueOf(Hex.encode(key2));


        assertThat(bytesKeyGenerator.getKeyLength()).isEqualTo(keyLength);
        assertThat(key1).hasSize(keyLength);
        assertThat(key2).hasSize(keyLength);
        assertThat(keyHexString1).isNotEqualTo(keyHexString2);
    }

    @Test
    void sharedKeyGenerator() {
        int sharedKeyLength = 16;

        BytesKeyGenerator sharedKeyGenerator = KeyGenerators.shared(sharedKeyLength);

        byte[] sharedKey1 = sharedKeyGenerator.generateKey();
        byte[] sharedKey2 = sharedKeyGenerator.generateKey();

        assertThat(sharedKeyGenerator.getKeyLength()).isEqualTo(sharedKeyLength);
        assertThat(sharedKey1).hasSize(sharedKeyLength);
        assertThat(sharedKey2).hasSize(sharedKeyLength);
        assertThat(sharedKey1).containsExactly(sharedKey2);
    }
}
