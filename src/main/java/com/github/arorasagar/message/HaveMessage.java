package com.github.arorasagar.message;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class HaveMessage extends Message {

    public HaveMessage(int fileIndex) {
        super(4, MessageType.BITFIELD, String.valueOf(fileIndex).getBytes(StandardCharsets.UTF_8));
    }

    public int val() {

        return Integer.valueOf(new String(getPayload()));
    }
}
