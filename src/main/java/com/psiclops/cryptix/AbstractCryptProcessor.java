package com.psiclops.cryptix;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@RequiredArgsConstructor
public abstract class AbstractCryptProcessor {

    public static void copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bytes = new byte[4096];
        int size;
        while ((size = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, size);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }
}
