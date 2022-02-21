package com.github.arorasagar.message;

import java.nio.charset.StandardCharsets;

public class HandshakeMessage extends Message {

    public HandshakeMessage(Integer peerId) {
        super(4, MessageType.HANDSHAKE, peerId.toString().getBytes(StandardCharsets.UTF_8));
    }
}
