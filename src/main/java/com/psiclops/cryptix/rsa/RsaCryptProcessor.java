package com.psiclops.cryptix.rsa;

import com.psiclops.cryptix.AbstractCryptProcessor;
import com.psiclops.cryptix.CryptProcessor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.crypto.*;
import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Cipher.ENCRYPT_MODE;

@Getter
@RequiredArgsConstructor
public class RsaCryptProcessor extends AbstractCryptProcessor implements CryptProcessor<byte[], byte[], KeyPair> {

    @Override
    public byte[] encrypt(KeyPair keyPair, byte[] decrypted) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IOException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        encrypt(
                keyPair,
                new ByteArrayInputStream(decrypted),
                byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public byte[] decrypt(KeyPair keyPair, byte[] decrypted) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IOException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        decrypt(
                keyPair,
                new ByteArrayInputStream(decrypted),
                byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public void encrypt(KeyPair keyPair, InputStream decrypted, OutputStream encrypted) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(ENCRYPT_MODE, keyPair.getPublic());
        copy(decrypted, new CipherOutputStream(encrypted, cipher));
    }

    @Override
    public void decrypt(KeyPair keyPair, InputStream encrypted, OutputStream decrypted) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(DECRYPT_MODE, keyPair.getPrivate());
        copy(encrypted, new CipherOutputStream(decrypted, cipher));
    }

}
