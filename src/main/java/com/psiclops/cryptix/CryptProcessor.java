package com.psiclops.cryptix;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.NoSuchPaddingException;

public interface CryptProcessor<ENCRYPTED, DECRYPTED, KEY> {

    ENCRYPTED encrypt(KEY key, DECRYPTED decrypted)
            throws InvalidAlgorithmParameterException,
                    NoSuchPaddingException,
                    IOException,
                    NoSuchAlgorithmException,
                    InvalidKeySpecException,
                    InvalidKeyException;

    void encrypt(KEY key, InputStream decrypted, OutputStream encrypted)
            throws IOException,
                    InvalidAlgorithmParameterException,
                    InvalidKeyException,
                    NoSuchAlgorithmException,
                    InvalidKeySpecException,
                    NoSuchPaddingException;

    DECRYPTED decrypt(KEY key, ENCRYPTED encrypted)
            throws InvalidAlgorithmParameterException,
                    NoSuchPaddingException,
                    IOException,
                    NoSuchAlgorithmException,
                    InvalidKeySpecException,
                    InvalidKeyException;

    void decrypt(KEY key, InputStream encrypted, OutputStream decrypted)
            throws IOException,
                    NoSuchAlgorithmException,
                    InvalidKeySpecException,
                    NoSuchPaddingException,
                    InvalidAlgorithmParameterException,
                    InvalidKeyException;
}
