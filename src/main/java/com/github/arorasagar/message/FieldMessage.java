package com.github.arorasagar.message;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;

public class FieldMessage extends Message {

    public FieldMessage(byte[] payload) {
        super(payload.length, MessageType.FIELD, payload);
    }

    public int getIndex() throws IOException {
        byte[] payload = getPayload();
        byte[] b = new ByteArrayInputStream(payload).readNBytes(4);
        return new BigInteger(b).intValue();
    }

    public byte[] getFileBytes() {
        byte[] payload = getPayload();
        byte[] b = new ByteArrayInputStream(payload, 4, payload.length).readAllBytes();
        return b;
    }
}
