package com.github.arorasagar.message;

public class ChokeMessage extends Message {

    public ChokeMessage() {
        super(0,MessageType.CHOKE, new byte[0]);
    }
}
