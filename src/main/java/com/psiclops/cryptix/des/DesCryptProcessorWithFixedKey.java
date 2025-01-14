package com.psiclops.cryptix.des;

import com.psiclops.cryptix.CryptProcessorWithFixedKey;
import lombok.RequiredArgsConstructor;

import javax.crypto.*;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RequiredArgsConstructor
public class DesCryptProcessorWithFixedKey implements CryptProcessorWithFixedKey<byte[], byte[]> {

    private final byte[] key;

    private final DesCryptProcessor desCryptProcessor = new DesCryptProcessor();

    @Override
    public byte[] encrypt(byte[] bytes) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IOException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
        return desCryptProcessor.encrypt(key, bytes);
    }

    @Override
    public void encrypt(InputStream decrypted, OutputStream encrypted) throws IOException, InvalidAlgorithmParameterException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException {
        desCryptProcessor.encrypt(key, decrypted, encrypted);
    }

    @Override
    public byte[] decrypt(byte[] bytes) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IOException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
        return desCryptProcessor.decrypt(key, bytes);
    }

    @Override
    public void decrypt(InputStream encrypted, OutputStream decrypted) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
        desCryptProcessor.decrypt(key, encrypted, decrypted);
    }
}
