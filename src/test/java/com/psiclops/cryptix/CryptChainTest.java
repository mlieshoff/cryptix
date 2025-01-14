package com.psiclops.cryptix;

import com.psiclops.cryptix.aes.AesCryptProcessorWithFixedKey;
import com.psiclops.cryptix.des.DesCryptProcessorWithFixedKey;
import com.psiclops.cryptix.rsa.KeyPairUtil;
import com.psiclops.cryptix.rsa.RsaCryptProcessorWithFixedKey;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.KeyPair;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

@ExtendWith(MockitoExtension.class)
class CryptChainTest {

    private static final String TEXT = "Hallo Welt!";
    private static final String KEY = "supersecret";

    private static final byte[] KEY_BYTES = KEY.getBytes(UTF_8);
    private static final byte[] TEXT_BYTES = TEXT.getBytes(UTF_8);

    private CryptChain unitUnderTest;

    @Test
    void chain() throws Exception {
        KeyPair keyPair = KeyPairUtil.createKeyPair();

        unitUnderTest = CryptChain.chain(List.of(new DesCryptProcessorWithFixedKey(KEY_BYTES), new DesCryptProcessorWithFixedKey(KEY_BYTES), new DesCryptProcessorWithFixedKey(KEY_BYTES), new AesCryptProcessorWithFixedKey(KEY_BYTES), new RsaCryptProcessorWithFixedKey(keyPair)));

        byte[] encrypted = unitUnderTest.encrypt(TEXT_BYTES);

        byte[] actual = unitUnderTest.decrypt(encrypted);

        assertArrayEquals(TEXT_BYTES, actual);
    }

}