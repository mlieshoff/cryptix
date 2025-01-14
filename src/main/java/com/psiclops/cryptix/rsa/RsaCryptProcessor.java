package com.psiclops.cryptix.rsa;

import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Cipher.ENCRYPT_MODE;

import com.psiclops.cryptix.AbstractCryptProcessor;
import com.psiclops.cryptix.CryptProcessor;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;

@Getter
@RequiredArgsConstructor
public class RsaCryptProcessor extends AbstractCryptProcessor
        implements CryptProcessor<byte[], byte[], KeyPair> {

    @Override
    public byte[] encrypt(KeyPair keyPair, byte[] decrypted)
            throws NoSuchPaddingException,
                    IOException,
                    NoSuchAlgorithmException,
                    InvalidKeyException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        encrypt(keyPair, new ByteArrayInputStream(decrypted), byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public byte[] decrypt(KeyPair keyPair, byte[] decrypted)
            throws NoSuchPaddingException,
                    IOException,
                    NoSuchAlgorithmException,
                    InvalidKeyException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        decrypt(keyPair, new ByteArrayInputStream(decrypted), byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public void encrypt(KeyPair keyPair, InputStream decrypted, OutputStream encrypted)
            throws IOException,
                    InvalidKeyException,
                    NoSuchAlgorithmException,
                    NoSuchPaddingException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(ENCRYPT_MODE, keyPair.getPublic());
        copy(decrypted, new CipherOutputStream(encrypted, cipher));
    }

    @Override
    public void decrypt(KeyPair keyPair, InputStream encrypted, OutputStream decrypted)
            throws IOException,
                    NoSuchAlgorithmException,
                    NoSuchPaddingException,
                    InvalidKeyException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(DECRYPT_MODE, keyPair.getPrivate());
        copy(encrypted, new CipherOutputStream(decrypted, cipher));
    }
}
