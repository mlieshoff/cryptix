package com.psiclops.cryptix.des;

import static java.nio.charset.StandardCharsets.UTF_8;

import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Cipher.ENCRYPT_MODE;

import com.psiclops.cryptix.AbstractCryptProcessor;
import com.psiclops.cryptix.CryptProcessor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DesCryptProcessor extends AbstractCryptProcessor
        implements CryptProcessor<byte[], byte[], byte[]> {

    @Override
    public byte[] encrypt(byte[] key, byte[] decrypted)
            throws NoSuchPaddingException,
                    IOException,
                    NoSuchAlgorithmException,
                    InvalidKeySpecException,
                    InvalidKeyException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        encrypt(key, new ByteArrayInputStream(decrypted), byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public byte[] decrypt(byte[] key, byte[] decrypted)
            throws NoSuchPaddingException,
                    IOException,
                    NoSuchAlgorithmException,
                    InvalidKeySpecException,
                    InvalidKeyException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        decrypt(key, new ByteArrayInputStream(decrypted), byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public void encrypt(byte[] key, InputStream decrypted, OutputStream encrypted)
            throws IOException,
                    InvalidKeyException,
                    NoSuchAlgorithmException,
                    InvalidKeySpecException,
                    NoSuchPaddingException {
        cryptOrDecrypt(key, decrypted, encrypted, ENCRYPT_MODE);
    }

    private void cryptOrDecrypt(
            byte[] key, InputStream decrypted, OutputStream encrypted, int encryptMode)
            throws InvalidKeyException,
                    NoSuchAlgorithmException,
                    InvalidKeySpecException,
                    NoSuchPaddingException,
                    IOException {
        byte[] keyBytes = new String(key).getBytes(UTF_8);
        DESKeySpec dks = new DESKeySpec(keyBytes);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
        SecretKey desKey = skf.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(encryptMode, desKey);
        copy(decrypted, new CipherOutputStream(encrypted, cipher));
    }

    @Override
    public void decrypt(byte[] key, InputStream encrypted, OutputStream decrypted)
            throws IOException,
                    NoSuchAlgorithmException,
                    InvalidKeySpecException,
                    NoSuchPaddingException,
                    InvalidKeyException {
        cryptOrDecrypt(key, encrypted, decrypted, DECRYPT_MODE);
    }
}
