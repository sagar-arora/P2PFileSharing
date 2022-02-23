package com.github.arorasagar;

import com.github.arorasagar.message.Message;

public class PeerMessage {
    Message message;
    PeerConnection receiverConnection;
    public PeerMessage(PeerConnection receiverConnection, Message message) {
        this.receiverConnection = receiverConnection;
        this.message = message;
    }
}
