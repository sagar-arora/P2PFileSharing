package com.github.arorasagar.message;

public class FieldMessage extends Message {

    public FieldMessage(byte[] payload) {
        super(payload.length, MessageType.FIELD, payload);
    }
}
