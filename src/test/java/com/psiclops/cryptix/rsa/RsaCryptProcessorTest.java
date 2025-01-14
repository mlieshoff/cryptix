package com.psiclops.cryptix.rsa;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import static java.nio.charset.StandardCharsets.UTF_8;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.KeyPair;

@ExtendWith(MockitoExtension.class)
class RsaCryptProcessorTest {

    private static final String TEXT = "Hallo Welt!";
    private static final String KEY = "supersecret";

    private static final byte[] KEY_BYTES = KEY.getBytes(UTF_8);
    private static final byte[] TEXT_BYTES = TEXT.getBytes(UTF_8);

    @InjectMocks private RsaCryptProcessor unitUnderTest;

    @Test
    void encryptDecrypt() throws Exception {
        KeyPair keyPair = KeyPairUtil.createKeyPair();

        byte[] encrypted = unitUnderTest.encrypt(keyPair, TEXT.getBytes(UTF_8));

        byte[] actual = unitUnderTest.decrypt(keyPair, encrypted);

        assertArrayEquals(TEXT_BYTES, actual);
    }
}
