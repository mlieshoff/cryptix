package com.psiclops.cryptix.rsa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

class KeyPairUtilTest {

    @Test
    void createKeyPair_whenCalled_thenReturnKeyPair() throws Exception {
        KeyPair actual = KeyPairUtil.createKeyPair();

        assertNotNull(actual);
    }

    @Test
    void createPublicKey_whenFromBytes_thenRestoreKeyPair() throws Exception {
        KeyPair expected = KeyPairUtil.createKeyPair();

        PublicKey actual = KeyPairUtil.createPublicKey(expected.getPublic().getEncoded());

        assertEquals(expected.getPublic(), actual);
    }

    @Test
    void createPrivateKey_whenFromBytes_thenRestoreKeyPair() throws Exception {
        KeyPair expected = KeyPairUtil.createKeyPair();

        PrivateKey actual = KeyPairUtil.createPrivateKey(expected.getPrivate().getEncoded());

        assertEquals(expected.getPrivate(), actual);
    }
}
