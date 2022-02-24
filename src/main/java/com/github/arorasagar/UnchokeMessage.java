package com.github.arorasagar;

import com.github.arorasagar.message.Message;
import com.github.arorasagar.message.MessageType;

public class UnchokeMessage extends Message {

    public UnchokeMessage() {
        super(0, MessageType.UNCHOKE, new byte[0]);
    }
}

