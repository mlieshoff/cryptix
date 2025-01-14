package com.psiclops.cryptix.aes;

import static org.junit.jupiter.api.Assertions.*;

import static java.nio.charset.StandardCharsets.UTF_8;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AesCryptProcessorTest {

    private static final String TEXT = "Hallo Welt!";
    private static final String KEY = "supersecret";

    private static final byte[] KEY_BYTES = KEY.getBytes(UTF_8);
    private static final byte[] TEXT_BYTES = TEXT.getBytes(UTF_8);

    @InjectMocks private AesCryptProcessor unitUnderTest;

    @Test
    void encryptDecrypt() throws Exception {

        byte[] encrypted = unitUnderTest.encrypt(KEY_BYTES, TEXT.getBytes(UTF_8));

        byte[] actual = unitUnderTest.decrypt(KEY_BYTES, encrypted);

        assertArrayEquals(TEXT_BYTES, actual);
    }
}
