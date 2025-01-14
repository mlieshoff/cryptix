package com.psiclops.cryptix.des;

import com.psiclops.cryptix.CryptProcessorWithFixedKey;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.NoSuchPaddingException;

@RequiredArgsConstructor
public class DesCryptProcessorWithFixedKey implements CryptProcessorWithFixedKey<byte[], byte[]> {

    private final byte[] key;

    private final DesCryptProcessor desCryptProcessor = new DesCryptProcessor();

    @Override
    public byte[] encrypt(byte[] bytes)
            throws NoSuchPaddingException,
                    IOException,
                    NoSuchAlgorithmException,
                    InvalidKeySpecException,
                    InvalidKeyException {
        return desCryptProcessor.encrypt(key, bytes);
    }

    @Override
    public void encrypt(InputStream decrypted, OutputStream encrypted)
            throws IOException,
                    InvalidKeyException,
                    NoSuchAlgorithmException,
                    InvalidKeySpecException,
                    NoSuchPaddingException {
        desCryptProcessor.encrypt(key, decrypted, encrypted);
    }

    @Override
    public byte[] decrypt(byte[] bytes)
            throws NoSuchPaddingException,
                    IOException,
                    NoSuchAlgorithmException,
                    InvalidKeySpecException,
                    InvalidKeyException {
        return desCryptProcessor.decrypt(key, bytes);
    }

    @Override
    public void decrypt(InputStream encrypted, OutputStream decrypted)
            throws IOException,
                    NoSuchAlgorithmException,
                    InvalidKeySpecException,
                    NoSuchPaddingException,
                    InvalidKeyException {
        desCryptProcessor.decrypt(key, encrypted, decrypted);
    }
}
