package com.psiclops.cryptix;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import static com.psiclops.cryptix.AbstractCryptProcessor.copy;

@Slf4j
@RequiredArgsConstructor
public class CryptChain implements CryptProcessorWithFixedKey<byte[], byte[]> {

    private final List<CryptProcessorWithFixedKey<byte[], byte[]>> encryptProcessors;
    private final List<CryptProcessorWithFixedKey<byte[], byte[]>> decryptProcessors;

    public static CryptChain chain(List<CryptProcessorWithFixedKey<byte[], byte[]>> cryptProcessors) {
        return new CryptChain(cryptProcessors, cryptProcessors.reversed());
    }

    @Override
    public byte[] encrypt(byte[] decrypted) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IOException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
        byte[] result = decrypted;
        long ms = -System.currentTimeMillis();
        for (CryptProcessorWithFixedKey<byte[], byte[]> encryptProcessor : encryptProcessors) {
            result = encryptProcessor.encrypt(result);
        }
        ms += System.currentTimeMillis();
        log.debug("Chain 'encrypt' took {} ms", ms);
        return result;
    }

    @Override
    public byte[] decrypt(byte[] encrypted) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IOException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
        byte[] result = encrypted;
        long ms = -System.currentTimeMillis();
        for (CryptProcessorWithFixedKey<byte[], byte[]> decryptProcessor : decryptProcessors) {
            result = decryptProcessor.decrypt(result);
        }
        ms += System.currentTimeMillis();
        log.debug("Chain 'decrypt' took {} ms", ms);
        return result;
    }

    @Override
    public void encrypt(InputStream decrypted, OutputStream encrypted) throws IOException, InvalidAlgorithmParameterException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException {
        InputStream interimInput = decrypted;
        if (encryptProcessors.size() == 1) {
            encryptProcessors.getFirst().encrypt(interimInput, encrypted);
        } else {
            for (CryptProcessorWithFixedKey<byte[], byte[]> encryptProcessor : encryptProcessors) {
                ByteArrayOutputStream interimOutput = new ByteArrayOutputStream();
                encryptProcessor.encrypt(interimInput, interimOutput);
                byte[] interim = interimOutput.toByteArray();
                interimInput = new ByteArrayInputStream(interim);
            }
        }
        copy(interimInput, encrypted);
    }

    @Override
    public void decrypt(InputStream encrypted, OutputStream decrypted) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
        InputStream interimInput = encrypted;
        if (encryptProcessors.size() == 1) {
            encryptProcessors.getFirst().decrypt(interimInput, decrypted);
        } else {
            for (CryptProcessorWithFixedKey<byte[], byte[]> decryptProcessor : decryptProcessors) {
                ByteArrayOutputStream interimOutput = new ByteArrayOutputStream();
                decryptProcessor.decrypt(interimInput, interimOutput);
                byte[] interim = interimOutput.toByteArray();
                interimInput = new ByteArrayInputStream(interim);
            }
        }
        copy(interimInput, decrypted);
    }

}
