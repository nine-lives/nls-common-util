package com.nls.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class LimitedByteArrayOutputStream extends OutputStream {
    private final ByteArrayOutputStream buffer;
    private final int limit;

    public LimitedByteArrayOutputStream(int limit) {
        this.buffer = new ByteArrayOutputStream(limit);
        this.limit = limit;
    }

    @Override
    public void write(int b) throws IOException {
        if (buffer.size() < limit) {
            buffer.write(b);
        }
    }

    @Override
    public String toString() {
        return buffer.toString();
    }
}
