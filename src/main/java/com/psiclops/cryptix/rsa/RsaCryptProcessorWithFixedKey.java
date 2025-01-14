package com.psiclops.cryptix.rsa;

import com.psiclops.cryptix.CryptProcessorWithFixedKey;
import lombok.RequiredArgsConstructor;

import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RequiredArgsConstructor
public class RsaCryptProcessorWithFixedKey implements CryptProcessorWithFixedKey<byte[], byte[]> {

    private final KeyPair keyPair;

    private final RsaCryptProcessor rsaCryptProcessor = new RsaCryptProcessor();

    @Override
    public byte[] encrypt(byte[] bytes) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IOException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
        return rsaCryptProcessor.encrypt(keyPair, bytes);
    }

    @Override
    public void encrypt(InputStream decrypted, OutputStream encrypted) throws IOException, InvalidAlgorithmParameterException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException {
        rsaCryptProcessor.encrypt(keyPair, decrypted, encrypted);
    }

    @Override
    public byte[] decrypt(byte[] bytes) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IOException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
        return rsaCryptProcessor.decrypt(keyPair, bytes);
    }

    @Override
    public void decrypt(InputStream encrypted, OutputStream decrypted) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
        rsaCryptProcessor.decrypt(keyPair, encrypted, decrypted);
    }
}
