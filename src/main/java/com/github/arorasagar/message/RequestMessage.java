package com.github.arorasagar.message;

import java.nio.charset.StandardCharsets;

public class RequestMessage extends Message {

    public RequestMessage(int piece) {
        super(4,MessageType.REQUESTED, String.valueOf(piece).getBytes(StandardCharsets.UTF_8));
    }
}
