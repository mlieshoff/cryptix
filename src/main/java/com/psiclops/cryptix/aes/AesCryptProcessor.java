package com.psiclops.cryptix.aes;

import static javax.crypto.Cipher.*;

import com.psiclops.cryptix.AbstractCryptProcessor;
import com.psiclops.cryptix.CryptProcessor;

import java.io.*;
import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class AesCryptProcessor extends AbstractCryptProcessor
        implements CryptProcessor<byte[], byte[], byte[]> {

    private static final String ENCRYPT_ALGO = "AES/GCM/NoPadding";

    private static final int TAG_LENGTH_BIT = 128; // must be one of {128, 120, 112, 104, 96}
    private static final int IV_LENGTH_BYTE = 12;
    private static final int SALT_LENGTH_BYTE = 16;

    private static byte[] getRandomNonce(int numBytes) {
        byte[] nonce = new byte[numBytes];
        new SecureRandom().nextBytes(nonce);
        return nonce;
    }

    private static SecretKey getAESKeyFromPassword(byte[] password, byte[] salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(new String(password).toCharArray(), salt, 65536, 256);
        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
    }

    @Override
    public byte[] encrypt(byte[] key, byte[] decrypted)
            throws InvalidAlgorithmParameterException,
                    NoSuchPaddingException,
                    IOException,
                    NoSuchAlgorithmException,
                    InvalidKeySpecException,
                    InvalidKeyException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        encrypt(key, new ByteArrayInputStream(decrypted), outputStream);
        return outputStream.toByteArray();
    }

    @Override
    public byte[] decrypt(byte[] key, byte[] encrypted)
            throws InvalidAlgorithmParameterException,
                    NoSuchPaddingException,
                    IOException,
                    NoSuchAlgorithmException,
                    InvalidKeySpecException,
                    InvalidKeyException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        decrypt(key, new ByteArrayInputStream(encrypted), byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public void encrypt(byte[] key, InputStream decrypted, OutputStream encrypted)
            throws IOException,
                    InvalidAlgorithmParameterException,
                    InvalidKeyException,
                    NoSuchAlgorithmException,
                    InvalidKeySpecException,
                    NoSuchPaddingException {
        byte[] salt = getRandomNonce(SALT_LENGTH_BYTE);
        byte[] iv = getRandomNonce(IV_LENGTH_BYTE);
        SecretKey aesKeyFromPassword = getAESKeyFromPassword(key, salt);
        Cipher cipher = getInstance(ENCRYPT_ALGO);
        cipher.init(ENCRYPT_MODE, aesKeyFromPassword, new GCMParameterSpec(TAG_LENGTH_BIT, iv));
        OutputStream result = new BufferedOutputStream(encrypted);
        result.write(ByteBuffer.allocate(iv.length + salt.length).put(iv).put(salt).array());
        CipherOutputStream cipherOutputStream = new CipherOutputStream(result, cipher);
        copy(decrypted, cipherOutputStream);
        result.close();
    }

    @Override
    public void decrypt(byte[] key, InputStream encrypted, OutputStream decrypted)
            throws IOException,
                    NoSuchAlgorithmException,
                    InvalidKeySpecException,
                    NoSuchPaddingException,
                    InvalidAlgorithmParameterException,
                    InvalidKeyException {
        byte[] iv = encrypted.readNBytes(IV_LENGTH_BYTE);
        byte[] salt = encrypted.readNBytes(SALT_LENGTH_BYTE);
        SecretKey aesKeyFromPassword = getAESKeyFromPassword(key, salt);
        javax.crypto.Cipher cipher = getInstance(ENCRYPT_ALGO);
        cipher.init(DECRYPT_MODE, aesKeyFromPassword, new GCMParameterSpec(TAG_LENGTH_BIT, iv));
        CipherInputStream cipherInputStream = new CipherInputStream(encrypted, cipher);
        copy(cipherInputStream, decrypted);
    }
}
