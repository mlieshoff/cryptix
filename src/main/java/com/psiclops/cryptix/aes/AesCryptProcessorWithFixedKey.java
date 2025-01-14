package com.psiclops.cryptix.aes;

import com.psiclops.cryptix.CryptProcessorWithFixedKey;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.NoSuchPaddingException;

@RequiredArgsConstructor
public class AesCryptProcessorWithFixedKey implements CryptProcessorWithFixedKey<byte[], byte[]> {

    private final byte[] key;

    private final AesCryptProcessor aesCryptProcessor = new AesCryptProcessor();

    @Override
    public byte[] encrypt(byte[] bytes)
            throws InvalidAlgorithmParameterException,
                    NoSuchPaddingException,
                    IOException,
                    NoSuchAlgorithmException,
                    InvalidKeySpecException,
                    InvalidKeyException {
        return aesCryptProcessor.encrypt(key, bytes);
    }

    @Override
    public void encrypt(InputStream decrypted, OutputStream encrypted)
            throws IOException,
                    InvalidAlgorithmParameterException,
                    InvalidKeyException,
                    NoSuchAlgorithmException,
                    InvalidKeySpecException,
                    NoSuchPaddingException {
        aesCryptProcessor.encrypt(key, decrypted, encrypted);
    }

    @Override
    public byte[] decrypt(byte[] bytes)
            throws InvalidAlgorithmParameterException,
                    NoSuchPaddingException,
                    IOException,
                    NoSuchAlgorithmException,
                    InvalidKeySpecException,
                    InvalidKeyException {
        return aesCryptProcessor.decrypt(key, bytes);
    }

    @Override
    public void decrypt(InputStream encrypted, OutputStream decrypted)
            throws IOException,
                    NoSuchAlgorithmException,
                    InvalidKeySpecException,
                    NoSuchPaddingException,
                    InvalidAlgorithmParameterException,
                    InvalidKeyException {
        aesCryptProcessor.decrypt(key, encrypted, decrypted);
    }
}
