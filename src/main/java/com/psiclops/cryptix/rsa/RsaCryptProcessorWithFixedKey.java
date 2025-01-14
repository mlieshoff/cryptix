package com.psiclops.cryptix.rsa;

import com.psiclops.cryptix.CryptProcessorWithFixedKey;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;

@RequiredArgsConstructor
public class RsaCryptProcessorWithFixedKey implements CryptProcessorWithFixedKey<byte[], byte[]> {

    private final KeyPair keyPair;

    private final RsaCryptProcessor rsaCryptProcessor = new RsaCryptProcessor();

    @Override
    public byte[] encrypt(byte[] bytes)
            throws NoSuchPaddingException,
                    IOException,
                    NoSuchAlgorithmException,
                    InvalidKeyException {
        return rsaCryptProcessor.encrypt(keyPair, bytes);
    }

    @Override
    public void encrypt(InputStream decrypted, OutputStream encrypted)
            throws IOException,
                    InvalidKeyException,
                    NoSuchAlgorithmException,
                    NoSuchPaddingException {
        rsaCryptProcessor.encrypt(keyPair, decrypted, encrypted);
    }

    @Override
    public byte[] decrypt(byte[] bytes)
            throws NoSuchPaddingException,
                    IOException,
                    NoSuchAlgorithmException,
                    InvalidKeyException {
        return rsaCryptProcessor.decrypt(keyPair, bytes);
    }

    @Override
    public void decrypt(InputStream encrypted, OutputStream decrypted)
            throws IOException,
                    NoSuchAlgorithmException,
                    NoSuchPaddingException,
                    InvalidKeyException {
        rsaCryptProcessor.decrypt(keyPair, encrypted, decrypted);
    }
}
